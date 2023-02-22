package com.mashibing.ssedriverclientweb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xcy
 * @date 2023/2/22 - 20:48
 */
@RestController
@Slf4j
public class SseController {

	private static final Map<String, SseEmitter> sseEmitterMap = new HashMap<>();

	/**
	 * 建立连接
	 *
	 * @param driverId
	 * @return
	 */
	@GetMapping("/connect/{driverId}")
	public SseEmitter connect(@PathVariable("driverId") String driverId) {
		log.info("司机的id：" + driverId);
		//0L表示连接永不超时
		SseEmitter sseEmitter = new SseEmitter(0L);
		sseEmitterMap.put(driverId, sseEmitter);
		return sseEmitter;
	}

	/**
	 * 推送消息
	 *
	 * @param driverId 消息接收者
	 * @param content  消息内容
	 * @return
	 */
	@GetMapping("/push")
	public String push(@RequestParam String driverId, @RequestParam String content) {
		try {
			//这个Map的value存的是SseEmitter对象，这个对象是SpringMVC提供的一种技术,可以实现服务端向客户端实时推送数据。
			//这个对象的send方法就是发送数据给到司机客户端。
			if (sseEmitterMap.containsKey(driverId)) {
				sseEmitterMap.get(driverId).send(content);
			}else {
				return "此用户：" + driverId + "不存在，推送失败";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "推送给了：" + driverId + "，消息内容是：" + content;
	}

	@GetMapping("/close/{driverId}")
	public String close(@PathVariable String driverId) {
		if (sseEmitterMap.containsKey(driverId)) {
			sseEmitterMap.remove(driverId);
		}
		return "close success";
	}
}
