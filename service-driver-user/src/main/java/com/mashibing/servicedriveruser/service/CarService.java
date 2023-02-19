package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TraceResponse;
import com.mashibing.servicedriveruser.mapper.CarMapper;
import com.mashibing.servicedriveruser.remote.ServiceMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xcy
 * @date 2023/2/16 - 21:10
 */
@Service
public class CarService {

	@Autowired
	private ServiceMapClient serviceMapClient;

	@Autowired
	private CarMapper carMapper;;

	public ResponseResult addCar(Car car) {
		LocalDateTime now = LocalDateTime.now();
		car.setGmtCreate(now);
		car.setGmtModified(now);
		//插入car
		carMapper.insert(car);
		//车辆和终端进行绑定
		//将车牌号作为绑定终端的name
		String name = car.getVehicleNo();
		//获取车辆终端的id
		ResponseResult<TerminalResponse> terminalResponse = serviceMapClient.addTerminal(name, car.getId() + "");
		String tid = terminalResponse.getData().getTid();
		car.setTid(tid);

		//获取车辆轨迹的id
		ResponseResult<TraceResponse> traceResponse = serviceMapClient.addTrace(tid);
		Integer trid = traceResponse.getData().getTrid();
		String trname = traceResponse.getData().getTrname();
		car.setTrid(trid);
		car.setTrname(trname);
		//更新car
		carMapper.updateById(car);
		return ResponseResult.success("");
	}

	public ResponseResult<Car> getCarById(Long carId) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", carId);
		List<Car> cars = carMapper.selectByMap(map);
		return ResponseResult.success(cars.get(0));
	}
}
