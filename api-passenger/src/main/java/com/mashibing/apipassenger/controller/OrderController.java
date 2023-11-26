package com.mashibing.apipassenger.controller;

/**
 * @author xcy
 * @date 2023/2/20 - 7:22
 */

import com.mashibing.apipassenger.service.OrderService;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.IdentityConstant;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.internalcommon.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/order")
//如果当前类中需要使用Validation验证框架，则添加@Validated
@Validated
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/add")
	public ResponseResult add(@RequestBody OrderRequest orderRequest) {
		return orderService.add(orderRequest);
	}

	@PostMapping("/cancel")
	public ResponseResult cancel(@RequestParam Long orderId) {
		return orderService.cancel(orderId);
	}

	@GetMapping("/detail")
	public ResponseResult<OrderInfo> detail(@NotNull(message = "订单id不能为空")
	                                            @Positive(message = "订单id不正确")
			                                            Long orderId){
		return orderService.detail(orderId);
	}

	@GetMapping("/current")
	public ResponseResult<OrderInfo> currentOrder(@NotNull(message = "订单id不能为空")
	                                                  @Positive(message = "订单id不正确")
			                                                  HttpServletRequest httpServletRequest){
		String authorization = httpServletRequest.getHeader("Authorization");
		TokenResult tokenResult = JWTUtils.parseToken(authorization);
		String identity = tokenResult.getIdentity();
		if (!identity.equals(IdentityConstant.PASSENGER_IDENTITY)){
			return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getMessage());
		}
		String phone = tokenResult.getPhone();

		return orderService.currentOrder(phone,IdentityConstant.PASSENGER_IDENTITY);
	}
}
