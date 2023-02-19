package com.mashibing.apidriver.service;

import com.mashibing.apidriver.remote.ServiceDriverUserClient;
import com.mashibing.apidriver.remote.ServiceMapClient;
import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ApiDriverPointRequest;
import com.mashibing.internalcommon.request.PointUploadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/19 - 15:26
 */
@Service
public class PointService {

	@Autowired
	private ServiceDriverUserClient serviceDriverUserClient;

	@Autowired
	private ServiceMapClient serviceMapClient;

	public ResponseResult upload(ApiDriverPointRequest apiDriverPointRequest) {
		//获取carId
		Long carId = apiDriverPointRequest.getCarId();
		//根据carId获取tid和trid，调用service-driver-user的接口
		ResponseResult<Car> car = serviceDriverUserClient.getCar(carId);
		String tid = car.getData().getTid();
		Integer trid = car.getData().getTrid();
		//调用service-map去上传
		PointUploadRequest pointUploadRequest = new PointUploadRequest();
		pointUploadRequest.setTid(tid);
		pointUploadRequest.setTrid(trid + "");
		pointUploadRequest.setPoints(apiDriverPointRequest.getPoints());
		return serviceMapClient.pointUpload(pointUploadRequest);
	}
}
