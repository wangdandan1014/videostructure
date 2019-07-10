package com.sensing.core.service;

import com.sensing.core.bean.SysResource;
import com.sensing.core.bean.SysRoleReso;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.ResponseBean;

import java.util.List;

/**
 * @author wenbo
 */
public interface ISysRoleResoService {

    public abstract SysRoleReso saveNewSysRoleReso(SysRoleReso sysRoleReso) throws Exception;

    public SysRoleReso updateSysRoleReso(SysRoleReso sysRoleReso) throws Exception;

    public abstract SysRoleReso findSysRoleResoById(Integer id) throws Exception;

    public abstract void removeSysRoleReso(java.lang.Integer id) throws Exception;

    public Pager queryPage(Pager uuid);


}