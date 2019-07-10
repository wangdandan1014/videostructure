package com.sensing.core.service;

import java.util.List;

import com.sensing.core.bean.SysResource;
import com.sensing.core.utils.Pager;

/**
 * @author wenbo
 */
public interface ISysResourceService {

    public abstract SysResource saveNewSysResource(SysResource sysResource) throws Exception;

    public SysResource updateSysResource(SysResource sysResource) throws Exception;

    public abstract SysResource findSysResourceById(Integer id) throws Exception;

    public abstract void removeSysResource(java.lang.Integer id) throws Exception;

    public Pager queryPage(Pager pager) throws Exception;

    /**
     * 根据用户名称查询所属资源信息
     *
     * @param userName 用户名
     * @return
     */
    public List<SysResource> queryResourceByUserName(String userName) throws Exception;

    public List<SysResource> getSysResoByUuid(String uuid);

    /**
     * 得到用户的权限所对应的searchcode集合
     * @param uuid 用户uuid
     * @return
     */
    public List<String> getSysResoSearchCode(String uuid);

    public void queryResource() throws Exception;

    /**
     * 查询操作日志的二级联通的选项
     * @return
     * @throws Exception
     * @author mingxingyu
     * @date   2019年4月11日 上午11:46:16
     */
    public Pager queryOpeLog() throws Exception;

}