package com.practice.spring.dao;

import org.apache.ibatis.annotations.Param;

import com.practice.spring.vo.MemberVO;

public interface MemberDAO {

	int insertMember(@Param("m") MemberVO member);

}
