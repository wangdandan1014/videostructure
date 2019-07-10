package com.sensing.core.service;

import com.sensing.core.bean.Person;
import com.sensing.core.utils.Pager;

/**
 *@author wenbo
 */
public interface IPersonService {

	public abstract Person saveNewPerson(Person person)  throws Exception;

	public Person updatePerson(Person person)  throws Exception;

	public abstract Person findPersonById(java.lang.String uuid) throws Exception;

	public abstract void removePerson(java.lang.String uuid) throws Exception;

	public Pager queryPage(Pager pager) throws Exception;

}