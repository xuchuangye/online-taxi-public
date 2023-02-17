package com.mashibing.apiboss.controller;

import com.mashibing.apiboss.service.CarService;
import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/17 - 14:58
 */
@RestController
public class CarController {

	@Autowired
	private CarService carService;

	@PostMapping("/car")
	public ResponseResult addCar(@RequestBody Car car) {
		return carService.addCar(car);
	}
}
