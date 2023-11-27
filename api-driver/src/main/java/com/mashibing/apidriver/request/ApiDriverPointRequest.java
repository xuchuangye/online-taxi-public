package com.mashibing.apidriver.request;

import com.mashibing.internalcommon.dto.PointDTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author xcy
 * @date 2023/2/19 - 15:25
 */
@Data
public class ApiDriverPointRequest {

	/**
	 * 车辆id
	 */
	@NotNull(message = "车辆id不能为空")
	@Positive(message = "车辆id必须为正数")
	private Long carId;

	/**
	 * 点信息：JSON数组
	 */
	@NotBlank(message = "点信息不能为空")
	private PointDTO[] points;
}
