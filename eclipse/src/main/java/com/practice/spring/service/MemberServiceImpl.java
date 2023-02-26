package com.practice.spring.service;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.spring.dao.MemberDAO;
import com.practice.spring.vo.MemberOKVO;
import com.practice.spring.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	
	private final BCryptPasswordEncoder passwordEncoder;
	private final MemberDAO memberDAO;
	private final JavaMailSender mailSender;

	@Override
	public boolean registerMember(MemberVO member,HttpServletRequest request) {
		if(member == null)
			return false;
		String newPw = passwordEncoder.encode(member.getMe_pw());
		member.setMe_pw(newPw);
		
		int res =  memberDAO.insertMember(member);
		if(res == 0)
			return false;
		String randomStr = createRandom(6);
		MemberOKVO mok = new MemberOKVO(member.getMe_id(), randomStr);
		memberDAO.insertMemberOK(mok);
		//이메일 전송
		String title = "이메일 인증을 해주세요.";
		String href = getHref(mok,request);
		String content = "<a href='"+href+"'>이메일 인증 링크</a>을 클릭해서 회원 가입을 완료 해주세요.";
		sendEmail(title,content,member.getMe_email());
		return true;
	}
	
	private String getHref(MemberOKVO mok,HttpServletRequest request) {
		String href = "http://localhost:8080"+request.getContextPath()+"/member/email/authentication?mo_me_id="+mok.getMo_me_id()+"&mo_num="+mok.getMo_num();
		return href;
	}
	
	private String createRandom(int size) {
		String pattern = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWYZ";
		int min = 0 , max = pattern.length()-1;
		String randomStr = "";
		while (randomStr.length() < size) {
			int r = (int) (Math.random()*(max-min+1)+min);
			randomStr+= pattern.charAt(r);
			
		}
		return randomStr;
	}
	
	private void sendEmail(String title, String content, String email) {
		String setfrom = "jeonghyeon.dev@gmail.com";

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

			messageHelper.setFrom(setfrom); // 보내는사람 생략하거나 하면 정상작동을 안함
			messageHelper.setTo(email); // 받는사람 이메일
			messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
			//setText에 true을 추가하면 , content에 있는 html코드를 html코드로 적용
			messageHelper.setText(content,true); // 메일 내용

			mailSender.send(message);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public boolean emailAuthentication(MemberOKVO mok) {
		if(mok == null || mok.getMo_me_id() == null || mok.getMo_num() == null)
			return false;
		//아이디,인증번호를 이용하여 삭제 시켜서 삭제된 갯수를 받아옴
		int delCount = memberDAO.deleteMemberOK(mok);
		if(delCount ==0 )
			return false;
		//인증 성공
		int updateCount = memberDAO.updateMemberAuthority(mok.getMo_me_id(),1);
		return updateCount !=0;
	}

	@Override
	public MemberVO login(MemberVO member) {
		MemberVO dbMember = memberDAO.selectMemberById(member.getMe_id());
		if(dbMember == null)
			return null;
		if(passwordEncoder.matches(member.getMe_pw(), dbMember.getMe_pw()))
			return dbMember;
		return null;
	}

	

}
