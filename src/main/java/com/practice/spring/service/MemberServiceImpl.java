package com.practice.spring.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.spring.dao.MemberDAO;
import com.practice.spring.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	
	private final BCryptPasswordEncoder passwordEncoder;
	private final MemberDAO memberDAO;

	@Override
	public boolean registerMember(MemberVO member) {
		if(member == null)
			return false;
		String newPw = passwordEncoder.encode(member.getMe_pw());
		member.setMe_pw(newPw);
		return memberDAO.insertMember(member) != 0;
	}

}
