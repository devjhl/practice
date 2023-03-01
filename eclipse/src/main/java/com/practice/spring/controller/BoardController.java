package com.practice.spring.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.practice.spring.service.BoardService;
import com.practice.spring.vo.BoardTypeVO;
import com.practice.spring.vo.BoardVO;
import com.practice.spring.vo.MemberVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	
	@GetMapping("/list")
	public String boardList(Model model) {
		ArrayList<BoardVO> list = boardService.getBoardList();
		model.addAttribute("list", list);
		return "/board/list";
	}
	
	@GetMapping("/insert")
	public String boardInsert(Model model,HttpServletRequest request) {
		//세션에서 회원 정보를 가져옴 ( 게시판을 가져오기 위해서)
		MemberVO user = (MemberVO) request.getSession().getAttribute("user");
		
		ArrayList<BoardTypeVO> btList = boardService.getBoardType(user.getMe_authority());
		model.addAttribute("btList", btList);
		
		if(btList.size() == 0) 
			return "redirect:/board/list";
		
		return "/board/insert";
	}
	
	@PostMapping("/insert")
	public String boardInsertPost(Model model,BoardVO board,HttpSession session) {
		//세션에서 회원 정보 가져옴
		MemberVO user = (MemberVO) session.getAttribute("user");
		boardService.insertBoard(board, user);
		return "redirect:/";
	}
}
