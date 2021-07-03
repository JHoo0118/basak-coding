package com.basakcoding.basak.service;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Integer memberId;
    private String email;
    private String password;
    private String username;
    private String emailValidate;
    private Date registeredAt;
    private String loginMethod;	
    private String avatar;
    private String emailSecret;
    
    
    public String getAvatarImagePath() {
    	if (avatar == null) return "/images/user.png";
    	return "/upload/member/" + this.memberId + "/" + this.avatar;
    }
}