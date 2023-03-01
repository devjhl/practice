package com.practice.spring.vo;

import lombok.Data;

@Data
public class BoardTypeVO {
	
	private int bt_num;
	private String bt_type;
	private String bt_name;
	private int bt_r_authority;
	private int bt_w_authority;
	
	public String getBt_r_authority_str() {
		return getAuthority(bt_r_authority);
	}
	
	public String getBt_w_authority_str() {
		return getAuthority(bt_w_authority);
	}
	
	private String getAuthority(int authority) {
		switch (authority) {
		case 0 : return "비회원";
		case 1 : return "회원";
		case 9 : return "관리자";
		case 10 : return "최고 관리자";
		}
		return "";
	}

}
