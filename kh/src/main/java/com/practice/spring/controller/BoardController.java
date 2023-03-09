package com.practice.spring.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.practice.spring.service.BoardService;
import com.practice.spring.utils.MessageUtils;
import com.practice.spring.vo.BoardTypeVO;
import com.practice.spring.vo.BoardVO;
import com.practice.spring.vo.FileVO;
import com.practice.spring.vo.LikesVO;
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
	public String boardInsertPost(Model model,BoardVO board,HttpSession session,MultipartFile []files) {
		//세션에서 회원 정보 가져옴
		MemberVO user = (MemberVO) session.getAttribute("user");
		boardService.insertBoard(board, user,files);
		return "redirect:/";
	}
	
	@GetMapping("/detail/{bo_num}")
	public String boardDetail(Model model,
			@PathVariable("bo_num") int bo_num,HttpSession session,
			HttpServletResponse response) {
		MemberVO user = (MemberVO) session.getAttribute("user");
		BoardVO board = boardService.getBoard(bo_num,user);
		ArrayList<FileVO> files = boardService.getFileList(bo_num);
		LikesVO likesVo = boardService.getLikes(bo_num,user);
		
		
		model.addAttribute("board", board);
		model.addAttribute("files", files);
		model.addAttribute("likes", likesVo);
		
		if(board == null) 
			MessageUtils.alertAndMovePage(response, "삭제되거나 조회권한이 없는 게시글 입니다.", "/test", "/board/list");
		return "/board/detail";
	}
	
	@ResponseBody
	@GetMapping(value = "/like/{li_state}/{bo_num}")
	public Map<String, Object> boardLike(HttpSession session, 
		@PathVariable("li_state")int li_state,
		@PathVariable("bo_num")int bo_num) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//res - 1: 추천, -1 : 비추천 : 0이면 취소
		MemberVO user = (MemberVO)session.getAttribute("user");
		int res = boardService.updateLikes(user, bo_num, li_state);
		boardService.updateBoardByLikes(bo_num);
		map.put("res", res);
		return map;
	}
	
	@GetMapping("/delete/{bo_num}")
	public String boardDelete(Model model,HttpSession session, @PathVariable("bo_num") int bo_num, HttpServletResponse response) {
			MemberVO user = (MemberVO) session.getAttribute("user");
			boolean res = boardService.deleteBoard(bo_num, user);
			if(res) {
				MessageUtils.alertAndMovePage(response, 
						"게시글을 삭제했습니다.", "/test", "/board/list");
			}else {
				MessageUtils.alertAndMovePage(response, 
						"작성자가 아니거나 이미 삭제된 게시글입니다.", "/test", 
						"/board/detail/"+bo_num);
			}
			return "/board/list";
	}
	
	@GetMapping("/update/{bo_num}")
	public String boardUpdate(Model model
			,HttpSession session,
			@PathVariable("bo_num")int bo_num,
			HttpServletResponse response) {
		//세션에 있는 회원 정보를 가져옴
		MemberVO user = (MemberVO) session.getAttribute("user");
		BoardVO board = boardService.getBoardByWriteAuthority(bo_num, user);
		//첨부파일 가져옴
		ArrayList<FileVO> files = boardService.getFileList(bo_num);
		
		if(board == null) {
			MessageUtils.alertAndMovePage(response, "작성자가 아니거나 존재하지 않은 게시글 입니다.", "/test", "/board/list");
		}else {
			model.addAttribute("board",board);
			model.addAttribute("files",files);
			//?
			ArrayList<BoardTypeVO> btList = boardService.getBoardType(user.getMe_authority());
			model.addAttribute("btList",btList);
			if(btList.size() == 0) {
				MessageUtils.alertAndMovePage(response, 
						"권한이 없어서작성할 수 있는 게시판이 없습니다.", "/test", 
						"/board/list");
			}
		}
		return "/board/update";
	}
	
	@PostMapping("/update/{bo_num}")
	public String boardUpdatePost(Model model
			,HttpSession session,
			@PathVariable("bo_num")int bo_num,
			HttpServletResponse response, // 수정할 게시글 정보
			BoardVO board,MultipartFile[] files, // 추가된 첨부파일
			int [] fileNums) { // 삭제될 첨부파일
		//세션에 있는 회원 정보 가져옴. 작성자와 아이디가 같은지 확인하려고
				MemberVO user = (MemberVO)session.getAttribute("user");
				if(boardService.updateBoard(board,files,fileNums, user)) {
					MessageUtils.alertAndMovePage(response, 
							"게시글을 수정했습니다.", "/test", 
							"/board/detail/"+bo_num);
				}else {
					MessageUtils.alertAndMovePage(response, 
							"게시글을 수정하지 못했습니다.", "/test", 
							"/board/list");
				}
				return "/board/detail/"+bo_num;
	}
	
}
	
