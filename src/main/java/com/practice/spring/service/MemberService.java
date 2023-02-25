package com.practice.spring.service;

import javax.servlet.http.HttpServletRequest;

import com.practice.spring.vo.MemberOKVO;
import com.practice.spring.vo.MemberVO;

public interface MemberService {

	boolean registerMember(MemberVO member,HttpServletRequest request);

	boolean emailAuthentication(MemberOKVO mok);
	
	MemberVO login(MemberVO member);
}
