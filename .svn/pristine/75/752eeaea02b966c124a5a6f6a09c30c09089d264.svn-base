package com.sensing.core.dao;

import java.util.List;
import java.util.Map;

import com.sensing.core.bean.SysUserRole;
import com.sensing.core.bean.SysUserRoleDesc;
import org.apache.ibatis.annotations.Param;

import com.sensing.core.bean.SysUser;
import com.sensing.core.utils.Pager;

/**
 * <p>Title: ISysUserDAO</p>
 * <p>Description: </p>
 * <p>Company: www.sensingtech.com</p>
 *
 * @author haowenfeng
 * @version 2.0
 * @date 2017年8月16日下午3:47:49
 */
public interface ISysUserDAO {
    public int saveSysUser(SysUser paramSysUser) throws Exception;

    public SysUser getSysUser(@Param("uuid") String uuid);

    public int removeSysUser(String uuid) throws Exception;

    public int updateSysUser(SysUser paramSysUser) throws Exception;

    public int selectCount(Pager paramPager) throws Exception;

    public SysUser getLoginUser(SysUser paramSysUser) throws Exception;

    public SysUser loginIsDel(SysUser paramSysUser) throws Exception;

    public List<SysUser> queryUserByUserName(@Param("id") String id, @Param("username") String username) throws Exception;

    public List<SysUser> queryUserByRoleId(Integer roleId) ;

    public List<SysUser> queryUser(Pager pager) throws Exception;

    public int queryCount(Pager pager) throws Exception;

    public void saveUserRoleo(@Param("roleId") String[] roleId, @Param("id") String id);

    public void deleteUserRoleo(String uuid);

    public List<SysUserRoleDesc> getRoleByUserUId(List<String> uuidIds);

    public List<SysUser> getUserHaveRatify(@Param("id") Integer id, @Param("userUuid") String userUuid);

    public int getCountByRoleId(@Param("roleId") Integer roleId);

}
