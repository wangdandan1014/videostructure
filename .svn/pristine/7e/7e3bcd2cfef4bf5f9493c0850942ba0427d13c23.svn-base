package com.sensing.core.service.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.sensing.core.bean.Template;
import com.sensing.core.utils.Exception.BussinessException;
import com.sensing.core.utils.Pager;
import com.sensing.core.service.ITemplateService;
import com.sensing.core.dao.ITemplateDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author mingxingyu
 */
@Service
public class TemplateServiceImpl implements ITemplateService {

	private static final Log log = LogFactory.getLog(ITemplateService.class);

	@Resource
	public ITemplateDAO templateDAO;

	public TemplateServiceImpl() {
		super();
	}

	@Override
	public Template saveNewTemplate(Template template) throws Exception {
		template.setCreateTime(new Date().getTime() / 1000);
		template.setIsDeleted((short) 0);
		templateDAO.saveTemplate(template);
		return template;
	}

	@Override
	public Template updateTemplate(Template template) throws Exception {
		templateDAO.updateTemplate(template);
		return template;
	}

	@Override
	public Template findTemplateById(java.lang.String uuid) throws Exception {
		try {
			return templateDAO.getTemplate(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public void removeTemplate(String uuid) throws Exception {
		try {
			templateDAO.removeTemplate(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public Pager queryPage(Pager pager) throws Exception {
		try {
			List<Template> list = templateDAO.queryList(pager);
			int totalCount = templateDAO.selectCount(pager);
			pager.setTotalCount(totalCount);
			pager.setResultList(list);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return pager;
	}

	@Override
	public void logicalDeleted(Integer templatedbId) throws Exception {
		templateDAO.logicalDeleted(templatedbId);
	}

	@Override
	public List<Template> getTemplateByObjUuid(String objUuid) throws Exception {
		List<Template> list = templateDAO.getTemplateByObjUuid(objUuid);
		return list;
	}

}