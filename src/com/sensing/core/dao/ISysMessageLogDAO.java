package com.sensing.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sensing.core.bean.message.SysMessageLog;

/**
 * SysMessageLogDAO继承基类
 */
public interface ISysMessageLogDAO {

    int insert(SysMessageLog record);

    int setMsgReadByExtendUuId(SysMessageLog m);

    int setMsgReadById(@Param("ids") List<Integer> ids);

    public Map<String,Long> getUnReadCount(@Param("userUuid")String userUuid);

    List<String> getUnReadByExtengIdAndType(@Param("jobsUuids") List<String> jobsUuids, @Param("type") Integer type);

    List<SysMessageLog> getUnReadByExtengId(@Param("jobsUuids") List<String> jobsUuids);

}