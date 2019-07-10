package com.sensing.core.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.sensing.core.bean.*;
import com.sensing.core.dao.ISysResourceDAO;
import com.sensing.core.dao.ISysRoleResoDAO;
import com.sensing.core.dao.ISysUserDAO;
import com.sensing.core.service.ISysResourceService;
import com.sensing.core.utils.Constants;
import com.sensing.core.utils.ResponseBean;
import com.sensing.core.utils.results.ResultUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sensing.core.dao.ISysRoleDAO;
import com.sensing.core.service.ISysRoleService;
import com.sensing.core.utils.Pager;
import org.springframework.util.CollectionUtils;

/**
 * @author wenbo
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {


    private static final Log log = LogFactory.getLog(ISysRoleService.class);

    @Resource
    public ISysRoleDAO sysRoleDAO;
    @Resource
    public ISysResourceDAO sysResourceDAO;
    @Resource
    public ISysUserDAO sysUserDAO;
    @Resource
    public ISysRoleResoDAO sysRoleResoDAO;
    @Resource
    public ISysResourceService sysResourceService;


    public SysRoleServiceImpl() {
        super();
    }

    /**
     * 新建角色
     * 要将当前用户角色已有的资源都查出来，
     * 存到sys_role_reso中如果 保存时勾线的资源将设置为is_del=0，未勾选的设置为1
     *
     * @param req
     * @return
     */
    @Override
    public int saveNewSysRole(SysRoleSaveReq req) {

        /*** sysRole ***/
        SysUser sysUser = sysUserDAO.getSysUser(req.getUserUUid());
        SysRole role = new SysRole();
        //2018/9/10 lxh  根据用户uuid查询对应的roleid  集合  取最大的
        int maxRoleId = getMaxRoleId(req.getUserUUid());
        role.setParentId(maxRoleId);
        role.setRoleName(req.getRoleName());
        role.setAddUuId(req.getUserUUid());
        sysRoleDAO.saveSysRole(role);
        /*** sysRoleReso ***/
        List<SysRoleReso> ssList = new ArrayList<>();
        List<Integer> roleIds = sysResourceDAO.getRoleByUuId(req.getUserUUid());
        List<SysResource> list = sysResourceDAO.getSysResoByRoleIds(roleIds, 0);

        SysRoleReso reso = null;
        for (SysResource s : list) {
            reso = new SysRoleReso();
            if (req.getSysResourceIds().contains(s.getId())) {
                reso.setIsDel(0);
            } else {
                reso.setIsDel(1);
            }
            reso.setResoId(s.getId());
            reso.setRoleId(role.getId());
            ssList.add(reso);
        }
        int code2 = sysRoleResoDAO.saveSysRoleResoBetch(ssList);
        return code2;
    }

    public int getMaxRole(String[] roleIds) {
        List<String> roleList = Arrays.asList(roleIds);
        List<Integer> roleList2 = roleList.stream().map(a -> Integer.parseInt(a)).collect(Collectors.toList());
        Integer max = Collections.max(roleList2);
        return max.intValue();
    }

    /**
     * 角色更新
     * 如果是角色的资源有更新，要做软删除，将sys_role_reso里的is_del 置为0
     *
     * @param req
     * @return
     */
    @Override
    public ResponseBean updateSysRole(SysRoleSaveReq req) {

        if (req.getIsDeleted() != null && req.getIsDeleted() == 1) {
            int countByRoleId = sysUserDAO.getCountByRoleId(req.getRoleId());
            if (countByRoleId > 0) {
                return ResultUtils.error(100, "当前角色绑定" + countByRoleId + "个用户，无法删除");
            }
        }
        /*** sysrole ***/
        sysRoleDAO.updateSysRole(req);

        if (!CollectionUtils.isEmpty(req.getSysResourceIds())) {
            /*** sysRoleReso  ***/
            List<SysRoleReso> roleReso = sysRoleResoDAO.getSysResoByRoleId(req);
            for (SysRoleReso s : roleReso) {
                if (req.getSysResourceIds().contains(s.getResoId())) {
                    s.setIsDel(0);
                } else {
                    s.setIsDel(1);
                }
            }
            sysRoleResoDAO.updateBetchSysRoleReso(roleReso, req.getRoleId());

            // 2019/1/24 lxh  更新了当前角色，当前角色的子角色是否增加相应的权限。。。
//            updateSysRoleResoByRoleId(addRoleReso, req.getRoleId());
        }
        return ResultUtils.success();

    }


    public void updateSysRoleResoByRoleId(List<SysRoleReso> addRoleReso, Integer roleId) {
        /*** sysRoleReso 和当前角色有关联的用户  ***/
        List<SysUser> sysUsers = sysUserDAO.queryUserByRoleId(roleId);
        List<SysRole> sysRoleList = sysRoleDAO.getRoleByAddUserUuids(sysUsers.stream().map(a -> a.getUuid()).collect(Collectors.toList()));
        List<SysRoleReso> sysReso = sysRoleResoDAO.getSysResoByRoleIds(sysRoleList.stream().map(a -> a.getId()).collect(Collectors.toList()));
    }


    @Override
    public Pager queryPage(SysRoleReq req) {
        List<Integer> pIds = null;
        List<SysRole> list = null;
        int totalCount = 0;
        //2018/12/25 lxh  超级管理员看到的用户管理列表是所有的用户，拥有最高权限，其他角色是能看到多有的，但是只能操作自己建的，，，，
        // 2018/11/19 lxh pid 这个字段未用到
        // 2019/1/31 lxh 角色管理 超级管理员能看到所有的角色，其他角色是能看到除了管理管理员之外的角色
        List<SysUserRole> userRoleIds = sysRoleDAO.getUserRoleByUserUuid(req.getUuid());
        req.setIsAdmin(0);
        for (SysUserRole sur : userRoleIds) {
            if (sur.getRoleId() == Constants.SYS_ROLE_SUPERADMIN) {
                req.setIsAdmin(1);
                break;
            }
        }
        list = sysRoleDAO.queryList(req);
        totalCount = sysRoleDAO.selectCount(req);

        if (!CollectionUtils.isEmpty(list)) {
            List<Integer> roleIds = list.stream().map(a -> a.getId()).collect(Collectors.toList());
            //   根据roleid查询sysuser对应的用户个数
            List<UserRoleCount> counts = sysRoleDAO.getUserRoleCount(roleIds);
            for (SysRole r : list) {
                List<UserRoleCount> cList = counts.stream().filter(a -> a.getRoleId() == r.getId().intValue()).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(cList) && cList.size() == 1) {
                    r.setRoleCount(cList.get(0).getCount());
                }
            }
        }

        Pager pager = new Pager();
        pager.setPageNo(req.getPageNo());
        pager.setPageRows(req.getPageRows());
        pager.setTotalCount(totalCount);
        pager.setResultList(list);

        return pager;

    }

    /**
     * 编辑的时候获取拥有的资源
     *
     * @param req
     * @return
     */
    @Override
    public List<SysResource> getSysResOnEdit(SysResoEdidReq req) {

        List<SysResource> allResot = sysResourceDAO.getSysResoByRoleIds(Arrays.asList(req.getRoleId()), null);
        List<SysResource> haveReso = allResot.stream().filter(a -> a.getIsDel() == 0).collect(Collectors.toList());

        //将allResot资源设置为父子级的数据
        /***  一级分类   *****/
        List<SysResource> allResoParent = allResot.stream().filter(a -> a.getParentId().intValue() == 0).collect(Collectors.toList());
        for (SysResource firstReso : allResoParent) {
            /***  二级分类   *****/
            List<SysResource> second = allResot.stream().filter(a -> firstReso.getId().intValue() == a.getParentId().intValue()).collect(Collectors.toList());
            for (SysResource secondReso : second) {
                /***  三级分类   *****/
                List<SysResource> third = allResot.stream().filter(a -> secondReso.getId().intValue() == a.getParentId().intValue()).collect(Collectors.toList());
                for (SysResource thirdReso : third) {
                    /***  四级分类   *****/
                    List<SysResource> four = allResot.stream().filter(a -> thirdReso.getId().intValue() == a.getParentId().intValue()).collect(Collectors.toList());
                    four.sort(Comparator.comparing(SysResource::getOrde));
                    thirdReso.setChildResoList(four);
                }
                third.sort(Comparator.comparing(SysResource::getOrde));
                secondReso.setChildResoList(third);
            }
            second.sort(Comparator.comparing(SysResource::getOrde));
            firstReso.setChildResoList(second);
        }
        allResoParent.sort(Comparator.comparing(SysResource::getOrde));

        //设置选中状态
        List<Integer> haveResoIds = haveReso.stream().map(a -> a.getId().intValue()).collect(Collectors.toList());
        for (SysResource s : allResoParent) {
            /*** 一级分类 **/
            s.setCheckState(haveResoIds.contains(s.getId().intValue()));
            List<SysResource> secondList = s.getChildResoList();
            if (!CollectionUtils.isEmpty(secondList)) {
                for (SysResource secondReso : secondList) {
                    /*** 二级分类 **/
                    secondReso.setCheckState(haveResoIds.contains(secondReso.getId().intValue()));
                    List<SysResource> thirdList = secondReso.getChildResoList();
                    if (!CollectionUtils.isEmpty(thirdList)) {
                        for (SysResource thirdReso : thirdList) {
                            /*** 三级分类 **/
                            thirdReso.setCheckState(haveResoIds.contains(thirdReso.getId().intValue()));
                            List<SysResource> fourthResoList = thirdReso.getChildResoList();
                            if (!CollectionUtils.isEmpty(fourthResoList)) {
                                for (SysResource fourthReso : fourthResoList) {
                                    /*** 四级级分类 **/
                                    fourthReso.setCheckState(haveResoIds.contains(fourthReso.getId().intValue()));
                                }
                            }
                        }
                    }
                }
            }
        }

        return allResoParent;
    }


    private int getMaxRoleId(String userUuid) {
        //通过userUuid得到角色集合，得到最大的角色
        List<SysUserRole> roleList = sysRoleDAO.getUserRoleByUserUuid(userUuid);
        // 2018/9/6 lxh 角色 
        OptionalInt max = roleList.stream().mapToInt(SysUserRole::getRoleId).max();
        return max.isPresent() ? max.getAsInt() : 0;
    }

	public  List<SysRole> getSysRoleByName(String roleName) {
		List<SysRole> list = sysRoleDAO.getSysRoleByName(roleName);
		return list;
	}

}