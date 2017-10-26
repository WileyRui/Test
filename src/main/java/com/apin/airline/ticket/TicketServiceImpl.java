package com.apin.airline.ticket;

import com.apin.airline.common.Airline;
import com.apin.airline.common.AopService;
import com.apin.airline.common.entity.AssignRecord;
import com.apin.airline.common.entity.Flight;
import com.apin.airline.common.entity.Log;
import com.apin.airline.common.entity.Seat;
import com.apin.airline.common.mapper.AirlineMapper;
import com.apin.airline.common.mapper.QueryMapper;
import com.apin.airline.common.mapper.RecordMapper;
import com.apin.airline.flight.dto.ApinCalendarElement;
import com.apin.airline.flight.dto.DealerListDto;
import com.apin.airline.ticket.dto.*;
import com.apin.util.Generator;
import com.apin.util.JsonUtils;
import com.apin.util.ReplyHelper;
import com.apin.util.pojo.Reply;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author:huanglei
 * Description:机票服务实现
 * Date:2017/10/11
 */
@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private AopService aopService;
    @Autowired
    private QueryMapper queryMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private AirlineMapper airlineMapper;
    @Override
    public Reply airlineInfo(CalendarInfo calendarInfo) {
        return null;
    }

    @Override
    public Reply modifyStock(Stock stock) {
        return null;
    }

    @Override
    public Reply saleStock(Stock stock) {
        ApinCalendarElement stockNumbers = aopService.findStock(stock);
        Integer unallocated = stockNumbers.getUnallocated();
        if(unallocated!=null&&stock.getStockNumber()>unallocated)
            return ReplyHelper.fail("库存不足");
        else{
            Ticket seat = new Ticket(stock, Airline.ONSALE);
            Ticket oldSeat = new Ticket(stock,Airline.OFFSALE);
            aopService.modifySeatStatus(seat,oldSeat);
            Log mbsAirlineLog = new Log(stock.getAirlineId(),"1004","销客成功,销客"+stock.getStockNumber()+"个",stock.getUserName(),stock.getUserId(),stock.getEventSource());
            aopService.insertLog(mbsAirlineLog);
            return ReplyHelper.success();
        }
    }

    @Override
    public Reply sale(DealerListDto dto) {
        List<Seat> mbsAirlineFlightSeats = queryMapper.selectByStatus(dto.getOwnerId(), dto.getFlightId(), Airline.PASSENGER);
        if (mbsAirlineFlightSeats.size()>dto.getNumber()){
            return ReplyHelper.invalidParam("逍客数量不得少于已添加乘机人数");
        }
        dto.setNumber(dto.getNumber()-mbsAirlineFlightSeats.size());
        queryMapper.updateToRemain(dto);
        queryMapper.updateStatus(dto);
        return ReplyHelper.success();
    }

    @Override
    public Reply dealStock(Deal deal) {
        Integer total = deal.getTotal();
        Stock stock = new Stock();
        stock.setDateList(deal.getDateList());
        stock.setAccountId(deal.getAccountId());
        stock.setAirlineId(deal.getAirlineId());
        List<ApinCalendarElement> calendarElements = aopService.findStockList(stock);
        if(calendarElements.size()==0||calendarElements.stream().anyMatch(e->e.getUnallocated()<total)||
                deal.getOwnerElementList().stream().mapToInt(OwnerElement::getSeatNumber).sum()>total)
            return ReplyHelper.fail("库存不足");
        deal.getOwnerElementList().stream().forEach(e->{
            List<String> idList =queryMapper.findModifySeatOwnerInBatchIdList(deal,e).stream().map(id->"'"+id+"'").collect(Collectors.toList());
            queryMapper.modifySeatOwnerInBatch(deal,e,idList.size()==0?"''":idList.toString().replace("[","").replace("]",""));
        });
        Log mbsAirlineLog = new Log(deal.getAirlineId(),"1003","分配库存成功",deal.getUserName(),deal.getUserId(),deal.getEventSouce());
        aopService.insertLog(mbsAirlineLog);
        return ReplyHelper.success();
    }

    @Override
    public Reply addPassenger(PassengerInfoDto dto) {
        List<Seat> mbsAirlineFlightSeats = queryMapper.selectByStatus(dto.getOwnerId(), dto.getFlightId(), null);
        if (mbsAirlineFlightSeats.size() < 1) return ReplyHelper.invalidParam("剩余位置不足");
        dto.setId( mbsAirlineFlightSeats.get(0).getId());
        queryMapper.updatePassengerInfo(dto);
        return ReplyHelper.success();
    }

    @Override
    public Reply importPassenger(ImportPassengerDto dto) {
        if (dto.getList().size()<1){
            return ReplyHelper.invalidParam("请完善乘机人信息");
        }
        List<Seat> seats = queryMapper.selectByStatus(dto.getOwnerId(), dto.getFlightId(), null);
        if (seats.size() < dto.getList().size())
            return ReplyHelper.invalidParam("剩余位置不足");
        for (int x = 0; x < dto.getList().size(); x++) {
            List<Seat> byCredNumber = queryMapper.findByCredNumber(dto.getList().get(x).getCredNumber(), dto.getFlightId(), dto.getOwnerId());
            if (byCredNumber.size()>=1){
                return ReplyHelper.invalidParam("存在多个重复的证件号，请完善！");
            }
        }
        for (int x = 0; x < dto.getList().size(); x++) {
            dto.getList().get(x).setId(seats.get(x).getId());
            queryMapper.updatePassengerInfo(dto.getList().get(x));
        }
        return ReplyHelper.success();
    }

    @Override
    public Reply passengerInfo(DealerListDto dto) {
        PageHelper.startPage(dto.getPageNo(), dto.getPageSize());
        List<Seat> mbsAirlineFlightSeats = queryMapper.selectByStatus(dto.getOwnerId(), dto.getFlightId(), Airline.PASSENGER);
        if (mbsAirlineFlightSeats.size() == 0)
            return ReplyHelper.success(new ArrayList<>(),0);

        PageInfo pageInfo = new PageInfo(mbsAirlineFlightSeats);
        return ReplyHelper.success(mbsAirlineFlightSeats,pageInfo.getTotal());
    }

    @Override
    public Reply updateSeatsOwner(UpdateSeatsOwnerDto dto) {
        //批量修改数据
        String owner = dto.getOwner();
        String accountId = dto.getAccountId();
        String dealerId = dto.getDealerId();
        String flightId = dto.getFlightId();
        int recoveryNum = dto.getRecoveryNum();
        queryMapper.updateSeatsOwner(owner, accountId, dealerId, flightId, recoveryNum);

        //保存回收的量
        AssignRecord mbcAssignRecord = new AssignRecord();
        mbcAssignRecord.setId(Generator.uuid());
        mbcAssignRecord.setAccountId(accountId);
        mbcAssignRecord.setDealerId(dealerId);
        mbcAssignRecord.setFlightId(flightId);
        mbcAssignRecord.setCount(0-recoveryNum);
        mbcAssignRecord.setType(3);
        mbcAssignRecord.setRemark("");
        mbcAssignRecord.setCreatorUser(dto.getUserName());
        mbcAssignRecord.setCreatorUserId(dto.getUserId());
        mbcAssignRecord.setCreatedTime(new java.util.Date());
        recordMapper.insert(mbcAssignRecord);

        Flight mbsAirlineFlight = queryMapper.selectByFlightId(flightId);
        Log mbsAirlineLog = new Log(mbsAirlineFlight.getAirlineId(), "1002", "手动收位成功", dto.getUserName(), dto.getUserId(),"ARM");
        aopService.insertLog(mbsAirlineLog);
        return ReplyHelper.success();
    }

    @Override
    public Reply updateSeatsOwnerById(UpdateAndLogsResponse updateAndLogsResponse) {

        List<FlightSeatResponse> flightSeatResponses = updateAndLogsResponse.getFlightSeatResponses();
        StringBuffer sb = new StringBuffer("");
        flightSeatResponses.forEach(flightSeatResponse -> {
            sb.append("'");
            sb.append(flightSeatResponse.getId());
            sb.append("',");
        });
        String ids = sb.toString();
        if(StringUtils.isEmpty(ids)){
            return ReplyHelper.success();
        }

        String creatorUser = null;
        String creatorUserId = null;
        List<Log> mbsAirlineLogs = updateAndLogsResponse.getMbsAirlineLogs();
        for (Log mbsAirlineLog : mbsAirlineLogs) {
            creatorUser = mbsAirlineLog.getOperatorUser();
            creatorUserId = mbsAirlineLog.getOperatorId();
            aopService.insertLog(mbsAirlineLog);
        }
        //查询出强制回收时，每个供应商回收的数量
        ids = ids.substring(0, ids.length() - 1);
        List<QueryForceRecoveryDto> queryForceRecoveryDtos = queryMapper.queryForceRecovery(ids);
        for (QueryForceRecoveryDto qrd : queryForceRecoveryDtos) {
            AssignRecord mbcAssignRecord = new AssignRecord();
            mbcAssignRecord.setId(Generator.uuid());
            mbcAssignRecord.setAccountId(qrd.getAccountId());
            mbcAssignRecord.setDealerId(qrd.getDealerId());
            mbcAssignRecord.setFlightId(qrd.getFlightId());
            mbcAssignRecord.setCount(0-qrd.getCount());
            mbcAssignRecord.setType(2);
            mbcAssignRecord.setRemark("");
            mbcAssignRecord.setCreatorUser(creatorUser);
            mbcAssignRecord.setCreatorUserId(creatorUserId);
            mbcAssignRecord.setCreatedTime(new java.util.Date());
            recordMapper.insert(mbcAssignRecord);
        }

        flightSeatResponses.forEach(flightSeatResponse -> {
           queryMapper.updateSeatsOwnerById(flightSeatResponse.getOwner(), flightSeatResponse.getOwnerId(), flightSeatResponse.getId());
        });

        return ReplyHelper.success();
    }

    @Override
    public Reply getEnableSeat(GetEnableSeatDto getEnableSeatDto) {
        String accountId = getEnableSeatDto.getAccountId();
        String dealerId = getEnableSeatDto.getDealerId();
        String flightId = getEnableSeatDto.getFlightId();
        if (StringUtils.isBlank(accountId)) {
            return ReplyHelper.invalidParam("包机商id不存在");
        }
        if (StringUtils.isBlank(dealerId)) {
            return ReplyHelper.invalidParam("分销商id不存在");
        }
        if (StringUtils.isBlank(flightId)) {
            return ReplyHelper.invalidParam("航班id不存在");
        }
        Integer ct = queryMapper.getRecoveryDateByFlightId(flightId);
        int status = 1;
        if(ct == 0){
            status = 2;
        }
        Integer sum = queryMapper.getEnableSeat(accountId, dealerId, flightId, status);
        return ReplyHelper.success(sum);
    }

    @Override
    public Reply queryFlightsToRecovery(String today) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = sdf.parse(today);
        Calendar start = Calendar.getInstance();
        start.setTime(date);
        List<QueryFlightsToRecoveryDto> queryFlightsToRecoveryDtos = queryMapper.queryFlightsToRecovery(start.getTime());
        return ReplyHelper.success(queryFlightsToRecoveryDtos);
    }

    @Override
    public Reply merchantPassengerInfo(PassengerParam dto) {
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize());
        List<PassengerInfo> passengerList = queryMapper.getPassengerList(dto.getAccountId(),dto);
        PageInfo pageInfo = new PageInfo(passengerList);
        System.out.println(pageInfo.getList().size());

        List<PassengerResponse> passengerResponsesList = new ArrayList<>();
        for (PassengerInfo passengerInfo : (List<PassengerInfo>)pageInfo.getList()) {
            PassengerResponse passengerResponse = new PassengerResponse();
            if (passengerInfo.getCredType() != null) {
                passengerResponse.setCredTypeStr(Constant.CredType.valueOf(passengerInfo.getCredType()));
            }
            if (passengerInfo.getPassengerType() != null) {
                passengerResponse.setPassengerTypeStr(Constant.PassengerType.valueOf(passengerInfo.getPassengerType()));
            }
            if (passengerInfo.getGender() != null){
                passengerResponse.setGenderStr(Constant.Gender.valueOf(passengerInfo.getGender()));
            }
            passengerResponse.setOwner(passengerInfo.getOwner());
            passengerResponse.setPassengerName(passengerInfo.getPassengerName());
            passengerResponse.setCredNumber(passengerInfo.getCredNumber());
            if (passengerInfo.getBirthday() != null){
                passengerResponse.setBirthday(passengerInfo.getBirthday().toString());
            }
            passengerResponse.setNation(passengerInfo.getNation());
            if (passengerInfo.getExpireTime() != null){
                passengerResponse.setExpireTime(passengerInfo.getExpireTime().toString());
            }
            passengerResponse.setIssuePlace(passengerInfo.getIssuePlace());
            passengerResponsesList.add(passengerResponse);
        }
        String encoder= JsonUtils.toJson(passengerResponsesList);
        return ReplyHelper.success(encoder,pageInfo.getTotal());
    }
}
