package com.sensing.core.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sensing.core.bean.Channel;
import com.sensing.core.bean.Regions;
import com.sensing.core.bean.RegionsTree;
import com.sensing.core.bean.RpcLog;
import com.sensing.core.bean.SysUser;
import com.sensing.core.service.IAuthorizationService;
import com.sensing.core.service.IChannelService;
import com.sensing.core.service.IRegionsService;
import com.sensing.core.service.IRpcLogService;
import com.sensing.core.service.ISysUserService;
import com.sensing.core.utils.BaseController;
import com.sensing.core.utils.Constants;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.ResponseBean;
import com.sensing.core.utils.StringUtils;
import com.sensing.core.utils.ValidationUtils;

/**
 * @author admin 分组管理
 */
@Controller
@RequestMapping("/regions")
@SuppressWarnings("all")
public class RegionsController extends BaseController {

	private static final Log log = LogFactory.getLog(RegionsController.class);

	@Resource
	public IRegionsService regionsService;
	@Resource
	public IChannelService channelService;
	@Resource
	public IAuthorizationService authorizationService;
	@Resource
	public ISysUserService sysUserService;

	@ResponseBody
	@RequestMapping("/query")
	public ResponseBean query(@RequestBody JSONObject p) {
		ResponseBean result = new ResponseBean();
		try {
			Pager pager = JSONObject.toJavaObject(p, Pager.class);
			if (pager.getPageRows() <= 0) {
				result.setError(100);
				result.setMessage("pageRows必须大于0");
			} else if (pager.getPageNo() <= 0) {
				result.setError(100);
				result.setMessage("pageNo必须大于0");
			} else {
				pager = regionsService.queryPage(pager);
				result.getMap().put("pager", pager);
				result.setError(0);
				result.setMessage("successful");
			}

		} catch (Exception e) {
			log.error(e);
			result.setError(-1);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/queryAllChild")
	public ResponseBean queryAllChild(@RequestBody JSONObject json, HttpServletRequest request) {
		ResponseBean result = new ResponseBean();
		List<Regions> resList = new ArrayList<Regions>();
		String del = json.getString("lx");
		Integer type = json.getInteger("type");
		resList = regionsService.queryAllChilds(del, type);
		result.setError(0);
		result.getMap().put("list", resList);
		result.setMessage("successful");
		return result;
	}

	@ResponseBody
	@RequestMapping("/update")
	public ResponseBean update(@RequestBody JSONObject m, HttpServletRequest request) {
		ResponseBean result = new ResponseBean();
		Regions model = new Regions();
		try {
			model = JSONObject.toJavaObject(m, Regions.class);
			String valRes = validateParam(model, 2);
			if (StringUtils.isNotEmpty(valRes)) { // 如果验证通过，则进行下一步，否则返回错误信息
				result.setError(6002);
				result.setMessage(valRes);
			} else {
				model = regionsService.updateRegions(model);
				result.getMap().put("model", model);
				result.setError(0);
				result.setMessage("successful");
			}
		} catch (Exception e) {
			log.error(e);
			result.setError(-1);
			result.setMessage(e.getMessage());
		}

		return result;
	}

	/**
	 * save data
	 */
	@ResponseBody
	@RequestMapping("/save")
	public ResponseBean save(@RequestBody JSONObject m, HttpServletRequest request) {
		Long d1 = new Date().getTime();
		ResponseBean result = new ResponseBean();
		String userId = "";
		SysUser su = new SysUser();
		Regions model = new Regions();
		try {
			model = JSONObject.toJavaObject(m, Regions.class);
			String valRes = validateParam(model, 1);
			if (StringUtils.isNotEmpty(valRes)) { // 如果验证通过，则进行下一步，否则返回错误信息
				result.setError(6001);
				result.setMessage(valRes);
			} else {
				int level = 0;
				// 父id为0，不允许添加根节点
//				if (model.getParentId() == 0) {
//					result.setError(6001);
//					result.setMessage("根节点已存在，不允许重复添加");
//					return result;
//				}
				// 判断该分组下是否有通道，如果有，不允许添加分组
				List<Channel> list = channelService.queryChannelListUnderRegionByRegionId(model.getParentId());
				if (list != null && list.size() > 0) {
					result.setError(6001);
					result.setMessage("该分组下已有通道，不允许添加分组");
					return result;
				}
				// 如查父区域id为空，说明为顶层区域
				if (model.getParentId() == null) {
					model.setParentId(0);
					model.setRegionLevel(0);
				} else {
					Regions parentRegion = regionsService.findRegionsById(model.getParentId());
					if (parentRegion != null) {
						level = parentRegion.getRegionLevel() + 1;
					}
					model.setRegionLevel(level);
				}
				if (model.getRegionLevel() > 10) {
					result.setError(100);
					result.setMessage("分组层级不能超过10层");
					return result;
				}
				model = regionsService.saveNewRegions(model);
				result.getMap().put("model", model);
				result.setError(0);
				result.setMessage("successful");
			}
		} catch (Exception e) {
			log.error(e);
			result.setError(-1);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public ResponseBean delete(@RequestBody JSONObject m, HttpServletRequest request, HttpServletResponse response) {
		ResponseBean result = new ResponseBean();
		Long d1 = new Date().getTime();
		String userId = "";
		SysUser su = new SysUser();
		Integer id = Integer.valueOf(m.getString("idarr"));
		try {
			int c = 0;
			List<Regions> list = regionsService.queryChildsById(id, "");
			if (list == null || list.size() > 0) {
				result.setError(100);
				result.setMessage("该分组下还有通道或分组信息，请先删除，再重新尝试");
			} else {
				int cc = regionsService.removeRegions(id);
				if (cc == 1) {
					result.setError(0);
					result.setMessage("successful");
				} else {
					result.setError(100);
					result.setMessage("删除失败，分组id不存在或已删除");
				}
			}
		} catch (Exception e) {
			log.error(e);
			result.setError(-1);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 保存区域信息之前验证 type=1验证save方法，type=2验证update方法 author dongsl date
	 * 2017年8月8日上午11:42:40
	 *
	 * @throws Exception
	 */
	private String validateParam(Regions region, int type) throws Exception {

		if (type == 2) {
			// 通道uuid是否存在
			if (region.getId() == null) {
				return "分组id不能为空";
			}
		}
		// 判断parentId是否存在，不存在返回错误信息
		if (region.getParentId() != null && region.getParentId() != 0) {
			Regions parentRegion = regionsService.findRegionsById(region.getParentId());
			if (parentRegion == null) {
				return "parentId所指的分组不存在";
			}
		}

		// 区域名称是否为空
		if (!StringUtils.isNotEmpty(region.getRegionName())) {
			return "分组名称不能为空";
		}

		// 区域名称长度不超过16
		if (!ValidationUtils.checkStrLengthWithNull(region.getRegionName(), 0, 16)) {

			return "分组名称限16个字符";
		}
		// 区域描述不超过64
		if (!ValidationUtils.checkStrLengthWithNull(region.getRegionDescription(), 0, 64)) {
			return "分组描述限64个字符";
		}
		List<Regions> list;
		if (region.getParentId() != null) {
			list = regionsService.queryRegionByParentId(String.valueOf(region.getParentId()), region.getRegionName(),
					region.getId());
		} else {
			list = regionsService.queryRegionByParentId(String.valueOf(0), region.getRegionName(), region.getId());
		}

		if (!"分组".equals(region.getRegionName()) && list != null && list.size() > 0) {
			return "当前分组下已存在名为：'" + region.getRegionName() + "'的分组";
		}
		return "";
	}

	/**
	 * @Description: 根据区域id查询直属子区域和通道, 如所传id为空，则默认查询一级节点
	 * @author dongsl
	 * @Date 2018年7月19日下午2:02:45
	 */
	@ResponseBody
	@RequestMapping("/queryChildsById")
	public ResponseBean queryChildsById(@RequestBody JSONObject json) {
		ResponseBean result = new ResponseBean();
		List<Regions> resList = new ArrayList<Regions>();
		try {
			Integer regionId = json.getInteger("id");
			String del = json.getString("del");
			resList = regionsService.queryChildsById(regionId, del);
			result.setError(0);
			result.getMap().put("list", resList);
			result.setMessage("successful");
		} catch (Exception e) {
			result.setError(-1);
			result.setMessage("查询异常");
		}
		return result;
	}

	/**
	 * @Description: 根据区域id查询直属子区域和通道, 如所传id为空，则默认查询一级节点
	 * @author dongsl
	 * @Date 2018年7月19日下午2:02:45
	 */
	@ResponseBody
	@RequestMapping("/queryAllChildsById")
	public ResponseBean queryAllChildsById(@RequestBody JSONObject json) {
		ResponseBean result = new ResponseBean();
		List<Regions> resList = new ArrayList<Regions>();
		try {
			Integer regionId = json.getInteger("id");
			resList = regionsService.queryChildsById(regionId, null);
			result.setError(0);
			result.getMap().put("list", resList);
		} catch (Exception e) {
			result.setError(-1);
			result.setMessage("查询异常");
		}
		return result;
	}

	@RequestMapping("/getRegionsById")
	@ResponseBody
	public ResponseBean getRegionsByID(@RequestBody JSONObject json) {
		ResponseBean result = new ResponseBean();
		String id = json.getString("id");
		try {
			Regions regions = regionsService.findRegionsById(Integer.valueOf(id));
			result.setError(0);
			result.getMap().put("data", regions);
			result.setMessage("successful");
		} catch (Exception e) {
			result.setError(-1);
			result.setMessage("操作失败，请重试");
		}
		return result;
	}

	@RequestMapping("/findAllRecursion")
	@ResponseBody
	public ResponseBean findAllRecursion(HttpServletResponse response) {
		ResponseBean result = new ResponseBean();
		try {
			List<RegionsTree> regions = regionsService.findAllRecursion();
			result.setError(0);
			result.getMap().put("data", regions);
		} catch (Exception e) {
			result.setError(-1);
			result.setMessage(e.getMessage());
		}
		return result;
	}
}
