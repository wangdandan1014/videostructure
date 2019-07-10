package com.sensing.core.service;

import com.sensing.core.bean.NonmotorVehicle;
import com.sensing.core.utils.Pager;

/**
 *@author wenbo
 */
public interface INonmotorVehicleService {

	public abstract NonmotorVehicle saveNewNonmotorVehicle(NonmotorVehicle capNonmotor)  throws Exception;

	public NonmotorVehicle updateNonmotorVehicle(NonmotorVehicle capNonmotor)  throws Exception;

	public abstract NonmotorVehicle findNonmotorVehicleById(java.lang.String uuid) throws Exception;

	public abstract void removeNonmotorVehicle(java.lang.String uuid) throws Exception;

	public Pager queryPage(Pager pager) throws Exception;

}