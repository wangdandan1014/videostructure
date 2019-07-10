package com.sensing.core.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.sensing.core.bean.Person;
import com.sensing.core.utils.Exception.BussinessException;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.UuidUtil;
import com.sensing.core.service.IPersonService;
import com.sensing.core.dao.IPersonDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *@author wenbo
 */
@Service
public class PersonServiceImpl implements IPersonService{


	private static final Log log = LogFactory.getLog(IPersonService.class);

	@Resource
	public IPersonDAO personDAO;

	public PersonServiceImpl() {
		super();
	}

	@Override
	public Person saveNewPerson(Person person)  throws Exception{
		try {
			String uuid = UuidUtil.getUuid();
			person.setUuid(uuid);
			personDAO.savePerson(person);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return person;
	}

	@Override
	public Person updatePerson(Person person)  throws Exception{
		personDAO.updatePerson(person);
		return person;
	}

	@Override
	public Person findPersonById(java.lang.String uuid) throws Exception{
		try {
			return personDAO.getPerson(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public void removePerson(String uuid) throws Exception{
		try {
			personDAO.removePerson(uuid);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
	}

	@Override
	public Pager queryPage(Pager pager) throws Exception{
		try {
			List<Person> list = personDAO.queryList(pager);
			int totalCount = personDAO.selectCount(pager);
			pager.setTotalCount(totalCount);
			pager.setResultList(list);
		} catch (Exception e) {
			log.error(e);
			throw new BussinessException(e);
		}
		return pager;
	}

}