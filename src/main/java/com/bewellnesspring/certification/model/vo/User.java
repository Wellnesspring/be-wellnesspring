package com.bewellnesspring.certification.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {

	private String userId;
	private int rule;
	private String userPw;
	private String name;
	private String serialNumF;
	private String serialNumL;
	private String phone;
	private int height;
	private int weight;
//	논란이 있을 수 있음
	private String joinAt;
	private String alarmAgree;
	private String profileImg;
	private int point;
	private String email;
}
