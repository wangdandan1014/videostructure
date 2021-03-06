package com.sensing.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sensing.core.bean.SysUser;
import com.sensing.core.bean.SysUserRole;
import com.sensing.core.cacahes.SessionCache;
import com.sensing.core.dao.ISysResourceDAO;
import com.sensing.core.dao.ISysRoleDAO;
import com.sensing.core.dao.ISysUserDAO;
import com.sensing.core.service.ISysUserService;
import com.sensing.core.utils.Constants;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.ResponseBean;
import com.sensing.core.utils.UuidUtil;
import com.sensing.core.utils.Exception.BussinessException;
import com.sensing.core.utils.Exception.ExpUtil;
import com.sensing.core.utils.results.ResultUtils;

/**
 * @author wenbo
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

	private static final Log log = LogFactory.getLog(ISysUserService.class);

	@Resource
	public ISysUserDAO sysUserDAO;

	@Resource
	public ISysRoleDAO sysRoleDAO;

	@Resource
	public ISysResourceDAO sysResourceDAO;

	/**
	 * @Description: 保存用户
	 * @author dongsl
	 * @Date 2018年6月13日下午1:47:41
	 */
	@Override
	public SysUser saveNewSysUser(SysUser sysUser) throws Exception {
		try {
//            //如果用户真实姓名为空，则真实姓名和登录名相同
//            if (sysUser.getRealname() == null || "".equals(sysUser.getRealname())) {
//                sysUser.setRealname(sysUser.getUsername());
//            }
			String id = UuidUtil.getUuid();
			sysUser.setUuid(id);
			sysUser.setAddTime(new Date()); // 创建时间
			sysUser.setLoginCount(0); // 登录次数默认为0
			sysUser.setIsDeleted(Constants.DELETE_NO);
			sysUserDAO.saveSysUser(sysUser);
			sysUserDAO.saveUserRoleo(sysUser.getRoleId(), id);
		} catch (Exception e) {
			log.error("保存用户失败！" + ExpUtil.getExceptionDetail(e));
			throw new BussinessException(e);
		}
		return sysUser;
	}

	/**
	 * @Description: 更新用户
	 * @author dongsl
	 * @Date 2018年6月13日下午1:49:19
	 */
	@Override
	public SysUser updateSysUser(SysUser sysUser) {
		try {
			// 数据库中的用户信息
			SysUser suDB = findSysUserById(sysUser.getUuid());
			// 如果用户真实姓名为空，则真实姓名和登录名相同
//            if (sysUser.getRealname() == null || "".equals(sysUser.getRealname())) {
//                sysUser.setRealname(sysUser.getUsername());
//            }
			sysUserDAO.updateSysUser(sysUser);
			if (sysUser.getRoleId() != null && !"".equals(sysUser.getRoleId())) {
				sysUserDAO.deleteUserRoleo(sysUser.getUuid());
				if (!Constants.DELETE_YES.equals(sysUser.getIsDeleted())) {
					sysUserDAO.saveUserRoleo(sysUser.getRoleId(), sysUser.getUuid());
				}
			}
			// 账号删除时、账号停用时、账号修改密码时
			// 都将已登录的账号的session删除掉。使之重新登录
			if (sysUser.getIsDeleted() == Constants.DELETE_YES || sysUser.getState() == Constants.USER_STATE_UNUSE
					|| (StringUtils.isNotEmpty(sysUser.getPassword())) && !suDB.getPassword().equals(sysUser.getPassword())) {
				// session缓存中如果有该用户,则将该用户移除掉
				SessionCache.removeSessionByUser(sysUser.getUuid());
			}
		} catch (Exception e) {
			log.info("更新用户信息失败！" + ExpUtil.getExceptionDetail(e));
			throw new BussinessException(e);
		}
		return sysUser;
	}

	/**
	 * @Description: 根据uuid查询用户详情
	 * @author dongsl
	 * @Date 2018年6月13日下午1:10:20
	 */
	@Override
	public SysUser findSysUserById(java.lang.String uuid) throws Exception {
		try {
			SysUser su = new SysUser();
			su = sysUserDAO.getSysUser(uuid);
			return su;
		} catch (Exception e) {
			log.error("查询用户详情失败！" + ExpUtil.getExceptionDetail(e));
			throw new BussinessException(e);
		}
	}

	/**
	 * @Description: 删除用户
	 * @author dongsl
	 * @Date 2018年6月13日下午1:53:09
	 */
	@Override
	public void removeSysUser(String uuid) throws Exception {
		try {
			sysUserDAO.removeSysUser(uuid);
		} catch (Exception e) {
			log.error("删除用户失败！" + ExpUtil.getExceptionDetail(e));
			throw new BussinessException(e);
		}
	}

	/**
	 * @Description: 查询用户列表
	 * @author dongsl
	 * @Date 2018年6月13日下午1:53:53
	 */
	@Override
	public Pager queryPage(Pager pager) throws Exception {
		int totalCount = 0;
		List<SysUser> userlist = null;
		String uuId = pager.getF().get("uuId");
		//  2019/1/31 lxh 只有超级管理员能看到所有的列表，其他人都是只能看到自己创建的列表
		List<SysUserRole> userRoleIds = sysRoleDAO.getUserRoleByUserUuid(uuId);

		pager.getF().put("isAdmin", "0");
		for (SysUserRole sur : userRoleIds) {
			if (sur.getRoleId() == Constants.SYS_ROLE_SUPERADMIN) {
				pager.getF().put("isAdmin", 1 + "");
				break;
			}
		}
		// 添加用户信息查询，多个用户角色的id查询
		String roleIds = pager.getF().get("roleIds");
		if (StringUtils.isNotEmpty(roleIds)) {
			String[] roleIdsArr = roleIds.split(",");
			String roleIdsIn = "";
//			roleIdsIn += "'" + roleIdsArr + "'" + ",";
			for (int i = 0; i < roleIdsArr.length; i++) {
				if (i != 0) {
					roleIdsIn += (",'" + roleIdsArr[i] + "'");
				} else {
					roleIdsIn += ("'" + roleIdsArr[i] + "'");
				}
			}
			pager.getF().put("roleIds", roleIdsIn);
		}
		userlist = sysUserDAO.queryUser(pager);
		totalCount = sysUserDAO.queryCount(pager);

		pager.setTotalCount(totalCount);
		pager.setResultList(userlist);
		return pager;

	}

	/**
	 * 登录方法
	 */
	public SysUser login(SysUser sysUser) {
		SysUser user = new SysUser();
		try {
			user = sysUserDAO.getLoginUser(sysUser);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询登录用户失败,失败信息为：" + e.getMessage());
		}
		return user;
	}

	/**
	 * 已删除的用户登录
	 *
	 * @param sysUser
	 * @return
	 */
	public SysUser loginIsDel(SysUser sysUser) {
		SysUser user = new SysUser();
		try {
			user = sysUserDAO.loginIsDel(sysUser);
		} catch (Exception e) {
			log.error("查询登录用户失败");
		}
		return user;
	}

	/**
	 * @Description: 根据用户名查询用户
	 * @author dongsl
	 * @Date 2018年6月13日下午1:55:22
	 */
	public List<SysUser> queryUserByUserName(String id, String username) throws Exception {
		List<SysUser> list = new ArrayList<SysUser>();
		try {
			list = sysUserDAO.queryUserByUserName(id, username);
		} catch (Exception e) {
			log.error("根据用户名查询用户失败" + ExpUtil.getExceptionDetail(e));
		}
		return list;
	}

	/**
	 * 查询有审批权限的用户
	 */
	@Override
	public ResponseBean getUserHaveRatify(String userUuid) {
		// 2018/12/21布控选择审批人：角色是超级管理员，不需要排除掉自己，他可以创建，自己审批；角色是非超级管理员，需要排除自己，他创建的只能是别人来审批
		SysUser sysUser = sysUserDAO.getSysUser(userUuid);
		if (sysUser != null && sysUser.getRoleId() != null && sysUser.getRoleId().length >= 1) {
			List<String> roleIds = Arrays.asList(sysUser.getRoleId());
			if (roleIds.contains(Constants.SYS_ROLE_SUPERADMIN + "")) {
				userUuid = null;
			}
		}
		// lxh 审批的权限id不建议更改
		List<SysUser> list = sysUserDAO.getUserHaveRatify(Constants.JOB_RATIFY_QUALIFICATION, userUuid);
		return ResultUtils.success("resultList", list);
	}

	@Override
	public ResponseBean setSessionTimeOut(Integer sessionTimeOut) {
		SessionCache.sessionTimeOut = sessionTimeOut;
		return ResultUtils.success();
	}

	@Override
	public ResponseBean getSessionTimeOut() {
		return ResultUtils.success(SessionCache.sessionTimeOut / 60);
	}

}