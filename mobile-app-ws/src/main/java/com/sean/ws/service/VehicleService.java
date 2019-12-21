package com.sean.ws.service;

import java.util.List;

import com.sean.ws.io.entity.UserEntity;
import com.sean.ws.io.entity.VehicleEntity;
import com.sean.ws.shared.dto.VehicleDto;

public interface VehicleService{
	
	UserEntity createVehicle(VehicleDto vehicle);
	VehicleDto storeVehicle(VehicleEntity vehicle);
	List<Object> listObject();
	
}
