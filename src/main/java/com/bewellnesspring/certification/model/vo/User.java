package com.bewellnesspring.certification.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {

	private String userId;
	private int rule;
	private String userPw;
	@EncodeField
	private String name;
	@EncodeField
	private String serialNumF;
	@EncodeField
	private String serialNumL;
	@EncodeField
	private String phone;
	@EncodeField
	private String height;
	@EncodeField
	private String weight;
	private Timestamp joinAt;
	private String alarmAgree;
	@EncodeField
	private String profileImg;
	private int point;
	private String locker;
}
