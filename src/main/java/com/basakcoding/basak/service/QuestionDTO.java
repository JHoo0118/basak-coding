package com.basakcoding.basak.service;

import java.beans.Transient;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {

   private Integer questionId;
   private Integer memberId;
   private Integer courseId;
   private String title;
   private String content;
   private String avatar;
   private String memberPath;
   private Date createdAt;
   private int likeCount;
   private int commentCount;
   
   private String name;
   private String userName;
   private String courseTitle;
   private String questionTitle;
   
   private long updateDays;
   private long updateHours;
   
   @Transient
    public String getMemberAvatarImagePath() {
       if (avatar == null && memberId !=null) return "/images/image4.jpg";
       return "/upload/member/" + this.memberId + "/" + this.avatar;
    }



   
}