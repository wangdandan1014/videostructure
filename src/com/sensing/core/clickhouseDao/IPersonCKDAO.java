package com.sensing.core.clickhouseDao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sensing.core.bean.Person;
import com.sensing.core.utils.Pager;

/**
 * @author wenbo
 */
public interface IPersonCKDAO {

	public int savePerson(@Param(value = "person") Person person, @Param(value = "taskIds") List<String> taskUuids)
			throws Exception;

	public List<Person> queryByUuid(@Param(value = "uuid") String uuid, @Param(value = "capTime") Long capTime)
			throws Exception;

	public List<Person> queryList(@Param(value = "pager") Pager pager,
			@Param(value = "deviceIds") List<String> deviceIds) throws Exception;

	public int selectCount(@Param(value = "pager") Pager pager, @Param(value = "deviceIds") List<String> deviceIds)
			throws Exception;

	public List<Person> queryByUuids(@Param(value = "uuids") List<String> uuids) throws Exception;

	public List<Person> queryListByTaskId(@Param("pager") Pager pager, @Param("deviceIds") List<String> deviceIds);

	public int selectCountByTaskId(@Param("pager") Pager pager, @Param("deviceIds") List<String> deviceIds);

	public List<Person> queryOffVideoListByTaskId(@Param("pager") Pager pager,
			@Param("deviceIds") List<String> deviceIds);

}
