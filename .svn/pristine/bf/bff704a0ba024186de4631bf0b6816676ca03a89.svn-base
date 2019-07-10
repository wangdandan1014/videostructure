package com.sensing.core.dao;

import java.util.List;

import com.sensing.core.bean.SysResource;
import com.sensing.core.utils.Pager;
import org.apache.ibatis.annotations.Param;

/**
 * @author wenbo
 */
public interface ISysResourceDAO {
    public int saveSysResource(SysResource sysResource);

    public SysResource getSysResource(java.lang.Integer id);

    public int removeSysResource(java.lang.Integer id);

    public int updateSysResource(SysResource sysResource);

    public List<SysResource> queryList(Pager pager);

    public int selectCount(Pager pager);

    /**
     * 根据用户名查询所属资源信息
     */
    public List<SysResource> selectResourceByUserName(String userName);

    List<SysResource> getSysResoByRoleIds(@Param("list") List<Integer> list, @Param("delState")Integer delState);

    List<SysResource> getAllSysResoByRoleIds();

    List<Integer> getRoleByUuId(String uuid);

    public List<SysResource> queryResourceByMethod();

    /**
     * 查询操作日志的二级联通的选项 
     * @return
     * @author mingxingyu
     * @date   2019年4月11日 下午1:29:05
     */
    public List<SysResource> queryOpeLogList();
    
    /**
     * 查询操作日志的二级联通的选项数量
     * @return
     * @author mingxingyu
     * @date   2019年4月11日 下午1:29:14
     */
    public int queryOpeLogCount();
    
    
}
