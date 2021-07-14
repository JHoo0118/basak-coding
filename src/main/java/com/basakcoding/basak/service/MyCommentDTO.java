package com.basakcoding.basak.service;

import java.beans.Transient;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyCommentDTO {
	private Integer commentId;
	private Integer questionId;
	private Integer adminId;
	private Integer memberId;
	private String content;
	private Date createdAt;
	private String username;
	private String memavatar;
	private String name;
	private String avatar;
    private String adminPath;
    private String memberPath;
	
	public String getAdminAvatarImagePath() {
    	if (avatar == null) return "/images/user.png";
    	return "/upload/admin/" + this.adminId + "/" + this.avatar;
    }
	
	@Transient
    public String getMemberAvatarImagePath() {
    	if (avatar == null) return "/images/user.png";
    	return "/upload/member/" + this.memberId + "/" + this.avatar;
    }
}
