package com.sensing.core.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sensing.core.utils.*;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.sensing.core.utils.httputils.HttpDeal;
import com.sensing.core.utils.props.RemoteInfoUtil;
import com.sensing.core.utils.results.ResultUtils;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.sensing.core.bean.ImageFile;
import com.sensing.core.bean.SysCarbrand;
import com.sensing.core.bean.Template;
import com.sensing.core.bean.TemplateDb;
import com.sensing.core.bean.TemplateObjMotor;
import com.sensing.core.dao.ISysCarbrandDAO;
import com.sensing.core.service.ICapAttrConvertService;
import com.sensing.core.service.IJobsService;
import com.sensing.core.service.ITemplateDbService;
import com.sensing.core.service.ITemplateObjMotorService;
import com.sensing.core.service.ITemplateService;

/**
 * 车辆管理信息
 * 
 * @author wangdandan
 *
 */
@Controller
@RequestMapping("/templateObjMotor")
public class TemplateObjMotorController extends BaseController {

	private static final Log log = LogFactory.getLog(TemplateObjMotorController.class);

	@Resource
	public ITemplateObjMotorService templateObjMotorService;
	@Resource
	public IJobsService jobsService;
	@Resource
	public ITemplateService templateService;
	@Resource
	public ITemplateDbService templateDbService;
	@Resource
	public ICapAttrConvertService capAttrConvertService;
	@Resource
	public ISysCarbrandDAO sysCarbrandDAO;

	@ResponseBody
	@RequestMapping("/query")
	public ResponseBean query(@RequestBody JSONObject p) throws Exception {
		log.info("开始查询车辆信息，调用 templateObjMotor/query 接口，传递参数为: " + p);
		Pager pager = JSONObject.toJavaObject(p, Pager.class);
		ResponseBean result = new ResponseBean();
		pager = templateObjMotorService.queryPage(pager);
		result = ResultUtils.success(result, "pager", pager);
		return result;
	}

	@ResponseBody
	@RequestMapping("/update")
	public ResponseBean update(@RequestBody JSONObject m) throws Exception {
		log.info("开始更新车辆信息，调用 templateObjMotor/update 接口，传递参数为: " + m);
		TemplateObjMotor model = JSONObject.toJavaObject(m, TemplateObjMotor.class);
		ResponseBean result = new ResponseBean();
		String validateInfo = validateData(model, 2);
		if (validateInfo != null) {
			return ResultUtils.error(result, 1000, validateInfo);
		}
		// 判断目标库是否存在
		if (model.getTemplatedbId() != null) {
			TemplateDb templateDb = templateDbService.findTemplateDbById(model.getTemplatedbId(), 0);
			if (templateDb == null) {
				return ResultUtils.error(result, 100, "目标库不存在");
			}
		}
		// 校验车主信息
		model = validateOwnerContactinfo(model);
		result = templateObjMotorService.updateTemplateObjMotor(model, result);
		return result;
	}

	/**
	 * 保存车辆信息数据
	 * 
	 * @param m
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
	public ResponseBean save(@RequestBody JSONObject m) throws Exception {
		TemplateObjMotor model = JSONObject.toJavaObject(m, TemplateObjMotor.class);
		ResponseBean result = new ResponseBean();
		String validateInfo = validateData(model, 1);
		if (validateInfo != null) {
			return ResultUtils.error(result, 1000, validateInfo);
		}
		model = validateOwnerContactinfo(model);
		result = templateObjMotorService.saveNewTemplateObjMotor(m, model, result);
		return result;
	}

	/**
	 * 车辆信息转移到其他目标库
	 * 
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/cut")
	public ResponseBean cut(@RequestBody JSONObject json) throws Exception {
		ResponseBean result = new ResponseBean();
		String objUuid = json.getString("objUuid");
		String dbId = json.getString("dbId");
		if (StringUtils.isNotEmpty(objUuid) && StringUtils.isNotEmpty(dbId)) {
			TemplateDb db = templateDbService.findTemplateDbById(Integer.parseInt(dbId), 0);
			TemplateObjMotor motor = templateObjMotorService.findTemplateObjMotorById(objUuid);
			if (db != null && motor != null) {
				// 校验新目标库是否已存在该目标车辆
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("isDeleted", 0 + "");
				params.put("plateNo", motor.getPlateNo());
				params.put("templatedbId", Integer.parseInt(dbId));
				List<TemplateObjMotor> list = templateObjMotorService.queryTemplateObjMotor(params);
				if (list != null && list.size() > 0) {
					result = ResultUtils.error(result, 100, "车牌号已存在");
				} else {
					result = templateObjMotorService.cutMotorToOtherDB(objUuid, dbId, result);
				}
			} else if (db == null) {
				result = ResultUtils.error(result, 100, "转移目标库不存在");
			} else if (motor == null) {
				result = ResultUtils.error(result, 100, "转移目标信息不存在");
			}
		} else {
			result = ResultUtils.error(result, 100, "传入参数不能为空");
		}
		return result;
	}

	/**
	 * 车辆信息复制到其他目标库
	 * 
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/copy")
	public ResponseBean copy(@RequestBody JSONObject json) throws Exception {
		ResponseBean result = new ResponseBean();
		String objUuid = json.getString("objUuid");
		String dbId = json.getString("dbId");
		if (StringUtils.isNotEmpty(objUuid) && StringUtils.isNotEmpty(dbId)) {
			TemplateDb db = templateDbService.findTemplateDbById(Integer.parseInt(dbId), 0);
			TemplateObjMotor motor = templateObjMotorService.findTemplateObjMotorById(objUuid);
			if (db != null && motor != null) {
				// 校验新目标库是否已存在该目标车辆
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("isDeleted", 0 + "");
				params.put("plateNo", motor.getPlateNo());
				params.put("templatedbId", Integer.parseInt(dbId));
				List<TemplateObjMotor> list = templateObjMotorService.queryTemplateObjMotor(params);
				if (list != null && list.size() > 0) {
					result = ResultUtils.error(result, 100, "车牌号已存在");
				} else {
					result = templateObjMotorService.copyMotorToOtherDB(objUuid, dbId, result);
				}
			} else if (db == null) {
				result = ResultUtils.error(result, 100, "复制目标库不存在");
			} else if (motor == null) {
				result = ResultUtils.error(result, 100, "复制目标信息不存在");
			}
		} else {
			result = ResultUtils.error(result, 100, "传入参数不能为空");
		}
		return result;
	}

	private TemplateObjMotor validateOwnerContactinfo(TemplateObjMotor model) {
		if (StringUtils.isNotEmpty(model.getOwnerName()) || StringUtils.isNotEmpty(model.getOwnerTel())
				|| StringUtils.isNotEmpty(model.getOwnerIdno()) || StringUtils.isNotEmpty(model.getOwnerAddr())) {
			model.setOwnerContactinfo((short) (1));
		} else {
			model.setOwnerContactinfo((short) (0));
		}
		return model;
	}

	// 1-保存 2-更新
	private String validateData(TemplateObjMotor model, int i) throws Exception {
		if (model == null) {
			return "数据转换失败";
		}
		// 车牌号校验
		if (!StringUtils.isNotEmpty(model.getPlateNo())) {
			return "车牌号码不能为空";
		}
		if (ValidationUtils.checkSpecialChar(model.getPlateNo())) {
			return "车牌号码不能含有特殊字符";
		}
		if (StringUtils.isNotEmpty(model.getPlateNo()) && model.getPlateNo().length() > 8) {
			return "车牌号码限8个字符";
		}
		if (StringUtils.isNotEmpty(model.getPlateNo())) {
			String plateNo = model.getPlateNo().replace("？", "_").replaceAll("\\*", "%").replaceAll("\\?", "_");
			String upperCasePlateNo = plateNo.toUpperCase();
			model.setPlateNo(upperCasePlateNo);
		}
		// 车辆品牌校验
		Pager pager = new Pager();
		pager.getF().put("typeCode", "CAP_VEHICLE_BRAND");
		pager.setPageFlag(null);
		List<SysCarbrand> brandsList = sysCarbrandDAO.queryList(pager);
		List<String> brandList = brandsList.stream().map(j -> j.getItemValue()).collect(Collectors.toList());
		if (StringUtils.isNotEmpty(model.getVehicleBrandTag()) && !brandList.contains(model.getVehicleBrandTag())
				&& !"品牌不限".equals(model.getVehicleBrandTag())) {
			return "车辆品牌输入错误";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isDeleted", 0 + "");
		params.put("plateNo", model.getPlateNo());
		if (i == 1) {
			// 车辆库唯一标识校验
			if (model.getTemplatedbId() == null || model.getTemplatedbId() == 0) {
				return "车辆库唯一标识校验失败";
			}
			if (StringUtils.isEmptyOrNull(model.getVehicleBrandTag())) {
				model.setVehicleBrandTag(null);
			}
			params.put("templatedbId", model.getTemplatedbId());
			List<TemplateObjMotor> list = templateObjMotorService.queryTemplateObjMotor(params);
			// 车牌号是否已入库校验
			if (list != null && list.size() > 0) {
				return "车牌号已存在";
			}
			// 主模版索引校验
			if (model.getMainTemplateIndex() != null && !ValidationUtils
					.checkValueRange(model.getMainTemplateIndex() - 0, new Integer[] { 0, 1, 2, 3, 4, 5 })) {
				return "主模版索引校验失败";
			}
		}
		if (i == 2) {
			// 唯一标识校验
			if (!StringUtils.isNotEmpty(model.getUuid())) {
				return "唯一标识校验失败，唯一标识不能为空";
			}
			// 车牌号重复校验
			TemplateObjMotor motor = templateObjMotorService.findTemplateObjMotorById(model.getUuid());
			params.put("templatedbId", motor.getTemplatedbId());
			List<TemplateObjMotor> list = templateObjMotorService.queryTemplateObjMotor(params);
			if (list != null && list.size() > 0) {
				for (TemplateObjMotor templateObjMotor : list) {
					if (!templateObjMotor.getUuid().equals(model.getUuid())) {
						return "车牌号已存在";
					}
				}
			}
			// 有布控任务的单目标不允许修改
			int count = jobsService.selectTemplatedbIdCountByObjUuid(model.getUuid());
			if (count > 0) {
				return "此目标有布控任务，不允许进行编辑";
			}
		}
		// 联系方式校验
		if (StringUtils.isNotEmpty(model.getOwnerTel()) && !MatchUtil.isMobile(model.getOwnerTel())
				&& !MatchUtil.isPhone(model.getOwnerTel())) {
			return "联系电话输入错误";
		}
		// 身份证号码校验
		if (StringUtils.isNotEmpty(model.getOwnerIdno()) && !IdCardUtils.validateCard(model.getOwnerIdno())) {
			return "身份证号输入错误";
		}
		// 描述校验，长度不超过300字符
		if (StringUtils.isNotEmpty(model.getMemo()) && !ValidationUtils.checkStrLengthLess(model.getMemo(), 300)) {
			return "描述长度不能超过300字符";
		}
		// 家庭地址校验，长度不超过300字符
		if (StringUtils.isNotEmpty(model.getOwnerAddr())
				&& !ValidationUtils.checkStrLengthLess(model.getOwnerAddr(), 300)) {
			return "家庭地址不能超过300字符";
		}
		// 名字校验，长度不超过20字符
		if (StringUtils.isNotEmpty(model.getOwnerName())
				&& !ValidationUtils.checkStrLengthLess(model.getOwnerName(), 20)) {
			return "名字限20个字符";
		}
		if (model.getPlateColor() == null || model.getPlateColor() == 0) {
			model.setPlateColor((short) 30000);// 30000作为标识数传至service做判断
		}
		if (model.getVehicleClass() == null || model.getVehicleClass() == 0) {
			model.setVehicleClass((short) 30000);
		}
		if (model.getVehicleColor() == null || model.getVehicleColor() == 0) {
			model.setVehicleColor((short) 30000);
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/deleteByUuid")
	public ResponseBean deleteByUuid(@RequestBody JSONObject json) {
		log.info("开始逻辑删除车辆信息，调用 templateObjMotor/deleteByUuid 接口，传递参数为: " + json);
		ResponseBean result = new ResponseBean();
		if (json == null || json.isEmpty()) {
			log.error("请求参数非法");
			return ResultUtils.error(result, 100, "请求参数非法");
		}
		String uuid = json.getString("uuid");
		try {
			TemplateObjMotor templateObjMotor = templateObjMotorService.findTemplateObjMotorById(uuid);
			if (templateObjMotor != null) {
//				if (templateObjMotor.getTemplatedbId() == 1) {
//					int i = jobsService.selectTemplatedbIdCountByObjUuid(uuid);
//					if (i != 0 && i > 0) {
//						return ResultUtils.error(result, 100, "此目标有布控任务，不能删除");
//					}
//				}
//				if (templateObjMotor.getTemplatedbId() != 1) {
//					// 判断是否有布控任务
//					int i = jobsService.selectTemplatedbIdCount(templateObjMotor.getTemplatedbId());
//					if (i != 0 && i > 0) {
//						return ResultUtils.error(result, 100, "布控任务关联此库，不能删除");
//					}
//				}
				templateObjMotor.setIsDeleted((short) 1);
				result = templateObjMotorService.updateTemplateObjMotor(templateObjMotor, result);
			} else {
				result = ResultUtils.error(result, 100, "此车辆信息不存在");
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("调用逻辑删除车辆信息发生异常，异常信息:" + e.getMessage());
			result = ResultUtils.error(result, 100, e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public ResponseBean delete(@RequestBody String[] idarr) {
		ResponseBean result = new ResponseBean();
		try {
			for (int i = 0; idarr != null && i < idarr.length; i++) {
				templateObjMotorService.removeTemplateObjMotor(idarr[i]);
			}
			result.setError(0);
			result.setMessage("successful");
		} catch (Exception e) {
			log.error(e);
			result.setError(100);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/uploadImageBak")
	public ResponseBean uploadImageBak(@RequestBody JSONObject json) throws Exception {
		log.info("开始上传图片到服务器，调用 templateObjMotor/uploadImage 接口，传递参数为: " + json);
		Map<String, Object> map = new HashMap<>();
		ResponseBean result = new ResponseBean();
		String imageData = json.getString("imageData");
		if (StringUtils.isEmptyOrNull(imageData)) {
			return ResultUtils.error(result, 1000, "图片信息校验失败，值不能为空");
		}
		String data = "";
		String postfix = "";
		byte[] imgByte = null;
		String[] d = imageData.split(";base64,");
		if (d != null && d.length == 2) {
			postfix = "." + d[0].split(":")[1].split("/")[1];
			data = d[1];
		} else {
			throw new RuntimeException("数据格式错误");
		}
		imgByte = Base64.decodeBase64(data.getBytes("UTF-8"));
		if (imgByte == null || imgByte.length == 0) {
			throw new RuntimeException("图片错误");
		}
		String prefix = UuidUtil.getUuid();
		// 保存图片到服务器
		String seceneURI = prefix + postfix;
		String secenePut = HttpDeal.doPut(seceneURI, imgByte);
		ImageFile seceneImageFile = JSONObject.toJavaObject(JSONObject.parseObject(secenePut), ImageFile.class);
		if (seceneImageFile.getError() >= 0) {
			String imgUrl = Constants.PHOTO_URL_PERSIST + seceneImageFile.getMessage();
			map.put("imgUrl", imgUrl);
			result = ResultUtils.success(map);
		} else {
			result = ResultUtils.error(result, 1000, "未获取到图片的地址");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/uploadImage")
	public ResponseBean uploadImage(@RequestBody JSONObject json) throws Exception {
		log.info("开始上传图片到服务器，调用 templateObjMotor/uploadImage 接口，传递参数为: " + json);
		ResponseBean result = new ResponseBean();
		String validateInfo = validateData(json);
		if (validateInfo != null) {
			return ResultUtils.error(result, 1000, validateInfo);
		}
		String index = json.getString("index");
		String objUuid = json.getString("objUuid");
		String imageData = json.getString("imageData");
		if (StringUtils.isNotEmpty(objUuid)) {
			int count = jobsService.selectTemplatedbIdCountByObjUuid(objUuid);
			if (count > 0) {
				return ResultUtils.error(result, 1000, "此目标有布控任务，不允许进行编辑");
			}
		}
		List<Template> list = templateService.getTemplateByObjUuid(objUuid);
		if (list != null && list.size() > 0) {
			for (Template template : list) {
				if (String.valueOf(template.getIndex()).equals(index)) {
					return ResultUtils.error(result, 100, "该索引已存在");
				}
			}
		}
		String data = "";
		String postfix = "";
		byte[] imgByte = null;
		String[] d = imageData.split(";base64,");
		if (d != null && d.length == 2) {
			postfix = "." + d[0].split(":")[1].split("/")[1];
			data = d[1];
		} else {
			throw new RuntimeException("数据格式错误");
		}
		imgByte = Base64.decodeBase64(data.getBytes("UTF-8"));
		if (imgByte == null || imgByte.length == 0) {
			throw new RuntimeException("图片错误");
		}
		String prefix = UuidUtil.getUuid();
		// 保存图片到服务器
		String seceneURI = prefix + postfix;
		String secenePut = HttpDeal.doPut(seceneURI, imgByte);
		ImageFile seceneImageFile = JSONObject.toJavaObject(JSONObject.parseObject(secenePut), ImageFile.class);
		if (seceneImageFile.getError() >= 0) {
			TemplateObjMotor templateObjMotor = templateObjMotorService.findTemplateObjMotorById(objUuid);
			String imgUrl = seceneImageFile.getMessage();
			Template t = new Template();
			t.setUuid(prefix);
			t.setCreateTime(new Date().getTime() / 1000);
			t.setIsDeleted((short) 0);
			t.setObjUuid(objUuid);
			t.setTemplatedbId(templateObjMotor.getTemplatedbId());
			t.setIndex(new Short(index));
			t.setImageUrl(imgUrl);
			templateService.saveNewTemplate(t);
			t.setImageUrl(Constants.PHOTO_URL_PERSIST + imgUrl);
			result = ResultUtils.success(result, "model", t);
		} else {
			result = ResultUtils.error(result, 1000, "未获取到图片的地址");
		}
		return result;
	}

	private String validateData(JSONObject json) {
		if (json == null || json.isEmpty()) {
			return "请求参数非法";
		}
		String index = json.getString("index");
		String objUuid = json.getString("objUuid");
		String imageData = json.getString("imageData");
		if (StringUtils.isEmptyOrNull(index)
				|| !ValidationUtils.checkValueRange(Integer.parseInt(index) - 0, new Integer[] { 0, 1, 2, 3, 4, 5 })) {
			return "索引校验失败，不能为空，且数值需在指定范围内";
		}
		if (StringUtils.isEmptyOrNull(objUuid)) {
			return "车辆信息唯一标识校验失败，数值不能为空";
		}
		if (StringUtils.isEmptyOrNull(imageData)) {
			return "图片信息校验失败，值不能为空";
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/deleteImage")
	public ResponseBean deleteImage(@RequestBody JSONObject json) throws Exception {
		log.info("开始删除图片，调用 templateObjMotor/deleteImage 接口，传递参数为: " + json);
		ResponseBean result = new ResponseBean();
		if (json == null || json.isEmpty()) {
			log.error("请求参数非法");
			return ResultUtils.error(result, 100, "请求参数非法");
		}
		String uuid = json.getString("templateUuid");
		templateService.removeTemplate(uuid);
		// 判断是不是主模板 如果是，删除主模板id和主模板url
		TemplateObjMotor motor = templateObjMotorService.findByMainTemplateUuid(uuid);
		if (motor != null) {
			motor.setMainTemplateUrl("");
			motor.setMainTemplateUuid("");
			templateObjMotorService.updateTemplateObjMotor(motor, result);
		}
		result = ResultUtils.success();
		return result;
	}

	@ResponseBody
	@RequestMapping("/setMainTemplate")
	public ResponseBean setMainTemplate(@RequestBody JSONObject json) throws Exception {
		log.info("开始设置主模版，调用 templateObjMotor/setMainTemplate 接口，传递参数为: " + json);
		ResponseBean result = new ResponseBean();
		if (json == null || json.isEmpty()) {
			log.error("请求参数非法");
			return ResultUtils.error(result, 100, "请求参数非法");
		}
		String templateUuid = json.getString("templateUuid");
		String objUuid = json.getString("objUuid");
		if (StringUtils.isNotEmpty(objUuid)) {
			int count = jobsService.selectTemplatedbIdCountByObjUuid(objUuid);
			if (count > 0) {
				return ResultUtils.error(result, 1000, "此目标有布控任务，不允许进行编辑");
			}
		}
		TemplateObjMotor templateObjMotor = templateObjMotorService.findTemplateObjMotorById(objUuid);
		// 设置主模板的路径
		Template template = templateService.findTemplateById(templateUuid);
		if (template != null && template.getImageUrl() != null) {
			templateObjMotor.setMainTemplateUrl(template.getImageUrl());
		}
		templateObjMotor.setMainTemplateUuid(templateUuid);
		result = templateObjMotorService.updateTemplateObjMotor(templateObjMotor, result);
		return result;
	}

	// 模板下载
	@RequestMapping(value = "/exportZip", method = RequestMethod.GET)
	public void exportZip(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			ZipOutputStream zipos = null;
			try {
				zipos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
				zipos.setMethod(ZipOutputStream.DEFLATED); // 设置压缩方法
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 循环将文件写入压缩流
			try {
				String downloadFilename = "车辆导入模版.zip";// 文件的名称
				downloadFilename = URLEncoder.encode(downloadFilename, "UTF-8");// 转换中文否则可能会产生乱码
				response.setContentType("application/octet-stream");// 指明response的返回对象是文件流
				response.setHeader("Content-Disposition", "attachment;filename=" + downloadFilename);// 设置在下载框默认显示的文件名

				for (int i = 1; i < 6; i++) {
					if (i == 1) {
						String folder = "pictures/1-主图.jpg";
						String str = request.getServletContext().getRealPath("/") + "images/1-主图.jpg";
						File files = new File(str);
						zipos.putNextEntry(new ZipEntry(folder));
						InputStream fis = new FileInputStream(files);
						byte[] buffer = new byte[1024];
						int r = 0;
						while ((r = fis.read(buffer)) != -1) {
							zipos.write(buffer, 0, r);
						}
					} else {
						String folder = "pictures/1-" + i + ".jpg";
						String str = request.getServletContext().getRealPath("/") + "images/1-" + i + ".jpg";
						File files = new File(str);
						zipos.putNextEntry(new ZipEntry(folder));
						InputStream fis = new FileInputStream(files);
						byte[] buffer = new byte[1024];
						int r = 0;
						while ((r = fis.read(buffer)) != -1) {
							zipos.write(buffer, 0, r);
						}
					}
				}
				// excel写入压缩流
				String file = "车辆信息模版.xlsx";
				String str = request.getServletContext().getRealPath("/") + "template/车辆信息模版.xlsx";
				File files = new File(str);
				zipos.putNextEntry(new ZipEntry(file));
				InputStream fis = new FileInputStream(files);
				byte[] buffer = new byte[1024];
				int r = 0;
				while ((r = fis.read(buffer)) != -1) {
					zipos.write(buffer, 0, r);
				}
				// 文档写入压缩流
				String file1 = "导入说明文档.rtf";
				String str1 = request.getServletContext().getRealPath("/") + "template/导入说明文档.rtf";
				File files1 = new File(str1);
				zipos.putNextEntry(new ZipEntry(file1));
				InputStream fis1 = new FileInputStream(files1);
				byte[] buffer1 = new byte[1024];
				int r1 = 0;
				while ((r1 = fis1.read(buffer1)) != -1) {
					zipos.write(buffer1, 0, r1);
				}

				zipos.flush();
				zipos.close();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 操作结束，关闭文件
		}
	}

	// 模板导入
//	@RequestMapping(value = "/importTemplateObjMotor", method = RequestMethod.POST)
//	public String importTemplateObjMotor(@RequestParam("file") MultipartFile file) throws Exception {
//		ResponseBean result = new ResponseBean();
//		Map<String, Object> params = new HashMap<String, Object>();
//		try {
//			// 创建处理EXCEL的类
//			ReadExcel readExcel = new ReadExcel();
//			// 解析excel，获取上传的事件单
//			List<TemplateObjMotor> motorList = readExcel.getExcelInfo(file);
//			// 至此已经将excel中的数据转换到list里面了,接下来就可以操作list,可以进行保存到数据库,或者其他操作,
//
//			if (motorList != null && !motorList.isEmpty()) {
//				for (int i = 0; i < motorList.size(); i++) {
//					TemplateObjMotor motor = motorList.get(i);
//					motor = capAttrConvertService.templateObjMotorReverse(motor);
//					// 校验车辆库名称
//					List<TemplateDb> list = templateDbService.queryTemplateDbByName(motor.getTemplatedbName());
//					TemplateDb templateDb = new TemplateDb();
//					if (list != null && list.size() > 0) {
//						templateDb = list.get(0);
//						motor.setTemplatedbId(templateDb.getId());
//					} else {
//						return "第" + motor.getId() + "行'" + templateDb.getTemplateDbName() + "'名称错误";
//					}
//					// 校验数据是否已存入数据库
//					params.put("templatedbId", motorList.get(i).getTemplatedbId());
//					params.put("plateNo", motorList.get(i).getPlateNo());
//					List<TemplateObjMotor> list2 = templateObjMotorService.queryTemplateObjMotor(params);
//					if (list2 != null && list2.size() > 0) {
//						return "第" + motor.getId() + "行车牌号码重复";
//					}
//					// 校验数据格式
//					String validateInfo = validateData(motor, 1);
//					if (validateInfo != null) {
//						return "第" + motor.getId() + "行数据格式错误" + validateInfo;
//					}
//					templateObjMotorService.saveNewTemplateObjMotor(null, motor, result);
////					result = ResultUtils.success();
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "OK";
//	}

	@SuppressWarnings("finally")
	@ResponseBody
	// 模板导入
	@RequestMapping(value = "/importTemplateObjMotorZip", method = RequestMethod.POST)
	public ResponseBean importTemplateObjMotorZip(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		long l1 = new Date().getTime() / 1000;
		request.setCharacterEncoding("utf-8");
		HashMap<String, String> imageMap = new HashMap<>();
		String dbId = request.getParameter("dbId");
		TemplateDb templateDb = templateDbService.findTemplateDbById(Integer.parseInt(dbId), 0);
		if (templateDb == null) {
			return ResultUtils.REQUIRED_EMPTY_ERROR();
		}
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {

			MultipartHttpServletRequest multiRequest = multipartResolver.resolveMultipart(request);
			MultipartFile file = multiRequest.getFile("file");

			String packageName = file.getOriginalFilename(); // 上传的包名
			InputStream srcInputStream = null; // 上传的源文件流

			try {
				srcInputStream = file.getInputStream();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			String newFileName = FileUtil.TEMP + new Date().getTime() + packageName;
			File tempFile = FileUtil.saveTempFile(srcInputStream, newFileName); // 将上传的文件保存到本地

			ByteArrayOutputStream bos = null;
			if (tempFile != null) {
				if (packageName.matches(".*\\.zip")) { // 是zip压缩文件
					List<String> fileList = FileUtil.unZipFiles(tempFile, newFileName);
					ZipInputStream zs = new ZipInputStream(new FileInputStream(tempFile), Charset.forName("utf-8"));
					List<TemplateObjMotor> motorList = null;
					try {
						File f;
						byte[] bytes = null;
						for (int a = 0; a < fileList.size(); a++) {
							f = new File(fileList.get(a));
							if (!f.isDirectory()) {
								String fileName = f.getName(); // pictureName

								if (fileName.endsWith("xlsx")) {
									// 创建处理EXCEL的类
									ReadExcel readExcel = new ReadExcel();
									InputStream input = new FileInputStream(f);
									ZipSecureFile.setMinInflateRatio(-1.0d);
									motorList = readExcel.getExcelInfo(input);
								} else {
									InputStream input = new FileInputStream(f);
									bos = new ByteArrayOutputStream(1000);
									byte[] b = new byte[1000];
									int n;
									while ((n = input.read(b)) != -1) {
										bos.write(b, 0, n);
									}
									input.close();
									bos.close();
									bytes = bos.toByteArray();

									String uuid = UuidUtil.getUuid();
									String suffix = fileName.substring(fileName.lastIndexOf("."));
									String seceneURI = uuid + suffix;
									String lastfileName = f.getName().substring(0, f.getName().lastIndexOf("."));
									String[] nameNo = lastfileName.split("-");
									if (nameNo != null && nameNo.length == 2) {
										String secenePut = "";
										ImageFile seceneImageFile;
										String imgUrl = "";
										if ("主图".equals(nameNo[1].trim())) {
											secenePut = HttpDeal.doPut(seceneURI, bytes);
											seceneImageFile = JSONObject.toJavaObject(JSONObject.parseObject(secenePut),
													ImageFile.class);
											if (seceneImageFile.getError() >= 0) {
												imgUrl = seceneImageFile.getMessage();
												imageMap.put(lastfileName, imgUrl);
											} else {
												throw new RuntimeException("未获取到图片的地址");
											}
										} else if (!"主图".equals(nameNo[1].trim())
												&& StringUtils.isNumeric(nameNo[1].trim())
												&& Integer.parseInt(nameNo[1].trim()) >= 2
												&& Integer.parseInt(nameNo[1].trim()) <= 5) {
											secenePut = HttpDeal.doPut(seceneURI, bytes);
											seceneImageFile = JSONObject.toJavaObject(JSONObject.parseObject(secenePut),
													ImageFile.class);
											if (seceneImageFile.getError() >= 0) {
												imgUrl = seceneImageFile.getMessage();
												imageMap.put(lastfileName, imgUrl);
											} else {
												throw new RuntimeException("未获取到图片的地址");
											}
										}
									}
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (zs != null) {
							zs.close();
						}
						if (bos != null) {
							bos.close();
						}
						FileUtil.deleteTempFile(tempFile); // 临时文件使用完毕删除
						if (newFileName.contains(".zip")) {
							newFileName = newFileName.substring(0, newFileName.indexOf("."));
						}
						FileUtil.deleteDir(new File(newFileName)); // 临时文件使用完毕删除
						long l2 = new Date().getTime() / 1000;
						log.info("有错误信息，，，批量上传耗时-------------------" + (l2 - l1) + "s");
						return saveZipData(motorList, imageMap, templateDb);
					}
				} else {
					return ResultUtils.error(100, "请上传zip压缩包");
				}
//				else if (packageName.matches(".*\\.rar")) {
//					List<String> fileList = RarUtil.unrar(newFileName, newFileName);
//
//					List<TemplateObjMotor> motorList = null;
//					try {
//						File f;
//						byte[] bytes = null;
//						for (int a = 0; a < fileList.size(); a++) {
//							f = new File(fileList.get(a));
//							if (!f.isDirectory()) {
//								String fileName = f.getName(); // pictureName
//
//								if (fileName.endsWith("xlsx")) {
//									// 创建处理EXCEL的类
//									ReadExcel readExcel = new ReadExcel();
//									InputStream input = new FileInputStream(f);
//									ZipSecureFile.setMinInflateRatio(-1.0d);
//									motorList = readExcel.getExcelInfo(input);
//								} else {
//									InputStream input = new FileInputStream(f);
//									bos = new ByteArrayOutputStream(1000);
//									byte[] b = new byte[1000];
//									int n;
//									while ((n = input.read(b)) != -1) {
//										bos.write(b, 0, n);
//									}
//									input.close();
//									bos.close();
//									bytes = bos.toByteArray();
//
//									String uuid = UuidUtil.getUuid();
//									String suffix = fileName.substring(fileName.lastIndexOf("."));
//									String seceneURI = uuid + suffix;
//									String secenePut = HttpDeal.doPut(seceneURI, bytes);
//									ImageFile seceneImageFile = JSONObject
//											.toJavaObject(JSONObject.parseObject(secenePut), ImageFile.class);
//									if (seceneImageFile.getError() >= 0) {
//										String imgUrl = seceneImageFile.getMessage();
//										String lastfileName = f.getName().substring(0, f.getName().lastIndexOf("."));
//										imageMap.put(lastfileName, imgUrl);
//									} else {
//										throw new RuntimeException("未获取到图片的地址");
//									}
//								}
//							}
//						}
//					} catch (IOException e) {
//						e.printStackTrace();
//					} finally {
//						if (bos != null) {
//							bos.close();
//						}
//						FileUtil.deleteTempFile(tempFile); // 临时文件使用完毕删除
//						if (newFileName.contains(".rar")) {
//							newFileName = newFileName.substring(0, newFileName.indexOf("."));
//						}
//						FileUtil.deleteDir(new File(newFileName)); // 临时文件使用完毕删除
//						long l2 = new Date().getTime() / 1000;
//						log.info("有错误信息，，，批量上传耗时-------------------" + (l2 - l1) + "s");
//						return saveZipData(motorList, imageMap, templateDb);
//					}
//
//				}
			} else

			{
				return ResultUtils.error(100, "文件不能为空");
			}
		}

		long l2 = new Date().getTime() / 1000;
		log.info("完全正确，批量上传耗时-------------------" + (l2 - l1) + "s");
		return ResultUtils.success();
	}

	private ResponseBean saveZipData(List<TemplateObjMotor> motorList, HashMap<String, String> map,
			TemplateDb templateDb2) throws Exception {
		if (CollectionUtils.isEmpty(motorList) || map == null || map.size() <= 0) {
//			return ResultUtils.UNKONW_ERROR();
			return ResultUtils.error(100, "车辆信息模版上传错误，请重新上传");
		}
		ResponseBean result = new ResponseBean();
		String resultStr = "";
		if (motorList != null && !motorList.isEmpty()) {
			for (int i = 0; i < motorList.size(); i++) {
				TemplateObjMotor motor = motorList.get(i);
				motor = capAttrConvertService.templateObjMotorReverse(motor);
				motor.setTemplatedbId(templateDb2.getId());
				// 校验数据格式
				String validateInfo = validateData(motor, 1);
				if (validateInfo != null) {
					resultStr += " 第" + motor.getNo() + "行数据格式错误：" + validateInfo + ";";
					continue;
				}
				// 校验车主信息
				motor = validateOwnerContactinfo(motor);
				// 设置图片
				setZipImg(motor, map);
				templateObjMotorService.saveNewTemplateObjMotor(null, motor, result);
			}
		}
		log.info(resultStr);
		if (StringUtils.isEmptyOrNull(resultStr)) {
			return ResultUtils.success();
		} else {
			return ResultUtils.error(100, resultStr);
		}
	}

	/**
	 * 设置motor的图片集合
	 * 
	 * @param validateInfo
	 *
	 * @param motor
	 * @param map
	 * @return
	 */
	private void setZipImg(TemplateObjMotor motor, HashMap<String, String> map) {
		for (String key : map.keySet()) {
			if (StringUtils.isNotEmpty(key) && key.contains("-")) {
				String[] split = key.split("-");
				String no = split[0].trim();
				if (no.equals(motor.getNo().trim())) {
					String uuid = UuidUtil.getUuid();
					Template t = new Template();
					if ("主图".equals(split[1].trim())) {
						motor.setMainTemplateUrl(map.get(key));
						motor.setMainTemplateUuid(uuid);
						t.setIndex(((short) 0));
					} else {
						t.setIndex(Short.parseShort((Short.parseShort(split[1].trim()) - 1) + ""));
					}
					List<Template> templateList = motor.getTemplateList();
					if (templateList == null) {
						templateList = new ArrayList<>();
					}
					t.setUuid(uuid);
					t.setImageUrl(map.get(key));
					templateList.add(t);
					motor.setTemplateList(templateList);
				}
			}
		}
	}

	@SuppressWarnings("resource")
	// 模板导入
	@RequestMapping(value = "/importTemplateObjMotorZip1", method = RequestMethod.POST)
	public String importTemplateObjMotorZip1(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResponseBean result = new ResponseBean();
		Map<String, Object> params = new HashMap<String, Object>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = multipartResolver.resolveMultipart(request);
			MultipartFile file = multiRequest.getFile("file");
			String packageName = file.getOriginalFilename(); // 上传的包名
			InputStream srcInputStream = null; // 上传的源文件流

			try {
				srcInputStream = file.getInputStream();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			File tempFile = FileUtil.saveTempFile(srcInputStream, packageName); // 将上传的文件保存到本地

			if (tempFile != null) {
				if (packageName.matches(".*\\.zip")) { // 是zip压缩文件
					try {
						Charset gbk = Charset.forName("utf-8");
						ZipInputStream zs = new ZipInputStream(new FileInputStream(tempFile), gbk);
						BufferedInputStream bs = new BufferedInputStream(zs);
						ZipEntry ze;
						byte[] picture = null;
						while ((ze = zs.getNextEntry()) != null) { // 获取zip包中的每一个zip file entry
							if (!ze.isDirectory()) {
								String fileName = ze.getName(); // pictureName
								List<TemplateObjMotor> motorList = null;
								if (fileName.endsWith("xlsx")) {
									if (ze.getSize() <= 0) {
										continue;
									}
									// 创建处理EXCEL的类
									ReadExcel readExcel = new ReadExcel();
									// 解析excel，获取上传的事件单
									picture = new byte[(int) ze.getSize()]; // 读一个文件大小
									bs.read(picture, 0, (int) ze.getSize());
									InputStream is = new ByteArrayInputStream(picture);
									motorList = readExcel.getExcelInfo(is);

									if (motorList != null && !motorList.isEmpty()) {
										for (int i = 0; i < motorList.size(); i++) {
											TemplateObjMotor motor = motorList.get(i);
											motor = capAttrConvertService.templateObjMotorReverse(motor);
											// 校验车辆库名称
											List<TemplateDb> list = templateDbService
													.queryTemplateDbByName(motor.getTemplatedbName());
											TemplateDb templateDb = new TemplateDb();
											if (list != null && list.size() > 0) {
												templateDb = list.get(0);
												motor.setTemplatedbId(templateDb.getId());
											} else {
												return "第" + motor.getNo() + "行'" + templateDb.getTemplateDbName()
														+ "'名称错误";
											}
											// 校验数据是否已存入数据库
											params.put("templatedbId", motorList.get(i).getTemplatedbId());
											params.put("plateNo", motorList.get(i).getPlateNo());
											List<TemplateObjMotor> list2 = templateObjMotorService
													.queryTemplateObjMotor(params);
											if (list2 != null && list2.size() > 0) {
												return "第" + motor.getNo() + "行车牌号码重复";
											}
											// 校验数据格式
											String validateInfo = validateData(motor, 1);
											if (validateInfo != null) {
												return "第" + motor.getNo() + "行数据格式错误，" + validateInfo;
											}
											templateObjMotorService.saveNewTemplateObjMotor(null, motor, result);
										}
									}
								} else {
									if (ze.getSize() <= 0) {
										continue;
									}
									picture = new byte[(int) ze.getSize()]; // 读一个文件大小
									bs.read(picture, 0, (int) ze.getSize());
									String uuid = UuidUtil.getUuid();
									String suffix = fileName.substring(fileName.lastIndexOf("."));
									String seceneURI = uuid + suffix;
									String secenePut = HttpDeal.doPut(seceneURI, picture);
									ImageFile seceneImageFile = JSONObject
											.toJavaObject(JSONObject.parseObject(secenePut), ImageFile.class);
									if (seceneImageFile.getError() >= 0) {
//										String imgUrl = seceneImageFile.getMessage();
									} else {
										throw new RuntimeException("未获取到图片的地址");
									}
								}
							}
						}
						bs.close();
						zs.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			FileUtil.deleteTempFile(tempFile); // 临时文件使用完毕删除
		}
		return "OK";
	}

}
