package com.practice.spring.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class BoardVO {
	
	private int bo_num;
	private String bo_title;
	private String bo_content;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date bo_register_date;
	private Date bo_update_date;
	private int bo_views;
	private int bo_up;
	private int bo_down;
	private int bo_ori_num;
	private String bo_me_id;
	private int bo_bt_num;
	private String bt_type;
	private String bt_name;

}
