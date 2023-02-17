package com.mashibing.apiboss.service;

import com.mashibing.apiboss.remote.ServiceDriverUserClient;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/17 - 17:06
 */
@Service
public class DriverCarBindingRelationshipService {

	@Autowired
	private ServiceDriverUserClient serviceDriverUserClient;

	public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship) {
		return serviceDriverUserClient.bind(driverCarBindingRelationship);
	}

	public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship) {
		return serviceDriverUserClient.unbind(driverCarBindingRelationship);
	}
}
