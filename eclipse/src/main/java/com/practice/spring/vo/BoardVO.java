package com.practice.spring.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class BoardVO {
	
	private int bo_num;
	private String bo_title;
	private String bo_content;
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
	
//	어노테이션 안먹힘
	public String getBo_register_date_str() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(bo_register_date);
	}

}
