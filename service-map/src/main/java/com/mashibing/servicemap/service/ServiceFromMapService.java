package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.remote.ServiceFromMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/18 - 19:56
 */
@Service
public class ServiceFromMapService {

	@Autowired
	private ServiceFromMapClient serviceFromMapClient;

	public ResponseResult addService(String name) {
		return serviceFromMapClient.addService(name);
	}
}
