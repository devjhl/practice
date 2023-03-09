package com.practice.spring.dao;

import org.apache.ibatis.annotations.Param;

import com.practice.spring.vo.MemberOKVO;
import com.practice.spring.vo.MemberVO;

public interface MemberDAO {

	int insertMember(@Param("m") MemberVO member);

	void insertMemberOK(@Param("mok") MemberOKVO mok);

	int deleteMemberOK(@Param("mok") MemberOKVO mok);

	int updateMemberAuthority(@Param("me_id")String mo_me_id, @Param("me_authority")int me_authority);
	
	MemberVO selectMemberById(@Param("me_id")String me_id);

}
