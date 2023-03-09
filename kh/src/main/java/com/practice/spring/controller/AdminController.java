package com.practice.spring.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.practice.spring.service.AdminService;
import com.practice.spring.vo.BoardTypeVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/board/type")
public class AdminController {
	
	private final AdminService adminService;
	
	@GetMapping("/list")
	public String boardTypeList(Model model) {
		ArrayList<BoardTypeVO> list = adminService.getBoardType();
		model.addAttribute("list", list);
		return "/admin/boardTypeList";
	}
	
	@PostMapping("/insert")
	public String boardTypeInsert(Model model, BoardTypeVO bt) {
		boolean res = adminService.insertBoardType(bt);
		return "redirect:/admin/board/type/list";
	}
	
	@PostMapping("/update")
	public String boardTypeUpdate(Model model,BoardTypeVO bt) {
		boolean res = adminService.updateBoardType(bt);
		return "redirect:/admin/board/type/list";
	}
	
	@PostMapping("/delete")
	public String boardTypeDelete(Model model, BoardTypeVO bt) {
		boolean res = adminService.deleteBoardType(bt.getBt_num());
		return "redirect:/admin/board/type/list";
	}

}
