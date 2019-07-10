package com.sensing.core.service.impl;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.sensing.core.bean.*;
import com.sensing.core.dao.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sensing.core.service.IChannelService;
import com.sensing.core.service.IRegionsService;
import com.sensing.core.utils.Exception.BussinessException;
import com.sensing.core.utils.Constants;
import com.sensing.core.utils.Exception.ExpUtil;
import com.sensing.core.utils.Pager;
import org.springframework.util.CollectionUtils;


/**
 * @author wenbo
 */
@Service
@SuppressWarnings("all")
public class RegionsServiceImpl implements IRegionsService {


    private static final Log log = LogFactory.getLog(RegionsServiceImpl.class);
    @Resource
    public IRegionsDAO regionsDAO;
    @Resource
    public IChannelDAO channelDAO;
    @Resource
    public IChannelService channelService;
    @Resource
    public ITaskDAO taskDAO;
    @Resource
    public IJobsDAO jobsDAO;
    @Resource
    public ITaskChannelDAO taskChannelDAO;

    @Resource
    public IJobsChannelDAO jobsChannelDAO;

    /**
     * @param regions
     * @return
     * @throws Exception
     * @Description: 保存区域信息
     * @author dongsl
     * @Date 2017年9月7日上午11:38:12
     */
    @Override
    public Regions saveNewRegions(Regions regions) throws Exception {
        try {
            //添加sort
            Regions temp = regionsDAO.queryMaxSortByParentId(regions.getParentId(), regions.getId());
            if (temp == null) {
                regions.setRegionSort(1);
            } else {
                regions.setRegionSort(temp.getRegionSort() + 1);
            }

            //设置区域SearchCode
            regions.setSearchCode(this.getRegionSeachCode(regions.getParentId()));
            regions.setIsDeleted(Constants.DELETE_NO);
            regionsDAO.saveRegions(regions);
        } catch (Exception e) {
            log.error(e);
            throw new BussinessException(e);
        }
        return regions;
    }

    /**
     * @Description: 修改区域信息
     * @author dongsl
     * @Date 2017年9月7日上午11:37:55
     */
    @Override
    public Regions updateRegions(Regions regions) throws Exception {
        try {
            regionsDAO.updateRegions(regions);
//			List<String> channelIDs = channelService.queryChannelListByRegionId(regions.getId());
//			LocalCache.updateLocalCacheData(channelIDs,regions);//更新本地缓存的通道区域关系
        } catch (Exception e) {
            log.error("更新区域信息失败！" + e.getMessage() + "---区域信息：" + regions.toString());
            throw new BussinessException(e);
        }
        return regions;
    }


    /**
     * @Description: 根据id查询区域
     * @author dongsl
     * @Date 2017年9月7日上午11:38:51
     */
    @Override
    public Regions findRegionsById(Integer id) throws Exception {
        try {
            return regionsDAO.getRegions(new Integer(id));
        } catch (Exception e) {
            log.error(e);
            throw new BussinessException(e);
        }
    }

    /**
     * @Description: 删除区域
     * @author dongsl
     * @Date 2017年9月7日上午11:39:08
     */
    @Override
    public int removeRegions(Integer id) throws Exception {
        int c = 0;
        try {
            c = regionsDAO.removeRegions(id);
        } catch (Exception e) {
            log.error(e);
            throw new BussinessException(e);
        }
        return c;
    }

    @Override
    public Pager queryPage(Pager pager) throws Exception {
        try {
            List<Regions> list = regionsDAO.queryList(pager);
            int totalCount = regionsDAO.selectCount(pager);
            pager.setTotalCount(totalCount);
            pager.setResultList(list);
        } catch (Exception e) {
            log.error(e);
            throw new BussinessException(e);
        }
        return pager;
    }


    /**
     * 查询区域通道列表
     */
    public List<Regions> queryRegionTree(String rId) throws Exception {
        return regionsDAO.queryRegionTree(rId);

    }

    /**
     * 根据通道名称查询根区域code
     */
    public List<Regions> queryRegionTreeByName(String name) throws Exception {
        return regionsDAO.queryRegionBootCodeByName(name);

    }

    /*	*//**
     * 递归查询区域通道信息
     * author dongsl
     * date 2017年8月10日下午4:08:51
     *//*
	public Regions getRegion(String rId) throws NumberFormatException,Exception {

		Regions region = findRegionsById(Integer.parseInt(rId));
		List<Regions> list = queryRegionTree(rId);
		for (Regions child : list) {
			if (child.getNodeType().equals(Constants.NODE_TYPE_CHANNEL)) {
				region.getChildRegionList().add(child);
				continue;
			}
			Regions n = getRegion(child.getrId()); // 递归
			region.getChildRegionList().add(n);
		}
		return region;
	}*/

    /**
     * 根据search_code查询区域信息
     */
    public Regions queryRegionByCode(String code) throws Exception {
        return regionsDAO.queryRegionByCode(code);
    }

    /**
     * 根据区域id集查询区域信息
     *
     * @param regionIds
     * @return
     * @throws Exception
     * @author mingxingyu
     * @date 2017年8月10日下午5:56:58
     */
    public List<Regions> queryByJobs(String jobsId) throws Exception {
        try {
            return regionsDAO.queryByJobs(jobsId);
        } catch (Exception e) {
            log.error(e);
            throw new BussinessException(e);
        }
    }

    @Override
    public List<Regions> queryRegionByParentId(String parentId, String regionName, Integer id) throws Exception {
        return regionsDAO.queryRegionByParentId(parentId, regionName, id);
    }

    /**
     * 根据任务查询关联的通道所属的一级区域的名称
     *
     * @param jobsId
     * @return
     * @throws Exception
     * @author mingxingyu
     * @date 2017年10月18日下午5:11:47
     */
    public List<Regions> queryOneLevelNameByJobs(String jobsId) throws Exception {
        try {
            return regionsDAO.queryOneLevelNameByJobs(jobsId);
        } catch (Exception e) {
            log.error(e);
            throw new BussinessException(e);
        }
    }

    private String getRegionSeachCode(Integer parentId) {
        String newSeachCode = "";
        try {
            //添加search_code
            DecimalFormat f = new DecimalFormat("0000");
            if (parentId != null && parentId != 0) {    //如果Pid不为空，则说明不是顶层类
                Regions parentRegion = this.findRegionsById(parentId);
                parentRegion = this.findRegionsById(parentId);
                int level = parentRegion.getRegionLevel() + 1;
                Regions region = regionsDAO.queryMaxCodeByLevel(level, parentId);

                if (region == null || region.getSearchCode() == null) {    //为空说明此层级暂无子节点，新保存的数据为其第一个子节点
                    newSeachCode = parentRegion.getSearchCode() + "0001";
                } else {                                //不为空则说明此层级已有子节点，新加的数据code在原来基础上+1
                    String last4Number = region.getSearchCode().substring(region.getSearchCode().length() - 4);
                    newSeachCode = region.getSearchCode().substring(0, region.getSearchCode().length() - 4) + f.format(Integer.parseInt(last4Number) + 1);
                }

            } else {    //如果Pid为空，则说明是顶层类

                Regions region = regionsDAO.queryMaxCodeByLevel(0, 0);
                if (region == null) {
                    newSeachCode = f.format(1);
                } else {
                    newSeachCode = f.format(Integer.parseInt(region.getSearchCode()) + 1);
                }
            }
        } catch (Exception e) {
            log.error("根据父区域id计算子区域serchCode方法失败！a");
        }
        return newSeachCode;
    }


    /**
     * @Description: 根据区域id查询直属子区域和通道, 如所传id为空，则默认查询一级节点
     * @author dongsl
     * @Date 2018年7月19日下午2:39:02
     */
    @Override
    public List<Regions> queryChildsById(Integer regionId, String isDeleted) throws Exception {
        List<Regions> list = new ArrayList<Regions>();
        try {
            list = regionsDAO.queryChildsById(regionId, isDeleted);
        } catch (Exception e) {
            log.error("调用RegionsService.queryChildsById(Integer regionId)方法异常！" + ExpUtil.getExceptionDetail(e));
            throw new BussinessException(e);
        }
        return list;
    }

    @Override
    public List<RegionsTree> findAllRecursion() throws Exception {
        List<RegionsTree> list = regionsDAO.findAllRecursion();
        return list;
    }

    /**
     *
     * @param del
     * @param type  1:是需要查出通道对应的布控状态 + 区域里对应的通道总数（首页）    2:区域里对应的通道总数 + 打开的通道数量（通道管理）
     * @return
     */
    @Override
    public List<Regions> queryAllChilds(String del, Integer type) {
        List<Regions> list = regionsDAO.queryAllChilds(del);

        if (type != null) {
            List<Regions> channelList = list.stream().filter(a -> Integer.parseInt(a.getNodeType()) == 1).collect(Collectors.toList());
            List<Regions> regionsList = list.stream().filter(a -> Integer.parseInt(a.getNodeType()) == 0).collect(Collectors.toList());
            /***** 区域下对应的 通道总个数 ************/
            setChannelCount(list, channelList, regionsList, 0);
            if (type == 1) {
                /********************************************   设置通道的布控状态 2019-02-14 0:布控中、2:未布控  **********************************************************************************/
                //查出来处在执行中的任务
                List<Jobs> jobs = jobsDAO.getUpdateStateJob(Arrays.asList(Constants.JOB_STATE_RUNNING));
                List<String> chennIds = new ArrayList<>();
                if (!CollectionUtils.isEmpty(jobs)) {
                    List<JobsChannelTemp> resps = jobsChannelDAO.getJobsChannelByJobUuid(jobs.stream().map(t -> t.getUuid()).collect(Collectors.toList()));
                    List<String> clist = resps.stream().map(a -> a.getChannelUuid()).collect(Collectors.toList());
                    chennIds.addAll(clist);
                }
                list.stream().forEach(a -> {
                    // 给首页用的数据 0:布控中  2:未布控
                    if (a.getNodeType() != null && a.getNodeType().equals("1")) {
                       if (!CollectionUtils.isEmpty(chennIds) && chennIds.contains(a.getrId())) {
                            a.setChannelState(0);
                        } else {
                            a.setChannelState(2);
                        }
                    }

                });
            } else if (type == 2) {
                List<Regions> openChannelList = channelList.stream().filter(a -> a.getState() == 1).collect(Collectors.toList());
                /***  区域下对应的 开启数量的通道个数 *****/
                setChannelCount(list, openChannelList, regionsList, 1);

            }
        }

        return list;
    }

    /**
     *
     * @param list
     * @param channelList
     * @param regionsList
     * @param type    0:区域下通道数量  1：区域下打开通道数量
     */
    private void setChannelCount(List<Regions> list, List<Regions> channelList, List<Regions> regionsList, int type) {
        for (Regions r : channelList) {
            //属于当前通道的父节点
            List<Regions> rList = regionsList.stream().filter(a -> Integer.parseInt(a.getrId()) == r.getParentId().intValue()).collect(Collectors.toList());
            int time = 0;
            while (true) {
                /**** 递归 ***/
                boolean b = setReginCount(rList, regionsList, time, type);
                if (b == false) {
                    break;
                }
            }
        }
    }


    /**
     * 从通道的第一个父级开始，将父级的数量+1，然后递归到第二个父级，数量+1，直到最外层；
     *
     * @param rList
     * @param regionsList 区域按照searchcode降序排序，要从最里层开始递归
     * @param type        0:区域下通道数量  1：区域下打开通道数量
     * @return
     */
    public boolean setReginCount(List<Regions> rList, List<Regions> regionsList, int time, int type) {
        time++;
        if (time > 10) {
            //防止嵌套次数多
            return false;
        }
        if ((rList != null && rList.size() == 1)) {
            Regions regions = rList.get(0);
            if (type == 0) {
                regions.setChannelCount(regions.getChannelCount() + 1);
            } else {
                regions.setOpenCount(regions.getOpenCount() + 1);
            }
            rList.set(0, regions);
            List<Regions> p2 = regionsList.stream().filter(a -> Integer.parseInt(a.getrId()) == regions.getParentId().intValue()).collect(Collectors.toList());
            setReginCount(p2, regionsList, time, type);
            return false;
        } else {
            return false;
        }

    }

}