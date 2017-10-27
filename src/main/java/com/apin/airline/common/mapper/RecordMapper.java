package com.apin.airline.common.mapper;


import com.apin.airline.common.entity.AssignRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author huanglei
 * @Description
 * @Date 2017/10/16
 */
@Mapper
public interface RecordMapper extends Mapper {

    /**
     * 增加舱位分配/回收记录
     *
     * @param assignRecord
     * @return
     */
    @Insert("INSERT INTO mbc_assign_record (id, account_id, dealer_id, flight_id, type, count, remark, creator_user, " +
            "creator_user_id, created_time) VALUES (#{id}, #{accountId}, #{dealerId}, #{flightId}, #{type}, #{count}, " +
            "#{remark}, #{creatorUser}, #{creatorUserId}, #{createdTime})")
    Insert addRecord(AssignRecord assignRecord);
}
