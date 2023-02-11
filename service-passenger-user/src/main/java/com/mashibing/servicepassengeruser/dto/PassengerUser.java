package com.mashibing.servicepassengeruser.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xcy
 * @date 2023/2/11 - 11:05
 */
@Data
public class PassengerUser {

	private Long id;
	private LocalDateTime gmtCreate;
	private LocalDateTime gmtModified;
	private String passengerPhone;
	private String passengerName;
	private Byte passengerGender;
	private Byte state;
}
