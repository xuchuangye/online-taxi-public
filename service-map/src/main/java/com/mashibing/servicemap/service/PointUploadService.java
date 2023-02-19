package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.PointUploadRequest;
import com.mashibing.servicemap.remote.PointUploadClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/19 - 11:26
 */
@Service
public class PointUploadService {

	@Autowired
	private PointUploadClient pointUploadClient;

	public ResponseResult pointUpload(PointUploadRequest pointUploadRequest) {
		return pointUploadClient.pointUpload(pointUploadRequest);
	}
}
