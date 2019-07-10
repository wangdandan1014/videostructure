package com.sensing.core.service;

import com.sensing.core.bean.*;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.ResponseBean;

import java.util.List;

/**
 * @author wenbo
 */
public interface ISysRoleService {

    /**
     * 角色新建保存
     *
     * @param sysRole
     * @return
     */
    public int saveNewSysRole(SysRoleSaveReq sysRole);

    /**
     * 角色更新
     *
     * @param sysRole
     * @return
     */
    public ResponseBean updateSysRole(SysRoleSaveReq sysRole);

    /**
     * 查询角色列表
     *
     * @param req
     * @return
     */
    public Pager queryPage(SysRoleReq req);

    /**
     * 编辑的时候获取拥有的资源
     * @param req
     */
    public List<SysResource> getSysResOnEdit(SysResoEdidReq req);
    
    public  List<SysRole> getSysRoleByName(String roleName) ;

}