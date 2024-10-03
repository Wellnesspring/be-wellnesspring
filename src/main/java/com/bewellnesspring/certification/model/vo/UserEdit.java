package com.bewellnesspring.certification.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEdit {
	
	private String userId;
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
	@EncodeField
	private String profileImg;
	
	public UserEdit(User user) {
		super();
		this.userId = user.getUserId();
		this.name = user.getName();
		this.serialNumF = user.getSerialNumF();
		this.serialNumL = user.getSerialNumL();
		this.phone = user.getPhone();
		this.height = user.getHeight();
		this.weight = user.getWeight();
		this.profileImg = user.getProfileImg();
	}
}
