package com.apin.airline.flight;

import com.apin.airline.common.Airline;
import com.apin.airline.common.AopService;
import com.apin.airline.common.entity.Flight;
import com.apin.airline.common.entity.Log;
import com.apin.airline.common.entity.Seat;
import com.apin.airline.common.mapper.AirlineMapper;
import com.apin.airline.common.mapper.BaseMapper;
import com.apin.airline.common.mapper.QueryMapper;
import com.apin.airline.flight.dto.*;
import com.apin.airline.ticket.dto.CalendarInfo;
import com.apin.airline.ticket.dto.Stock;
import com.apin.util.DateHelper;
import com.apin.util.Generator;
import com.apin.util.ReplyHelper;
import com.apin.util.pojo.Reply;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author:huanglei
 * Description:
 * Date:2017/10/12
 */
@Service
public class FlightServiceImpl implements FlightService {
    @Autowired
    private AirlineMapper airlineMapper;
    @Autowired
    private AopService aopService;
    @Autowired
    private QueryMapper queryMapper;


    @Override
    public Reply homeCalendarInfo(HomeCalendarInfoQueryRequest request) {
        if(StringUtils.isEmpty(request.getAccountId())) {
            return ReplyHelper.invalidParam("账户ID不能为空");
        }

        if(StringUtils.isEmpty(request.getStartDate())) {
            return ReplyHelper.invalidParam("查询开始日期不能为空");
        }
        if(StringUtils.isEmpty(request.getEndDate())) {
            return ReplyHelper.invalidParam("查询结束日期不能为空");
        }

        if(!DateHelper.validateFormat(request.getStartDate(), "yyyy-MM-dd")) {
            return ReplyHelper.invalidParam("查询开始日期格式错误，请使用yyyy-MM-dd格式");
        }

        if(!DateHelper.validateFormat(request.getEndDate(), "yyyy-MM-dd")) {
            return ReplyHelper.invalidParam("查询结束日期格式错误，请使用yyyy-MM-dd格式");
        }
        List<HomeCalendarInfoDto> homeCalendarInfoDtos = queryMapper.queryHomeCalendarInfo(request);
        return ReplyHelper.success(homeCalendarInfoDtos);
    }

    @Override
    public Reply airlineInfo(CalendarInfo calendarInfo) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfE = new SimpleDateFormat("E");
        ApinCalendar apinCalendar = new ApinCalendar();
        String startDate = calendarInfo.getStartDate();
        Integer year = Integer.parseInt(startDate.substring(0,4));
        Integer month = Integer.parseInt(startDate.substring(5,7));
        Calendar systemCalendar = Calendar.getInstance();
        systemCalendar.set(Calendar.YEAR, year);
        systemCalendar.set(Calendar.MONTH, month - 1);
        systemCalendar.set(Calendar.DATE, 1);
        systemCalendar.roll(Calendar.DATE, -1);
        String firstDay = year+"-"+(month<10?"0":"")+month+"-01";
        String lastDay = year+"-"+(month<10?"0":"")+month+"-"+systemCalendar.get(Calendar.DATE);
        Integer firstWeekDay = null;
        switch (sdfE.format(sdf.parse(firstDay))){
            case "星期日":{firstWeekDay=0;break;}
            case "星期一":{firstWeekDay=1;break;}
            case "星期二":{firstWeekDay=2;break;}
            case "星期三":{firstWeekDay=3;break;}
            case "星期四":{firstWeekDay=4;break;}
            case "星期五":{firstWeekDay=5;break;}
            case "星期六":{firstWeekDay=6;break;}
            default:break;
        }
        apinCalendar.setFirstWeekDay(firstWeekDay);
        List<ApinCalendarElement> calendarElementList = queryMapper.findElementList(calendarInfo);
        List<ApinCalendarElement> needList = new ArrayList<>();
        LocalDate startLocalDate = LocalDate.parse(firstDay);
        LocalDate endLocalDate = LocalDate.parse(lastDay);
        ApinCalendarElement freeElement = new ApinCalendarElement();
        freeElement.setFreeStatus(true);
        Stream.iterate(startLocalDate, localDate -> localDate.plusDays(1))
                .limit(ChronoUnit.DAYS.between(startLocalDate, endLocalDate) + 1).forEach(d->{
            Optional<ApinCalendarElement> calendarElement=calendarElementList.stream().filter(e->e.getDepartDate().equals(d.toString())).findFirst();
            if(calendarElement.isPresent()) {
                needList.add(calendarElement.get());
            }
        });
        apinCalendar.setContentList(needList);
        return ReplyHelper.success(apinCalendar);
    }

    @Override
    public Reply homeAirlineInfo(HomeAirlineQueryRequest request) {
        if(StringUtils.isEmpty(request.getAccountId())) {
            return ReplyHelper.invalidParam("账户ID不能为空");
        }

        if(StringUtils.isEmpty(request.getQueryDate())) {
            return ReplyHelper.invalidParam("查询日期不能为空");
        }

        if(!DateHelper.validateFormat(request.getQueryDate(), "yyyy-MM-dd")) {
            return ReplyHelper.invalidParam("查询日期格式错误，请使用yyyy-MM-dd格式");
        }
        List<HomeAirlineResponse> homeAirlineResponses = queryMapper.queryAllAirlinesByDay(request);
        return ReplyHelper.success(homeAirlineResponses);
    }

    @Override
    public Reply modifyStock(Stock stock) {
        if(queryMapper.ifAllocated(stock)) {
            return ReplyHelper.fail("当前日期已有分配,不可修改库存");
        } else{
            Flight flight = queryMapper.findByAirlineIdAndFlightDate(stock.getAirlineId(),stock.getCurrentDate());
            Integer onSale = aopService.findStock(stock).getSold();
            Integer difference = stock.getStockNumber()-flight.getSeatCount()+onSale;
            flight.setSeatCount(stock.getStockNumber()+onSale);
            airlineMapper.updateSeatCount(flight.getId(),flight.getSeatCount());
            if(difference<0) {
                stock.setStockNumber(-difference);
                queryMapper.deleteStock(stock);
            }else if(difference>0){
                Seat seat = queryMapper.findByFlightIdAndOwnerId(flight.getId(),stock.getAccountId());
                seat.setSeatStatus(Airline.OFFSALE);
                for(int i=0;i<difference;i++){
                    seat.setId(Generator.uuid());
                    airlineMapper.addSeats(seat.getAccountId(),seat.getFlightId(),seat.getOwner(),seat.getCredType(),seat.getDivider(),seat.getDividerId());
                }
            }
            Log mbsAirlineLog = new Log(stock.getAirlineId(),"1005","修改库存成功,"+(difference<0?"减少":"增加")+difference+"张",stock.getUserName(),stock.getUserId(),stock.getEventSource());
            aopService.insertLog(mbsAirlineLog);
            return ReplyHelper.success();
        }
    }

    @Override
    public Reply modifyPrice(Stock stock) {
        BigDecimal adultPrice = stock.getAdultPrice();
        BigDecimal childPrice = stock.getChildPrice();
        airlineMapper.updatePrice(stock.getAirlineId(),stock.getDateList(),stock.getAdultPrice(),stock.getChildPrice());
        Log mbsAirlineLog = new Log(stock.getAirlineId(),"1006","修改价格成功,修改为成人价"+adultPrice+",儿童价"+childPrice,stock.getUserName(),stock.getUserId(),stock.getEventSource());
        aopService.insertLog(mbsAirlineLog);
        return ReplyHelper.success();
    }

    @Override
    public Reply priceImport(List<PriceTemplateBean> priceTemplateBeanList) {
        if (priceTemplateBeanList != null) {
            for (int i = 0; i < priceTemplateBeanList.size(); i++) {
                queryMapper.priceImport(priceTemplateBeanList.get(i));
            }
        }
        PriceTemplateBean bean = priceTemplateBeanList.get(0);
        Log Log = new Log(bean.getAirlineId(), "", "批量导入更新", bean.getUserName(), bean.getUserId(), "crm");
        aopService.insertLog(Log);
        return ReplyHelper.success();
    }

    @Override
    public Reply flightList(DealerListDto listDto) {
        if (StringUtils.isBlank(listDto.getAccountId())) {
            return ReplyHelper.invalidParam("包机商id不能为空");
        }
        if (StringUtils.isBlank(listDto.getOwnerId())) {
            return ReplyHelper.invalidParam("分销商id不能为空");
        }

        PageHelper.startPage(listDto.getPageNo(), listDto.getPageSize());
        List<FlightBo> flightBos = queryMapper.selectAirsByAccountId(listDto.getAccountId(),listDto.getOwnerId());
        if (flightBos.size() == 0)
            return ReplyHelper.success(new ArrayList<>(),0);
        PageInfo pageInfo = new PageInfo(flightBos);
        flightBos.forEach(flightBo->{
            List<String> dateList = queryMapper.selectByAirlineId( flightBo.getAirlineId());
            flightBo.setDateList(dateList);
            Integer autoCount = queryMapper.selectAssign(listDto, Airline.AUTO,flightBo.getAirlineId());
            Integer handCount = queryMapper.selectAssign(listDto, Airline.HAND,flightBo.getAirlineId());
            flightBo.setAutoCount( Math.abs(autoCount!=null?autoCount:0));
            flightBo.setDays(flightBo.getDays()+1);
            flightBo.setHandCount(Math.abs(handCount!=null?handCount:0));
        });
        return ReplyHelper.success(flightBos,pageInfo.getTotal());
    }

    @Override
    public Reply stockList(DealerListDto listDto) {
        if (StringUtils.isBlank(listDto.getAccountId())) {
            return ReplyHelper.invalidParam("包机商id不能为空");
        }
        if (StringUtils.isBlank(listDto.getOwnerId())) {
            return ReplyHelper.invalidParam("分销商id不能为空");
        }
        if (StringUtils.isBlank(listDto.getStartDate())&&StringUtils.isBlank(listDto.getEndDate())) {
            return ReplyHelper.invalidParam("日期为空");
        }

        List<StockBo> stockBos = queryMapper.selectStockInfo(listDto);
        List<String> flightIds=new ArrayList<>();
        for (StockBo stockBo:stockBos) {
            flightIds.add(stockBo.getFlightId());
            if (stockBo.getRecoveryDate()!=null&&stockBo.getRecoveryDate().getTime()<=System.currentTimeMillis()){
                List<Seat> mbsAirlineFlightSeats = queryMapper.selectByStatus(listDto.getOwnerId(), stockBo.getFlightId(), Airline.PASSENGER);
                stockBo.setSolded(mbsAirlineFlightSeats.size());
                stockBo.setRemain(stockBo.getTotal()-mbsAirlineFlightSeats.size());
            }

            Integer autoCount=queryMapper.selectAssignCount(listDto.getAccountId(),listDto.getOwnerId(),Airline.AUTO,stockBo.getFlightId());
            Integer handCount=queryMapper.selectAssignCount(listDto.getAccountId(),listDto.getOwnerId(),Airline.HAND,stockBo.getFlightId());
            stockBo.setAutoCount(Math.abs(autoCount!=null?autoCount:0));
            stockBo.setHandCount(Math.abs(handCount!=null?handCount:0));
        }

        List<StockBo> stockBos2=queryMapper.selectRecoverySeat(listDto);
        List<StockBo> collect = stockBos2.stream().filter(stockBo -> !flightIds.contains(stockBo.getFlightId())).collect(Collectors.toList());
        collect.forEach(stockBo ->{
            stockBo.setAutoCount(Math.abs(stockBo.getAutoCount()!=null?stockBo.getAutoCount():0));
            stockBo.setHandCount(Math.abs(stockBo.getHandCount()!=null?stockBo.getHandCount():0));});

        stockBos.addAll(collect);

        return ReplyHelper.success(stockBos);
    }

    @Override
    public Reply hasList(HasListDto dto) {
        List<Seat> mbsAirlineFlightSeats = queryMapper.hasList(dto.getAccountId(), dto.getOwnerId());
        if (mbsAirlineFlightSeats.size()<1) return ReplyHelper.invalidParam("一条也没有");
        return ReplyHelper.success();
    }

    @Override
    public Reply searchFlight(SearchDto searchDto) {
        List<FlightDetail> flightDetails=new ArrayList<>();
        for (CityList cityList : searchDto.getCityList()) {
            String img = queryMapper.selectCityImg(cityList.getArrCity());
            FlightDetail flightDetail = queryMapper.selectFlight(cityList);
            if (flightDetail!=null) {
                flightDetail.setArrCityImgUrl(img);
                flightDetails.add(flightDetail);
            }
        }
        if (flightDetails.size()>8){
            flightDetails = flightDetails.subList(0, 8);
        }
        return ReplyHelper.success(flightDetails);
    }

    /**
     * 根据城市对与出发日期查询航班
     *
     * @param cityList
     * @return
     */
    @Override
    public Reply searchFlights(CityList cityList) {
        List<FlightDetail> flightDetails = queryMapper.selectFlights(cityList);
        return ReplyHelper.success(flightDetails);
    }

    @Override
    public Reply searchFlightMonth(CityList cityList) {
        return ReplyHelper.success(queryMapper.selectFlightsMonth(cityList));
    }

    @Override
    public Reply count(DealerListDto listBo) {
       return ReplyHelper.success(queryMapper.count(listBo.getAccountId(),listBo.getCompName()));
    }

    @Override
    public Reply compName() {
        return ReplyHelper.success(queryMapper.findCompName());
    }

    @Override
    public Reply searchFlightDetail(CityList cityList) throws Exception{
        List<ResponseAirlineDto> responseAirlineDtos = queryMapper.selectFlightDetail(cityList);
        for (ResponseAirlineDto responseAirlineDto : responseAirlineDtos) {
            List<AirlineInfo> airlineInfos = queryMapper.selectByFlightNum(responseAirlineDto.getAirlineId());
            if (airlineInfos.size()==0) {
                continue;
            }
            if (airlineInfos.size() == 2) {
                AirlineInfo airlineInfo = airlineInfos.get(1);
                airlineInfo.setDepDate(responseAirlineDto.getRetDate());
                airlineInfo.setArrDate(getArrDate(airlineInfo));
            }
            AirlineInfo airlineInfo = airlineInfos.get(0);
            airlineInfo.setDepDate(cityList.getDepDate());
            airlineInfo.setArrDate(getArrDate(airlineInfo));
            responseAirlineDto.setAirlineInfo(airlineInfos);
        }
        return ReplyHelper.success(responseAirlineDtos);
    }

    @Override
    public Reply searchFlightList(CityList cityList) {
        List<ResponseAirlineDto> responseAirlineDto = queryMapper.selectFlightList(cityList);
        return ReplyHelper.success(responseAirlineDto);
    }

    @Override
    public Reply monthQuery(CityList cityList) {
       List<String> months = queryMapper.selectMonthQuery(cityList);
        return ReplyHelper.success(months);
    }

    @Override
    public Reply dayQuery(CityList cityList) {
        List<DayPrice> dayPrices = queryMapper.selectFlightDates(cityList);
        return ReplyHelper.success(dayPrices);
    }



    private String getArrDate(AirlineInfo airlineInfo) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date arr = formatter.parse(airlineInfo.getArrTime());
        Date dep = formatter.parse(airlineInfo.getDepTime());
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = formatter1.parse(airlineInfo.getDepDate());
        if (arr.getTime() > dep.getTime()) {
            return airlineInfo.getDepDate();
        }
        airlineInfo.setTag(1);
        return formatter1.format(new Date(parse.getTime()+24*3600*1000));
    }

}
