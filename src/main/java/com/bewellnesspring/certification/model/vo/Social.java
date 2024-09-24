package com.bewellnesspring.certification.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Social {
	
	public Social(String userId, long idNum, String platform) {
		super();
		this.userId = userId;
		this.idNum = idNum;
		this.platform = platform;
	}
	
	private int id;
	private String userId;
	private long idNum;
	private String platform;
}
