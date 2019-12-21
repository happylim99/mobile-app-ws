package com.sean.ws.ui.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sean.ws.io.entity.UserEntity;
import com.sean.ws.io.entity.VehicleEntity;
import com.sean.ws.service.VehicleService;
import com.sean.ws.shared.dto.VehicleDto;
import com.sean.ws.ui.model.response.VehicleRest;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
	
	@Autowired
	VehicleService vehicleService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@GetMapping("/create")
	public UserEntity createVehicle()
	{
		System.out.println("starttttttttttttt");
		VehicleDto vehicleDto = new VehicleDto();
		vehicleDto.setId(1);
		vehicleDto.setVehicleType("Bus");
		System.out.println("kkkkkkkkkkkkk");
		vehicleDto.setPlateNo("abc1234");
		System.out.println("eeeeeeeeeeeeeeeee");
//		VehicleDto storedDetails = vehicleService.createVehicle(vehicleDto);
//		VehicleRest vehicleRest = modelMapper.map(storedDetails, VehicleRest.class);
		return vehicleService.createVehicle(vehicleDto);
		//return vehicleRest;
	}
	
	@GetMapping("/store")
	public VehicleDto storeVehicle()
	{
		VehicleEntity vehicleEntity = new VehicleEntity();
		vehicleEntity.setVehicleType("Bus");
		vehicleEntity.setPlateNo("abc1234aaaaaaaaaaaaaaaa");

		return vehicleService.storeVehicle(vehicleEntity);
	}
	
	@GetMapping("/list")
	public List<Object> listVehicle()
	{
		return vehicleService.listObject();
	}

}
