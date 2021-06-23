package com.basakcoding.basak.service;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
	private Integer adminId;
	private String email;
	private String name;
	private String password;
	private String avatar;
	private Date createdAt;
	private String bio;
	
	public String getAvatarImagePath() {
    	if (avatar == null) return "/images/user.png";
    	return "/upload/admin/" + this.adminId + "/" + this.avatar;
    }
}
