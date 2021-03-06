package com.sensing.core.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.sensing.core.utils.BaseController;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.ResponseBean;
import com.sensing.core.utils.StringUtils;
import com.sensing.core.utils.UuidUtil;
import com.sensing.core.utils.ValidationUtils;
import com.sensing.core.utils.results.ResultEnum;
import com.sensing.core.utils.results.ResultUtils;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensing.core.alarm.DataInitService;
import com.sensing.core.bean.SysTypecode;
import com.sensing.core.bean.TemplateDb;
import com.sensing.core.service.IJobsService;
import com.sensing.core.service.ISysTypecodeService;
import com.sensing.core.service.ITemplateDbService;

/**
 * 车辆库管理
 * 
 * @author wangdandan
 *
 */

@Controller
@RequestMapping("/templateDb")
public class TemplateDbController extends BaseController {

	private static final Log log = LogFactory.getLog(TemplateDbController.class);

	@Resource
	public ITemplateDbService templateDbService;
	@Resource
	public IJobsService jobsService;
	@Resource
	public ISysTypecodeService sysTypecodeService;

	@ResponseBody
	@RequestMapping("/query")
	public ResponseBean query(@RequestBody JSONObject p) throws Exception {
		Pager pager = JSONObject.toJavaObject(p, Pager.class);
		ResponseBean result = new ResponseBean();
		pager = templateDbService.queryPage(pager);
		result = ResultUtils.success(result, "pager", pager);
		return result;
	}

	@ResponseBody
	@RequestMapping("/queryById")
	public ResponseBean queryById(@RequestBody JSONObject json) {
		ResponseBean result = new ResponseBean();
		if (json == null || json.isEmpty()) {
			log.error("请求参数非法");
			result.setError(100);
			result.setMessage("请求参数非法");
			return result;
		}
		String id = json.getString("id");
		try {
			TemplateDb templateDb = templateDbService.findTemplateDbById(Integer.parseInt(id), 0);
			result.getMap().put("model", templateDb);
			result.setError(0);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			result.setError(100);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/queryTemplateDb")
	public ResponseBean queryTemplateDb(@RequestBody JSONObject json) {
		ResponseBean result = new ResponseBean();
		Map<String, Object> params = new HashMap<String, Object>();
		if (json == null || json.isEmpty()) {
			log.error("请求参数非法");
			result.setError(100);
			result.setMessage("请求参数非法");
			return result;
		}
		String id = json.getString("id");
		String isUsed = json.getString("isUsed");
		String ids = json.getString("ids");
		String[] strs = ids.split(",");
		List<String> idList = Arrays.asList(strs);

		params.put("isUsed", isUsed);
		params.put("idList", idList);
		try {
			List<TemplateDb> templateDb = templateDbService.queryTemplateDb(params);
			result.getMap().put("list", templateDb);
			result.setError(0);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			result.setError(100);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/update")
	public ResponseBean update(@RequestBody JSONObject m) throws Exception {
		log.info("开始更新车辆库信息，调用 templateDb/update 接口，传递参数为: " + m);
		TemplateDb model = JSONObject.toJavaObject(m, TemplateDb.class);
		ResponseBean result = new ResponseBean();
		String validateInfo = validateData(model, 2);
		if (validateInfo != null) {
			return ResultUtils.error(result, ResultEnum.DATA_ERROR.getCode(), validateInfo);
		}
		// 判断目标库是否存在
		TemplateDb templateDb = templateDbService.findTemplateDbById(model.getId(), 0);
		if (templateDb == null) {
			return ResultUtils.error(result, 100, "目标库不存在");
		}
		// 停用目标库
		if (model.getIsUsed() != null && model.getIsUsed() == 2) {
			// 判断jobs_templatedb表中的templatedb_id是否存在，若存在，不能停用
			int i = jobsService.selectTemplatedbIdCount(model.getId());
			if (i > 0) {
				return ResultUtils.error(result, 100, "目标库正在使用中，无法停用，请先调整布控任务");
			}
		}
		model = templateDbService.updateTemplateDb(model);
		result = ResultUtils.success(result, "model", model);
		return result;
	}

	/**
	 * 保存目标库信息
	 * 
	 * @param m
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
	public ResponseBean save(@RequestBody JSONObject m) throws Exception {
		log.info("开始保存目标库信息，调用 templateDb/save 接口，传递参数为: " + m);
		TemplateDb model = JSONObject.toJavaObject(m, TemplateDb.class);
		ResponseBean result = new ResponseBean();
		String validateInfo = validateData(model, 1);
		if (validateInfo != null) {
			return ResultUtils.error(result, ResultEnum.DATA_ERROR.getCode(), validateInfo);
		}
		model = templateDbService.saveNewTemplateDb(model);
		result = ResultUtils.success(result, "model", model);
		return result;
	}

	// 1-保存 2-更新
	private String validateData(TemplateDb model, int i) throws Exception {
		if (model == null) {
			return "数据转换失败";
		}
		List<TemplateDb> list = templateDbService.queryTemplateDbByName(model.getTemplateDbName());
		if (i == 1) {
			// 名称校验，必填项，长度不超过20
			if (!StringUtils.isNotEmpty(model.getTemplateDbName())
					|| !ValidationUtils.checkStrLengthLess(model.getTemplateDbName(), 20)) {
				return "名称不能为且文字限20个字符";
			}
			// 名称重复校验
			if (list != null && list.size() > 0) {
				return "'" + model.getTemplateDbName() + "'车辆库已存在";
			}
			// 库类型校验
//			if (model.getTemplateDbType() == null
//					|| !ValidationUtils.checkValueRange(model.getTemplateDbType() - 0, new Integer[] { 1, 2, 3 })) {
			if (model.getTemplateDbType() == null) {
				return "库类型校验失败";
			}
			// 描述校验，长度不超过300字符
			if (model.getTemplateDbDescription() != null
					&& !ValidationUtils.checkStrLengthLess(model.getTemplateDbDescription(), 300)) {
				return "描述文字限300个字符";
			}
			// 启用状态校验，必填项，2不启用 1启用 0没意义
			if (model.getIsUsed() == null
					|| !ValidationUtils.checkValueRange(model.getIsUsed() - 0, new Integer[] { 0, 1, 2 })) {
				return "启用状态校验失败，不能为空，且数值需在指定范围内";
			}
		}
		if (i == 2) {
			if (model.getId() == null || model.getId().equals("")) {
				return "id校验失败，不能为空";
			}
			// 名称重复校验
			if (list != null && list.size() > 0) {
				for (TemplateDb templateDb : list) {
					if (templateDb.getId() != model.getId()) {
						return "'" + model.getTemplateDbName() + "'车辆库已存在";
					}
				}
			}
		}
		// 删除标记校验，删除标记 0-未删除 1-删除
		if (model.getIsDeleted() != null
				&& !ValidationUtils.checkValueRange(model.getIsDeleted() - 0, new Integer[] { 0, 1 })) {
			return "删除标记校验失败，不能为空，且数值需在指定范围内";
		}

		return null;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public ResponseBean delete(@RequestBody String[] idarr) {
		ResponseBean result = new ResponseBean();
		try {
			for (int i = 0; idarr != null && i < idarr.length; i++) {
				templateDbService.removeTemplateDb(new Integer(idarr[i]));
			}
			result.setError(0);
			result.setMessage("successful");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			result.setError(100);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 逻辑删除车辆库
	 * 
	 * 布控任务的布控中、暂停中、待启动和休息中关联车辆库时不能删除
	 * 
	 * 已撤控和已完成时车辆库可以删除
	 * 
	 * @param json
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteById")
	public ResponseBean deleteById(@RequestBody JSONObject json) {
		log.info("开始逻辑删除车辆库信息，调用 templateDb/deleteById 接口，传递参数为: " + json);
		ResponseBean result = new ResponseBean();
		if (json == null || json.isEmpty()) {
			log.error("请求参数非法");
			result.setError(100);
			result.setMessage("请求参数非法");
			return result;
		}
		String id = json.getString("id");
		try {
			if (Integer.parseInt(id) == 1) {
				return ResultUtils.error(100, "单目标库不可删除");
			}
			TemplateDb templateDb = templateDbService.findTemplateDbById(Integer.parseInt(id), 0);
			if (templateDb != null) {
				int i = jobsService.selectTemplatedbIdCount(templateDb.getId());
				if (i != 0 && i > 0) {
					result.setError(100);
					result.setMessage("目标库正在使用中，无法删除，请先调整布控任务");
					return result;
				}
				templateDb.setIsDeleted((short) 1);
				templateDbService.updateTemplateDb(templateDb);
				result.setError(0);
				result.setMessage("successful");
			} else {
				result.setError(100);
				result.setMessage("此车辆库不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("调用逻辑删除车辆库信息发生异常，异常信息:" + e.getMessage());
			result.setError(100);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 保存目标库类型
	 * 
	 * @param m
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/saveDBType")
	public ResponseBean saveDBType(@RequestBody JSONObject p) throws Exception {
		log.info("开始保存目标库类型信息，调用 templateDb/saveDBType 接口，传递参数为: " + p);
		String itemValue = p.getString("itemValue");
		if (StringUtils.isEmptyOrNull(itemValue)) {
			return ResultUtils.REQUIRED_EMPTY_ERROR();
		}
//		List<SysTypecode> allList = sysTypecodeService.getSysTypecodeByTypeCodeAndItemValue("TEMPLATEDB_TYPE", null);
//		if (allList != null && allList.size() > 9) {
//			return ResultUtils.error(100, "库类型最多添加10个，请先删除其他库类型");
//		}
		if (StringUtils.isNotEmpty(itemValue) && "单目标布控".equals(itemValue.trim())) {
			return ResultUtils.error(100, "单目标布控类型已存在，请重新添加");
		}
		List<SysTypecode> list = sysTypecodeService.querySysTypecodeByTypeCodeAndItemValue("TEMPLATEDB_TYPE",
				itemValue);
		if (list != null && list.size() > 0) {
			return ResultUtils.error(100, itemValue + "已存在，请重新添加");
		}
		Integer itemId = sysTypecodeService.getMaxItemIdByMemo("库类型");
		SysTypecode model = new SysTypecode();
		model.setTypeCode("TEMPLATEDB_TYPE");
		model.setMemo("库类型");
		model.setItemValue(itemValue);
		if (itemId == null) {
			model.setItemId(1);
		} else {
			model.setItemId(itemId + 1);
		}
		sysTypecodeService.saveNewSysTypecode(model);
		ResponseBean result = new ResponseBean();
		result = ResultUtils.success(result, "model", model);
		return result;
	}

	/**
	 * 查询库类型
	 * 
	 * @param p
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/queryDBType")
	public ResponseBean queryDBType(@RequestBody JSONObject p) throws Exception {
		log.info("开始查询目标库类型信息，调用 templateDb/queryDBType 接口，传递参数为: " + p);
		ResponseBean result = new ResponseBean();
		String excludeId = p.getString("excludeId");
		String itemValue = p.getString("itemValue");
		if (StringUtils.isNotEmpty(itemValue) && "单目标布控".equals(itemValue.trim())) {
			return ResultUtils.error(100, "单目标布控类型不允许修改");
		}
		List<SysTypecode> list = sysTypecodeService.getSysTypecodeByTypeCodeAndItemValue("TEMPLATEDB_TYPE", itemValue,
				excludeId);
		result = ResultUtils.success(result, "list", list);
		return result;
	}

	/**
	 * 根据id查询库类型
	 * 
	 * @param p
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/queryDBTypeByItemId")
	public ResponseBean queryDBTypeByItemId(@RequestBody JSONObject p) throws Exception {
		log.info("开始查询目标库类型信息，调用 templateDb/queryDBTypeByItemId 接口，传递参数为: " + p);
		ResponseBean result = new ResponseBean();
		String itemId = p.getString("itemId");
		if (itemId == null) {
			ResultUtils.REQUIRED_EMPTY_ERROR();
		}
		List<SysTypecode> list = sysTypecodeService.querySysTypeCodeByTypeCodeAndItemId("TEMPLATEDB_TYPE", itemId);
		if (list != null && list.size() > 0) {
			result = ResultUtils.success(result, "model", list.get(0));
		} else {
			result = ResultUtils.NONE();
		}
		return result;
	}

	/**
	 * 更新目标库类型
	 * 
	 * @param p
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping("/updateDBType")
	public ResponseBean updateDBType(@RequestBody JSONObject m) {
		log.info("开始更新目标库类型信息，调用 templateDb/updateDBType 接口，传递参数为: " + m);
		ResponseBean result = new ResponseBean();
		SysTypecode model = JSONObject.toJavaObject(m, SysTypecode.class);
		Integer itemId = model.getItemId();
		if (itemId == null) {
			return ResultUtils.REQUIRED_EMPTY_ERROR();
		}
		if (itemId == 1) {
			return ResultUtils.error(100, "单目标布控类型不允许修改");
		}
		String itemValue = model.getItemValue();
		if (StringUtils.isNotEmpty(itemValue)) {
			List<SysTypecode> list = sysTypecodeService.getSysTypecodeByItemValue("TEMPLATEDB_TYPE", itemValue);
			if (list != null && list.size() > 0) {
				return ResultUtils.error(100, itemValue + "已存在，请重新添加");
			}
		}
		try {
			sysTypecodeService.updateSysTypecodeNew(model);
			result = ResultUtils.success(model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新目标库类型信息错误，错误信息为：" + e.getMessage());
			result = ResultUtils.error(100, "编辑失败");
		}
		return result;
	}

	/**
	 * 删除库类型
	 * 
	 * @param p
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/deleteDBType")
	public ResponseBean deleteDBType(@RequestBody JSONObject p) throws Exception {
		log.info("开始删除目标库类型信息，调用 templateDb/deleteDBType 接口，传递参数为: " + p);
		ResponseBean result = new ResponseBean();
		Integer itemId = p.getInteger("itemId");
		if (itemId == null) {
			return ResultUtils.REQUIRED_EMPTY_ERROR();
		}
		if (itemId == 1) {
			return ResultUtils.error(100, "单目标布控类型不允许删除");
		}
		try {
			// 该库类型关联布控任务时不可删除
			int count = sysTypecodeService.relevanceJobsCount(itemId);
			if (count > 0) {
				return ResultUtils.error(100, "该库类型关联布控任务不可删除");
			}
			List<TemplateDb> list = templateDbService.queryTemplateDbByType(itemId);
			if (list != null && list.size() > 0) {
				return ResultUtils.error(100, "该库类型已被使用，不可删除\n请更换已使用的库类型，再进行删除");
			}
			sysTypecodeService.deleteByItemIdAndTypeCode(itemId, "TEMPLATEDB_TYPE");
			result = ResultUtils.success();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除库类型失败,失败信息为：" + e.getMessage());
			result = ResultUtils.error(100, "删除库类型失败");
		}
		return result;
	}
}
