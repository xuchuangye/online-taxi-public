package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.MapConfigConstant;
import com.mashibing.internalcommon.dto.PointDTO;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.PointUploadRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;

/**
 * @author xcy
 * @date 2023/2/19 - 11:27
 */
@Service
public class PointUploadClient {

	@Value("${amap.key}")
	private String aMapKey;

	@Value("${amap.sid}")
	private String sid;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 轨迹点上传
	 * https://tsapi.amap.com/v1/track/point/upload
	 * ?
	 * key=e0d3379f01867fcdb15286f434e7eaa3
	 * &
	 * sid=880995
	 * &
	 * tid=637229942
	 * &
	 * trid=60
	 * &
	 * points=%5B+++++++
	 * {++++++++++++++++%22
	 * location%22%3A+%22116.38%2C39.97%22%2C++++++++%22
	 * locatetime%22%3A+1676712249339++++++++
	 * }%5D
	 *
	 * @param pointUploadRequest
	 * @return
	 */
	public ResponseResult pointUpload(PointUploadRequest pointUploadRequest) {
		String tid = pointUploadRequest.getTid();
		String trid = pointUploadRequest.getTrid();
		PointDTO[] points = pointUploadRequest.getPoints();

		StringBuilder url = new StringBuilder();
		url.append(MapConfigConstant.POINT_UPLOAD_URL);
		url.append("?");
		url.append("key=").append(aMapKey);
		url.append("&");
		url.append("sid=").append(sid);
		url.append("&");
		url.append("tid=").append(tid);
		url.append("&");
		url.append("trid=").append(trid);
		url.append("&");
		url.append("points=");
		//左中括号的URL编码：%5B
		url.append("%5B");
		for (PointDTO point : points) {
			//左大括号的URL编码：%7B
			url.append("%7B");
			//双引号的URL编码：%22
			//冒号的URL编码：%3A
			url.append("%22location%22%3A");
			url.append("%22").append(point.getLocation()).append("%22");
			//逗号的URL编码：%2C
			url.append("%2C");
			url.append("%22locatetime%22%3A").append(point.getLocatetime());
			//右大括号的URL编码：%7D
			url.append("%7D");
		}
		//右中括号的URL编码：%5D
		url.append("%5D");
		System.out.println("轨迹点上传的请求：" + url.toString());
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(URI.create(url.toString()), null, String.class);
		JSONObject body = JSONObject.fromObject(responseEntity.getBody());
		System.out.println("轨迹点上传的响应：" + body);
		JSONObject data = body.getJSONObject("data");

		return ResponseResult.success("");
	}
}
