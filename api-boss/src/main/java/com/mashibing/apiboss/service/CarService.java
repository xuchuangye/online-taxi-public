package com.mashibing.apiboss.service;

import com.mashibing.apiboss.remote.ServiceDriverUserClient;
import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/17 - 14:59
 */
@Service
public class CarService {

	@Autowired
	private ServiceDriverUserClient serviceDriverUserClient;

	public ResponseResult addCar(Car car) {
		return serviceDriverUserClient.addCar(car);
	}
}
