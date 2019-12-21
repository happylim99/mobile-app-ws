package com.sean.ws.shared.dto;

import java.io.Serializable;

public class VehicleDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2902615886723324577L;
	private int id;
	private String vehicleType;
	private String plateNo;

	public VehicleDto() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

}
