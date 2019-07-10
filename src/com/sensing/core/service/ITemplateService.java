package com.sensing.core.service;

import java.util.List;

import com.sensing.core.bean.Template;
import com.sensing.core.utils.Pager;

/**
 * @author mingxingyu
 */
public interface ITemplateService {

	public abstract Template saveNewTemplate(Template template) throws Exception;

	public Template updateTemplate(Template template) throws Exception;

	public abstract Template findTemplateById(java.lang.String uuid) throws Exception;

	public abstract void removeTemplate(java.lang.String uuid) throws Exception;

	public Pager queryPage(Pager pager) throws Exception;

	public abstract void logicalDeleted(Integer templatedbId) throws Exception;

	public abstract List<Template> getTemplateByObjUuid(String objUuid) throws Exception;

}