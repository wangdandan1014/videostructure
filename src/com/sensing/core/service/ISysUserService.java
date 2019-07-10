package com.sensing.core.service;

import java.util.List;
import java.util.Map;

import com.google.protobuf.StructOrBuilder;
import com.sensing.core.bean.SysOrg;
import com.sensing.core.bean.SysUser;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.ResponseBean;

/**
 * @author wenbo
 */
public interface ISysUserService {

    public SysUser saveNewSysUser(SysUser sysUser) throws Exception;

    public SysUser updateSysUser(SysUser sysUser);

    public SysUser findSysUserById(java.lang.String uuid) throws Exception;

    public void removeSysUser(java.lang.String uuid) throws Exception;

    public Pager queryPage(Pager pager) throws Exception;

    public SysUser login(SysUser sysUser);

    public SysUser loginIsDel(SysUser sysUser);

    public List<SysUser> queryUserByUserName(String id, String username) throws Exception;

    public ResponseBean getUserHaveRatify(String userUuid);

    public ResponseBean setSessionTimeOut(Integer sessionTimeOut);

    public ResponseBean getSessionTimeOut();

}