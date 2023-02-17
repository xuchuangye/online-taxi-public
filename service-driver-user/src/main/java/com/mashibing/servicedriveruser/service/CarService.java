package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author xcy
 * @date 2023/2/16 - 21:10
 */
@Service
public class CarService {

	@Autowired
	private CarMapper carMapper;;

	public ResponseResult addCar(Car car) {
		LocalDateTime now = LocalDateTime.now();
		car.setGmtCreate(now);
		car.setGmtModified(now);
		carMapper.insert(car);

		return ResponseResult.success("");
	}
}
