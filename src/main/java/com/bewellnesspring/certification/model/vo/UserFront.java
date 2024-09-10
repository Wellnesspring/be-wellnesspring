package com.bewellnesspring.certification.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFront {
	
	private String userId;
	private int rule;
	private String name;
	private String alarmAgree;
	private String profileImg;
	
	public UserFront(User user) {
		super();
		this.userId = user.getUserId();
		this.rule = user.getRule();
		this.name = user.getName();
		this.alarmAgree = user.getAlarmAgree();
		this.profileImg = user.getProfileImg();
	}
}
