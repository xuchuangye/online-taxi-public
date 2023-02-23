package com.mashibing.servicessepush.controller;

import com.mashibing.internalcommon.utils.SseKeyUtils;
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
	 * @param userId
	 * @param identity
	 * @return
	 */
	@GetMapping("/connect")
	public SseEmitter connect(@RequestParam Long userId, @RequestParam String identity) {
		String sseMapKey = SseKeyUtils.generator(userId, identity);
		log.info("司机的id：" + sseMapKey);
		//0L表示连接永不超时
		SseEmitter sseEmitter = new SseEmitter(0L);
		sseEmitterMap.put(sseMapKey, sseEmitter);
		return sseEmitter;
	}

	/**
	 * 推送消息
	 *
	 * @param userId
	 * @param identity
	 * @param content
	 * @return
	 */
	@GetMapping("/push")
	public String push(@RequestParam Long userId, @RequestParam String identity, @RequestParam String content) {
		log.info("此用户：" + userId + "，身份标识：" + identity);
		String sseMapKey = SseKeyUtils.generator(userId, identity);
		try {
			//这个Map的value存的是SseEmitter对象，这个对象是SpringMVC提供的一种技术,可以实现服务端向客户端实时推送数据。
			//这个对象的send方法就是发送数据给到司机客户端。
			if (sseEmitterMap.containsKey(sseMapKey)) {
				sseEmitterMap.get(sseMapKey).send(content);
			} else {
				return "此用户：" + sseMapKey + "不存在，推送失败";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "推送给了：" + sseMapKey + "，消息内容是：" + content;
	}

	@GetMapping("/close")
	public String close(@RequestParam Long userId, @RequestParam String identity) {
		String sseMapKey = SseKeyUtils.generator(userId, identity);
		if (sseEmitterMap.containsKey(sseMapKey)) {
			sseEmitterMap.remove(sseMapKey);
		}
		return "close success";
	}
}
