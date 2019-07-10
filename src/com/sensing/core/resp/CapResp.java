package com.sensing.core.resp;

import com.sensing.core.bean.MotorVehicle;
import com.sensing.core.bean.NonmotorVehicle;
import com.sensing.core.bean.Person;

public class CapResp implements Comparable<CapResp> {

	private Person capPeople;
	private MotorVehicle motorVehicle;
	private NonmotorVehicle capNonmotor;
	private String score;

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public Person getCapPeople() {
		return capPeople;
	}

	public void setCapPeople(Person capPeople) {
		this.capPeople = capPeople;
	}

	public NonmotorVehicle getCapNonmotor() {
		return capNonmotor;
	}

	public void setCapNonmotor(NonmotorVehicle capNonmotor) {
		this.capNonmotor = capNonmotor;
	}

	public MotorVehicle getMotorVehicle() {
		return motorVehicle;
	}

	public void setMotorVehicle(MotorVehicle motorVehicle) {
		this.motorVehicle = motorVehicle;
	}

	@Override
	public int compareTo(CapResp cap) {
		int c = 0;
		if (cap.getCapPeople() != null) {
			c = (int) (cap.capPeople.getCapTime() - this.capPeople.getCapTime());
		}
		if (cap.getMotorVehicle() != null) {
			c = (int) (cap.motorVehicle.getCapTime() - this.motorVehicle.getCapTime());
		}
		if (cap.getCapNonmotor() != null) {
			c = (int) (cap.capNonmotor.getCapTime() - this.capNonmotor.getCapTime());
		}
		return c;
	}
}
