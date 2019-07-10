package com.sensing.core.service;

import java.util.List;
import java.util.Map;

import com.sensing.core.bean.Channel;
import com.sensing.core.bean.ChannelQueryResult;
import com.sensing.core.utils.Pager;

/**
 * @author wenbo
 */
public interface IChannelService {

	/**
	 * 查询通道的数量
	 * 
	 * @param pager
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date 2018年7月27日 上午10:12:03
	 */
	public int queryChannelCount(Pager pager) throws Exception;

	public Channel saveNewChannel(Channel channel) throws Exception;

	public Channel updateChannel(Channel channel) throws Exception;

	public Channel findChannelById(String uuid) throws Exception;

	public int removeChannel(String id, String uuid) throws Exception;

	public Pager queryPage(Pager pager) throws Exception;

	/**
	 * 根据通道uuid查询一条通道信息
	 * 
	 * @param channelId
	 * @param isDel
	 * @return
	 * @throws Exception
	 */
	public Channel getOneChannelByUuid(String channelId, String isDel) throws Exception;

	public List<Channel> queryChannelByChannelNoAndAddr(String channelNo, String channelAddr) throws Exception;

	public List<Channel> queryChannelByChannelNameAndRegionId(String channelName, Integer regionId, short isDel)
			throws Exception;

	/**
	 * 查询全通道信息
	 * 
	 * @return
	 */
	public abstract List<Channel> queryChannelList() throws Exception;

	/**
	 * @Description: 根据区域id查询所有子通道列表
	 * @param regionId
	 * @return
	 * @throws Exception
	 * @return List<String>
	 * @author dongsl
	 * @Date 2017年10月24日下午12:59:42
	 */
	public List<String> queryChannelListByRegionId(Integer regionId) throws Exception;

	public ChannelQueryResult QueryChannelsByRegionID(Integer regionID, Integer nStartNum, Integer nCount)
			throws Exception;

	/**
	 * 添加视频信息
	 * 
	 * @param fsd
	 * @return
	 */
	public Channel saveNewVideo(Channel fsd);

	public List<Channel> getChannelByIds(String... ids);

	public List<Channel> getChannelByUuIds(List<String> list);

	public int queryVideoByChannelName(String fileName);

	public void initChannelState();

	public void CallRemoveChannel(String uuid);

	public Map<String, Object> queryChannelStatCount(Map<String, Object> channelStatMap) throws Exception;

	public Pager queryPageNew(Pager pager);

	public List<Channel> queryChannelListUnderRegionByRegionId(Integer parentId);

}