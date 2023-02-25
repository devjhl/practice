package com.practice.spring.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.practice.spring.service.MemberService;
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
	public String registerPost(@ModelAttribute("member") MemberVO member) {
		System.out.println(member);
		boolean res = memberService.registerMember(member);
		if(res) {
			return "redirect:/";
		}else {
			return "/member/register";
		}
	}
}
