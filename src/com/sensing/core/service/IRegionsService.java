package com.sensing.core.service;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sensing.core.bean.Regions;
import com.sensing.core.bean.RegionsTree;
import com.sensing.core.utils.Pager;

/**
 *@author wenbo
 */
public interface IRegionsService {

	public Regions saveNewRegions(Regions regions)  throws Exception;

	public Regions updateRegions(Regions regions)  throws Exception;

	public Regions findRegionsById(Integer id) throws Exception;

	public int removeRegions(java.lang.Integer id) throws Exception;

	public Pager queryPage(Pager pager) throws Exception;
	
	public List<Regions> queryRegionTree(String rId)throws Exception;
	
	public List<Regions> queryRegionTreeByName(String name)throws Exception;
	
	public Regions queryRegionByCode(@Param("searchCode")String name) throws Exception;
	
	public List<Regions> queryRegionByParentId(@Param("parentId")String parentId,@Param("regionName") String regionName,Integer id) throws Exception;

	/**
	 * 根据区域id集查询区域信息
	 * @param regionIds
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2017年8月10日下午5:56:58
	 */
	public List<Regions> queryByJobs(String jobsId) throws Exception;
	
	/**
	 * 根据任务查询关联的通道所属的一级区域的名称
	 * @param jobsId
	 * @return
	 * @throws Exception
	 * @author mingxingyu
	 * @date   2017年10月18日下午5:11:47
	 */
	public List<Regions> queryOneLevelNameByJobs(String jobsId) throws Exception;
	
	/**
	 * @Description: 根据区域id查询直属子区域和通道,如所传id为空，则默认查询一级节点  
	 * @author dongsl
	 * @Date 2018年7月19日下午2:39:02
	 */
	public List<Regions> queryChildsById(Integer regionId,String isDeleted) throws Exception;
	
	public List<RegionsTree> findAllRecursion()throws Exception;

	public List<Regions> queryAllChilds(String del,Integer type);
}