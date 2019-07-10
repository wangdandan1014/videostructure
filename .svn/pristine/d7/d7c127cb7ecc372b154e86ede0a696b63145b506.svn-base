package com.sensing.core.dao;

import java.util.List;

import com.sensing.core.bean.SysResource;
import com.sensing.core.bean.SysRoleReso;
import com.sensing.core.bean.SysRoleSaveReq;
import com.sensing.core.utils.Pager;
import org.apache.ibatis.annotations.Param;

/**
 * @author wenbo
 */
public interface ISysRoleResoDAO {
    public int saveSysRoleReso(SysRoleReso sysRoleReso) ;

    public int saveSysRoleResoBetch(List<SysRoleReso> list);

    public SysRoleReso getSysRoleReso(java.lang.Integer id) ;

    public int removeSysRoleReso(java.lang.Integer id) ;

    public int updateSysRoleReso(SysRoleReso sysRoleReso) ;

    public List<SysRoleReso> queryList(Pager pager);

    public int selectCount(Pager pager);

    public int insertBetchSysReso(SysRoleSaveReq req);

    /**
     * 根据roleid删除系统资源
     * @param req
     * @return
     */
    public int delSysResoByRoleId(SysRoleSaveReq req);

    public List<SysRoleReso> getSysResoByRoleId(SysRoleSaveReq req);

    public List<SysRoleReso> getSysResoByRoleIds(List<Integer> roleIds);

    public int updateBetchSysRoleReso(@Param("list")List<SysRoleReso> list, @Param("roleId") Integer roleId) ;


}
