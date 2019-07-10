package com.sensing.core.service;

import java.util.List;
import java.util.Map;

import com.sensing.core.bean.TemplateDb;
import com.sensing.core.utils.Pager;

/**
 * @author mingxingyu
 */
public interface ITemplateDbService {

	public abstract TemplateDb saveNewTemplateDb(TemplateDb templateDb) throws Exception;

	public TemplateDb updateTemplateDb(TemplateDb templateDb) throws Exception;

	public abstract TemplateDb findTemplateDbById(Integer id, Integer isDeleted) throws Exception;

	public List<TemplateDb> queryTemplateDb(Map<String, Object> param);

	public abstract void removeTemplateDb(java.lang.Integer id) throws Exception;

	public Pager queryPage(Pager pager) throws Exception;

	public abstract List<TemplateDb> queryTemplateDbByName(String templatedbName);

	public abstract List<TemplateDb> queryTemplateDbByType(Integer itemId) throws Exception;

}