package com.sensing.core.service;

import com.sensing.core.utils.Pager;

/**
 *@author wenbo
 */
public interface IAuthorizationService {
	
	/**
	 * 根据用户ID和登录时间生成令牌
	 * @param userId
	 * @param login_time
	 * @return
	 */
	public String createToken(String userId,long login_time) throws Exception;
	
	/**
	 * 验证token是否有效
	 * @param token
	 * @return
	 */
	public boolean validateToken(String token);
	
	/**
	 * 根据用户令牌获取组织信息
	 * @return
	 */
	public String[] getOrgArrFromToken(String token);
	
	/**
	 * 根据组织名称查询组织信息，6位数组，如:"所属机构ID、1级ID、2级ID、NULL、NULL、NULL",最多五级，第一个是用户直接所属组织的ID
	 * @return
	 */
	public String[] getOrgArrByName(String orgName);
	
	/**
	 * pager对象的查询条件map中增加
	 * @param p
	 */
	public void addOrgInfoToPager(Pager p);
	
	/**
	 * 根据用户令牌获取用户UUID
	 * @return
	 */
	public String getUserIdFromToken(String token);
	
	/**
	 * 从token中查询用户是否是超级管理员
	 * @param token
	 * @return 1:超级管理员
	 */
	public String getIsAdminFromToken(String token);
	
	
	/**
	 * 查询用户对某对象的操作权限级别
	 * @param objectType
	 * @param objectId
	 * @param token
	 * @return 1:只读  2：读写  3：读写删(次方法对只读的查询不全，没有包括上级可自动查询下级资源的情况，仅适用与判断编辑和删除权限)
	 */
	public int getObjectAuth(String objectType,String objectId,String token);
	
	/**
	 * 查询用户对某对象的操作权限级别
	 * @param objectType
	 * @param objectId
	 * @param orgId
	 * @return 1:只读  2：读写  3：读写删(次方法对只读的查询不全，没有包括上级可自动查询下级资源的情况，仅适用与判断编辑和删除权限)
	 */
	public int getObjectAuthByOrgId(String objectType,String objectId,String orgId);
	
	/**
	 * getObjectAuth方法的升级版
	 * @param objectType
	 * @param objectId
	 * @param token
	 * @return 1：只读(可授权)  2：读写   3：编辑    4:只读(不可授权)
	 */
	public int getObjectAuthAll(String objectType,String objectId,String token);
	
	/**
	 * 根据机构ID获取资源权限
	 * @param objectType
	 * @param objectId
	 * @param orgId
	 * @return 1：只读(可授权)  2：读写   3：编辑    4:只读(不可授权)
	 */
	public int getObjectAuthAllByOrgId(String objectType,String objectId,String orgId);
	

}