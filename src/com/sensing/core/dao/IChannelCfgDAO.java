package com.sensing.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sensing.core.bean.ChannelCfgTemp;



public interface IChannelCfgDAO {
	
	/**
	 * 根据区域id查询通道信息
	 * author dongsl
	 * date 2017年8月18日下午4:13:13
	 */
	public List<ChannelCfgTemp> QueryChannelsByRegionID(@Param("regionId") String regionId,@Param("nStartNum")int nStartNum, @Param("nCount")int nCount,@Param("idList")List<String> idList) throws Exception;
	
}
