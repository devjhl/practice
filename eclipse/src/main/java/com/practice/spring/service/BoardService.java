package com.practice.spring.service;

import java.util.ArrayList;

import com.practice.spring.vo.BoardTypeVO;
import com.practice.spring.vo.BoardVO;
import com.practice.spring.vo.MemberVO;

public interface BoardService {
	
	boolean insertBoard(BoardVO boardVO, MemberVO user);

	ArrayList<BoardVO> getBoardList();
	
	ArrayList<BoardTypeVO> getBoardType(int authority);

	BoardVO getBoard(int bo_num, MemberVO user);

}
