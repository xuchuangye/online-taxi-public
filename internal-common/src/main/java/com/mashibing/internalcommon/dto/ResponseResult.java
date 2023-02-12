package com.mashibing.internalcommon.dto;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author xcy
 * @date 2023/2/8 - 16:02
 */
@Getter
@Setter
//链式代码
@Accessors(chain = true)
public class ResponseResult<T> {

	private int code;
	private String message;
	private T data;

	/**
	 * 请求响应成功
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> ResponseResult success(T data) {
		return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode())
				.setMessage(CommonStatusEnum.SUCCESS.getMessage())
				.setData(data);
	}

	/**
	 * 请求响应失败
	 * @param data
	 * @return
	 */
	public static <T> ResponseResult fail(T data) {
		return new ResponseResult().setCode(CommonStatusEnum.FAIL.getCode())
				.setMessage(CommonStatusEnum.FAIL.getMessage())
				.setData(data);
	}

	/**
	 * 自定义请求响应失败，响应码和提示信息
	 * @param code
	 * @param message
	 * @return
	 */
	public static ResponseResult fail(int code, String message) {
		return new ResponseResult().setCode(code).setMessage(message);
	}

	/**
	 * 自定义请求响应失败，响应码和提示信息以及返回的数据
	 * @param code
	 * @param message
	 * @param data
	 * @return
	 */
	public static ResponseResult fail(int code, String message, String data) {
		return new ResponseResult().setCode(code).setMessage(message).setData(data);
	}
}
