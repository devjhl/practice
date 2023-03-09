package com.practice.spring.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MemberVO {
	
		private String me_id;
	    
		private String me_pw;
	    
		private String me_email;
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date me_birthday;
	    
		private int me_authority;
		
		private Date me_join_time;
		
	
}
