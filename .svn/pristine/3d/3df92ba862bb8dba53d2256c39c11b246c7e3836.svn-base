package com.sensing.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sensing.core.bean.Template;
import com.sensing.core.utils.Pager;

/**
 * @author mingxingyu
 */
public interface ITemplateDAO {
	public int saveTemplate(Template template) throws Exception;

	public Template getTemplate(java.lang.String uuid) throws Exception;

	public int removeTemplate(java.lang.String uuid) throws Exception;

	public int updateTemplate(Template template) throws Exception;

	public List<Template> queryList(Pager pager) throws Exception;

	public int selectCount(Pager pager) throws Exception;

	public List<Template> getTemplateByObjUuid(String objUuid) throws Exception;

	public void logicalDeleted(@Param("templatedbId")Integer templatedbId);

}
