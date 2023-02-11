package com.mashibing.apipassenger.remote;

import com.mashibing.internalcommon.dto.PassengerUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationcodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xcy
 * @date 2023/2/11 - 14:54
 */
@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {

	@RequestMapping(method = RequestMethod.POST, value = "/user")
	ResponseResult loginOrRegister(@RequestBody VerificationcodeDTO verificationcodeDTO);
}
