package com.mashibing.internalcommon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
