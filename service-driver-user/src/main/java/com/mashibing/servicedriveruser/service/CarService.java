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

		//车辆和终端进行绑定
		//将车牌号作为绑定终端的name
		String name = car.getVehicleNo();
		ResponseResult<TerminalResponse> terminalResponse = serviceMapClient.addTerminal(name);
		String tid = terminalResponse.getData().getTid();
		car.setTid(tid);

		//车辆和轨迹进行绑定
		ResponseResult<TraceResponse> traceResponse = serviceMapClient.addTrace();
		Integer trid = traceResponse.getData().getTrid();
		car.setTrid(trid);

		carMapper.insert(car);
		return ResponseResult.success("");
	}
}
