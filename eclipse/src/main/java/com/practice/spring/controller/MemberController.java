package com.practice.spring.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.practice.spring.service.MemberService;
import com.practice.spring.vo.MemberOKVO;
import com.practice.spring.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Validated
public class MemberController {
	
	private final MemberService memberService;
	
	@GetMapping("/register")
	public String register() {
		return "/member/register";
	}
	
	@PostMapping("/register")
	public String registerPost(@ModelAttribute("member") MemberVO member,HttpServletRequest request) {
		boolean res = memberService.registerMember(member,request);
		if(res) {
			return "redirect:/";
		}else {
			return "/member/register";
		}
	}
	
	@GetMapping("/email/authentication")
	public String emailAuthentication(MemberOKVO mok) {
		boolean res = memberService.emailAuthentication(mok);
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "/member/login";
	}
	
	@PostMapping("/login")
	public String loginPost(Model model, MemberVO member) {
		if(member == null)
			return null;
		MemberVO user = memberService.login(member);
		if(user == null)
			return "redirect:/member/login";
		model.addAttribute("user",user); // 회원정보를 전달해야지 인터셉터가 가로챔
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		MemberVO memberVO =  (MemberVO) session.getAttribute("user");
		if(memberVO != null) {
			session.removeAttribute("user");
		}
		return "redirect:/";
	}
}
