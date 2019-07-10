package com.sensing.core.service.impl;


import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sensing.core.bean.SysOrg;
import com.sensing.core.bean.SysUser;
import com.sensing.core.dao.ISysOrgDAO;
import com.sensing.core.dao.ISysOrgObjectDAO;
import com.sensing.core.dao.ISysUserDAO;
import com.sensing.core.service.IAuthorizationService;
import com.sensing.core.utils.AuthorizationToken;
import com.sensing.core.utils.EncodeUtil;
import com.sensing.core.utils.Pager;

/**
 *@author wenbo
 */
@Service
public class AuthorizationServiceImpl implements IAuthorizationService{


	private static final Log log = LogFactory.getLog(AuthorizationServiceImpl.class);

	@Resource
	public ISysUserDAO sysUserDAO;
	
	@Resource
	public ISysOrgDAO sysOrgDAO;
	
	@Resource
	public ISysOrgObjectDAO sysOrgObjectDAO;
	
	public AuthorizationServiceImpl() {
		super();
	}

	
	/**
	 * 根据用户ID和登录时间生成令牌
	 * @param userId
	 * @param login_time
	 * @return
	 */
	@Override
	public String createToken(String userId,long login_time) throws Exception{
		//查询用户信息
		SysUser user = sysUserDAO.getSysUser(userId);
		
		//查询用户的组织信息
		SysOrg org = null;
		SysOrg tempOrg = null;

		if( user.getOrgId() != null ){
			org = sysOrgDAO.getSysOrg(user.getOrgId());
			tempOrg = org;
		}
		String[] orgIdArr = new String[]{null,null,null,null,null,null}; 
		if(org!=null) orgIdArr[0] = org.getUuid();
		
		//递归查询上级
		while(tempOrg!=null){
			orgIdArr[tempOrg.getOrgLevel().intValue()] = tempOrg.getUuid();
			if(tempOrg.getParentId()!=null && !tempOrg.getParentId().trim().equals("")){
				tempOrg = sysOrgDAO.getSysOrg(tempOrg.getParentId());
			}else{
				tempOrg = null;	
			}
		}		
		AuthorizationToken token = new AuthorizationToken(user.getUuid(),user.getUsername(),user.getIsAdmin()+"",login_time,user.getOrgId(),orgIdArr);
		return token.encode();
	}
	
	/**
	 * 验证token是否有效
	 * @param token
	 * @return
	 */
	@Override
	public boolean validateToken(String token){
		try{
			EncodeUtil.decode(token); 
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 根据用户令牌获取组织信息，6位数组，如:"所属机构ID、1级ID、2级ID、NULL、NULL、NULL",最多五级，第一个是用户直接所属组织的ID
	 * @return
	 */
	public String[] getOrgArrFromToken(String token){
		try{
			String tokenStr = EncodeUtil.decode(token); 
			AuthorizationToken tokenModel = new AuthorizationToken(tokenStr);
			String orgIdArr[] = tokenModel.getOrgIdArr();
			for(int i=0;i<orgIdArr.length;i++){
				if("null".equals(orgIdArr[i]))
					orgIdArr[i] = "";
			}
			return orgIdArr;
		}catch(Exception e){
			log.error("令牌解析错误"+token);
			return new String[]{null,null,null,null,null,null};
		}
	}
	
	/**
	 * 根据组织名称查询组织信息，6位数组，如:"所属机构ID、1级ID、2级ID、NULL、NULL、NULL",最多五级，第一个是用户直接所属组织的ID
	 * @return
	 */
	public String[] getOrgArrByName(String orgName){
		SysOrg org = null;
		SysOrg tempOrg = null;
		String[] orgArr = new String[]{null,null,null,null,null,null}; ;
		try{
			//根据组织查询机构信息
			org = sysOrgDAO.getSysOrgByName(orgName);
			//递归查询组织信息
			if( org != null){
				tempOrg = org;
				orgArr[0] = org.getUuid();
			}
			//递归查询上级
			while(tempOrg!=null){
				orgArr[tempOrg.getOrgLevel().intValue()] = tempOrg.getUuid();
				if(tempOrg.getParentId()!=null && !tempOrg.getParentId().trim().equals("")){
					tempOrg = sysOrgDAO.getSysOrg(tempOrg.getParentId());
				}else{
					tempOrg = null;	
				}
			}
		}catch(Exception e){
			log.error(e);
			return new String[]{null,null,null,null,null,null};
		}
		return orgArr;
	}
	
	/**
	 * 根据用户令牌获取用户UUID
	 * @return
	 */
	public String getUserIdFromToken(String token){
		try{
			String tokenStr = EncodeUtil.decode(token); 
			AuthorizationToken tokenModel = new AuthorizationToken(tokenStr);
			return tokenModel.getUserId();
		}catch(Exception e){
			log.error("令牌解析错误"+token);
			return null;
		}
	}
	
	/**
	 * 从token中查询用户是否是超级管理员，超级管理员不过滤数据权限
	 * @param token
	 * @return 1:超级管理员
	 */
	public String getIsAdminFromToken(String token){
		try{
			String tokenStr = EncodeUtil.decode(token); 
			AuthorizationToken tokenModel = new AuthorizationToken(tokenStr);
			return tokenModel.getIsAdmin();
		}catch(Exception e){
			log.error("令牌解析错误"+token);
			return null;
		}
	}
	
	/**
	 * pager对象的查询条件map中增加
	 * @param p
	 */
	public void addOrgInfoToPager(Pager p){
		String token = p.getF().get("token");
		//没有令牌过滤掉所有权限
		if(token==null || token.equals("")){
			p.getF().put("orgId", "-1");
			p.getF().put("orgId1", "-1");
		}else{
			String[] orgArr = this.getOrgArrFromToken(token);
			String isAdmin = this.getIsAdminFromToken(token);
			
			if(orgArr[0]!=null && !orgArr[0].equals("") && !orgArr[0].equals("null")) p.getF().put("orgId", orgArr[0]);
			if(orgArr[1]!=null && !orgArr[1].equals("") && !orgArr[1].equals("null")) p.getF().put("orgId1", orgArr[1]); 
			if(orgArr[2]!=null && !orgArr[2].equals("") && !orgArr[2].equals("null")) p.getF().put("orgId2", orgArr[2]);
			if(orgArr[3]!=null && !orgArr[3].equals("") && !orgArr[3].equals("null")) p.getF().put("orgId3", orgArr[3]);
			if(orgArr[4]!=null && !orgArr[4].equals("") && !orgArr[4].equals("null")) p.getF().put("orgId4", orgArr[4]);
			if(orgArr[5]!=null && !orgArr[5].equals("") && !orgArr[5].equals("null")) p.getF().put("orgId5", orgArr[5]);
			if(isAdmin!=null && !isAdmin.equals("") && !isAdmin.equals("null")) p.getF().put("isSuperAdmin", "1");
		}
	}
	
	/**
	 * 查询用户对某对象的操作权限级别
	 * @param objectType
	 * @param objectId
	 * @param token
	 * @return 1:只读  2：读写  3：读写删(次方法对只读的查询不全，没有包括上级可自动查询下级资源的情况，仅适用与判断编辑和删除权限)
	 */
	public int getObjectAuth(String objectType,String objectId,String token){
		AuthorizationToken tokenModel = null;
		try{
			String tokenStr = EncodeUtil.decode(token); 
			tokenModel = new AuthorizationToken(tokenStr);
		}catch(Exception e){
			log.error(e);
			return 0;
		}
		
		//超级管理员 返回3,可删除
		if("1".equals(tokenModel.getIsAdmin())){
			return 3;
		}
		//查询授权的权限级别
		Pager pg = new Pager();
		pg.getF().put("objectType", objectType);
		pg.getF().put("objectId", objectId);
		pg.getF().put("orgId", tokenModel.getOrgId());
		
		int l = 0;
		try{
			Integer level = sysOrgObjectDAO.getObjectAuthLevel(pg);
			if(level != null) l = level;
		
			
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
		}
		return l;
	}
	
	
	/**
	 * 查询用户对某对象的操作权限级别
	 * @param objectType
	 * @param objectId
	 * @param orgId
	 * @return 1:只读  2：读写  3：读写删(次方法对只读的查询不全，没有包括上级可自动查询下级资源的情况，仅适用与判断编辑和删除权限)
	 */
	public int getObjectAuthByOrgId(String objectType,String objectId,String orgId){
		SysOrg org = null;
		SysOrg tempOrg = null;
	
		int l = 0;
		try{
			if( orgId != null && !orgId.equals("")){
				org = sysOrgDAO.getSysOrg(orgId);
				tempOrg = org;
			}else{
				return 0;
			}
			String[] orgArr = new String[]{null,null,null,null,null,null}; 
			if(org!=null) orgArr[0] = org.getUuid();
			else return 0;
			
			//递归查询上级
			while(tempOrg!=null){
				orgArr[tempOrg.getOrgLevel().intValue()] = tempOrg.getUuid();
				if(tempOrg.getParentId()!=null && !tempOrg.getParentId().trim().equals("")){
					tempOrg = sysOrgDAO.getSysOrg(tempOrg.getParentId());
				}else{
					tempOrg = null;	
				}
			}	
			//查询授权的权限级别
			Pager p = new Pager();
			p.getF().put("objectType", objectType);
			p.getF().put("objectId", objectId);
			if(orgArr[0]!=null && !orgArr[0].equals("") && !orgArr[0].equals("null")) p.getF().put("orgId", orgArr[0]);
			if(orgArr[1]!=null && !orgArr[1].equals("") && !orgArr[1].equals("null")) p.getF().put("orgId1", orgArr[1]); 
			if(orgArr[2]!=null && !orgArr[2].equals("") && !orgArr[2].equals("null")) p.getF().put("orgId2", orgArr[2]);
			if(orgArr[3]!=null && !orgArr[3].equals("") && !orgArr[3].equals("null")) p.getF().put("orgId3", orgArr[3]);
			if(orgArr[4]!=null && !orgArr[4].equals("") && !orgArr[4].equals("null")) p.getF().put("orgId4", orgArr[4]);
			if(orgArr[5]!=null && !orgArr[5].equals("") && !orgArr[5].equals("null")) p.getF().put("orgId5", orgArr[5]);
			
			Integer level = sysOrgObjectDAO.getObjectAuthLevel(p);
			if(level != null) l = level;	
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
		}
		return l;
	}
	
	/**
	 * getObjectAuth方法的升级版
	 * @param objectType
	 * @param objectId
	 * @param token
	 * @return 1：只读(可授权)  2：读写   3：编辑    4:只读(不可授权)
	 */
	public int getObjectAuthAll(String objectType,String objectId,String token){
		if(objectType.equals("RG")){
			return 2;
		}
		int level = 0;
		try{		
			level = this.getObjectAuth(objectType, objectId, token);
			if(level == 0){
				//查询授权的权限级别
				Pager pg = new Pager();
				pg.getF().put("objectType", objectType);
				pg.getF().put("objectId", objectId);
				pg.getF().put("token", token);
				this.addOrgInfoToPager(pg);
				//查询上级组织对下级资源的只读权限
				Integer level1 = sysOrgObjectDAO.getAuthFromChildAuto(pg);
				if(level1>0) level = 4;
			}
		}catch(Exception e){
			log.error(e);
			return 0;
		}

		
		return level;
	}
		
	/**
	 * 根据机构ID获取资源权限
	 * @param objectType
	 * @param objectId
	 * @param orgId
	 * @return 1：只读(可授权)  2：读写   3：编辑    4:只读(不可授权)
	 */
	public int getObjectAuthAllByOrgId(String objectType,String objectId,String orgId){
		SysOrg org = null;
		SysOrg tempOrg = null;
		int level = 0;
		try{	
			level = this.getObjectAuthByOrgId(objectType, objectId, orgId);
			if(level == 0){
				//递归查询组织信息
				if( orgId != null && !orgId.equals("")){
					org = sysOrgDAO.getSysOrg(orgId);
					tempOrg = org;
				}else{
					return 0;
				}
				String[] orgArr = new String[]{null,null,null,null,null,null}; 
				if(org!=null) orgArr[0] = org.getUuid();
				else return 0;
				
				//递归查询上级
				while(tempOrg!=null){
					orgArr[tempOrg.getOrgLevel().intValue()] = tempOrg.getUuid();
					if(tempOrg.getParentId()!=null && !tempOrg.getParentId().trim().equals("")){
						tempOrg = sysOrgDAO.getSysOrg(tempOrg.getParentId());
					}else{
						tempOrg = null;	
					}
				}
				//查询授权的权限级别
				Pager p = new Pager();
				p.getF().put("objectType", objectType);
				p.getF().put("objectId", objectId);
				if(orgArr[0]!=null && !orgArr[0].equals("") && !orgArr[0].equals("null")) p.getF().put("orgId", orgArr[0]);
				if(orgArr[1]!=null && !orgArr[1].equals("") && !orgArr[1].equals("null")) p.getF().put("orgId1", orgArr[1]); 
				if(orgArr[2]!=null && !orgArr[2].equals("") && !orgArr[2].equals("null")) p.getF().put("orgId2", orgArr[2]);
				if(orgArr[3]!=null && !orgArr[3].equals("") && !orgArr[3].equals("null")) p.getF().put("orgId3", orgArr[3]);
				if(orgArr[4]!=null && !orgArr[4].equals("") && !orgArr[4].equals("null")) p.getF().put("orgId4", orgArr[4]);
				if(orgArr[5]!=null && !orgArr[5].equals("") && !orgArr[5].equals("null")) p.getF().put("orgId5", orgArr[5]);
				//查询上级组织对下级资源的只读权限
				Integer level1 = sysOrgObjectDAO.getAuthFromChildAuto(p);
				if(level1>0) level = 4;
			}
		}catch(Exception e){
			log.error(e);
			return 0;
		}
		
		return level;
	}	

}