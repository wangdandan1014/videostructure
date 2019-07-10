package com.sensing.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sensing.core.bean.Regions;
import com.sensing.core.bean.RegionsTree;
import com.sensing.core.utils.Pager;

/**
 * @author wenbo
 */
@SuppressWarnings("all")
public interface IRegionsDAO {
	public int saveRegions(Regions regions) throws Exception;

	public Regions getRegions(java.lang.Integer id) throws Exception;

	public int removeRegions(java.lang.Integer id) throws Exception;

	public int updateRegions(Regions regions) throws Exception;

	public List<Regions> queryList(Pager pager) throws Exception;

	public int selectCount(Pager pager) throws Exception;

	public List<Regions> queryRegionAndChannel(Pager pager) throws Exception;

	public int queryRegionAndChannelCount(Pager pager) throws Exception;

	/**
	 * 查询某一级中最大的search_code author dongsl date 2017年8月10日下午8:18:05
	 */
	public Regions queryMaxCodeByLevel(@Param("level") int level, @Param("parentId") int parentId) throws Exception;

	/**
	 * 根据id查询区域列表 author dongsl date 2017年8月10日下午8:18:41
	 */
	public List<Regions> queryRegionTree(@Param("rId") String rId) throws Exception;

	/**
	 * 根据通道名称查询区域列表 author dongsl date 2017年8月10日下午8:19:34
	 */
	public List<Regions> queryRegionBootCodeByName(@Param("name") String name) throws Exception;

	/**
	 * 根据serach_code查询区域 author dongsl date 2017年8月10日下午8:19:57
	 */
	public Regions queryRegionByCode(@Param("searchCode") String name) throws Exception;

	public List<Regions> queryRegionListByCode(@Param("codeMap") Map codeMap) throws Exception;

	/**
	 * 根据父id和区域名称查询区域（区域去重） author dongsl date 2017年8月21日下午8:37:32
	 */
	public List<Regions> queryRegionByParentId(@Param("parentId") String parentId,
			@Param("regionName") String regionName, @Param("id") Integer id) throws Exception;

	/**
	 * 根据区域id集查询区域信息
	 * 
	 * @param regionIds
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date 2017年8月10日下午5:56:58
	 */
	public List<Regions> queryByJobs(@Param("regionIds") String regionIds) throws Exception;

	public Regions queryMaxSortByParentId(@Param("parentId") Integer parentId, @Param("id") Integer id)
			throws Exception;

	/**
	 * @Description: 查询区域下所属的子区域和通道
	 * @param pager
	 * @return
	 * @throws Exception
	 * @return List<Regions>
	 * @author dongsl
	 * @Date 2017年9月22日下午2:35:29
	 */
	public Regions queryChildRegionAndChannel(@Param("regionId") Integer regionId) throws Exception;

	/**
	 * @Description: 查询一个一级区域
	 * @param regionId
	 * @return
	 * @throws Exception
	 * @return Regions
	 * @author dongsl
	 * @Date 2017年10月12日下午5:08:17
	 */
	public Regions queryPhoneRegion() throws Exception;

	/**
	 * 根据任务查询关联的通道所属的一级区域的名称
	 * 
	 * @param jobsId
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date 2017年10月18日下午5:11:47
	 */
	public List<Regions> queryOneLevelNameByJobs(@Param("jobsId") String jobsId) throws Exception;

	/**
	 * 根据通道ID查询对应的区域信息
	 * 
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	public Regions queryRegionByChannelId(@Param("channelId") String channelId) throws Exception;

	/**
	 * @Description: 根据区域id查询直属子区域和通道,如所传id为空，则默认查询一级节点
	 * @author dongsl
	 * @Date 2018年7月19日下午2:39:02
	 */
	public List<Regions> queryChildsById(@Param("regionId") Integer regionId, @Param("isDeleted") String isDeleted)
			throws Exception;

	public List<RegionsTree> findAllRecursion() throws Exception;

	public List<Regions> queryAllChilds(@Param("del") String del);

	public List<Regions> queryAllRegions();
}
