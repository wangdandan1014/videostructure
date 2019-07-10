package com.sensing.core.dao;

import java.util.List;

import com.sensing.core.bean.*;
import com.sensing.core.utils.Pager;
import org.omg.PortableInterceptor.INACTIVE;

/**
 * @author wenbo
 */
public interface ISysRoleDAO {
	public int saveSysRole(SysRole sysRole) ;

	public SysRole getSysRole(java.lang.Integer id) ;
	
	public  List<SysRole> getSysRoleByName(String roleName) ;

	public List<SysRole> getSysListRoleByUuid(java.lang.Integer id) ;

	public int removeSysRole(java.lang.Integer id) throws Exception;

	public int updateSysRole(SysRoleSaveReq sysRole) ;

	public List<SysRole> queryList(SysRoleReq req) ;

	public int selectCount(SysRoleReq req) ;

	public List<UserRoleCount> getUserRoleCount(List<Integer> list) ;

    /**
     * 通过用户id查询所对应的角色id
     * @param userUuid
     * @return
     */
	public List<SysUserRole> getUserRoleByUserUuid(String  userUuid) ;

	public String getUserNameByUuId(String  userUuid) ;

	public List<SysRole> getRoleByAddUserUuids(List<String>  list) ;


}
