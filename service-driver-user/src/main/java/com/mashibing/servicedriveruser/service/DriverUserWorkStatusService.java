package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.dto.DriverUserWorkStatus;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xcy
 * @date 2023/2/18 - 14:45
 */
@Service
public class DriverUserWorkStatusService {

	@Autowired
	private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

	/**
	 * 修改司机状态
	 * @param driverId
	 * @param workStatus
	 * @return
	 */
	public ResponseResult changeWorkStatus(Long driverId, Integer workStatus) {
		Map<String, Object> map = new HashMap<>();
		map.put("driver_id", driverId);

		//查询司机状态
		List<DriverUserWorkStatus> driverUserWorkStatuses = driverUserWorkStatusMapper.selectByMap(map);
		DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatuses.get(0);
		driverUserWorkStatus.setWorkStatus(workStatus);
		LocalDateTime now = LocalDateTime.now();
		driverUserWorkStatus.setGmtModified(now);

		//修改司机状态
		driverUserWorkStatusMapper.updateById(driverUserWorkStatus);
		return ResponseResult.success("");
	}
}
