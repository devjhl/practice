package com.practice.spring.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.practice.spring.dao.BoardDAO;
import com.practice.spring.vo.BoardTypeVO;
import com.practice.spring.vo.BoardVO;
import com.practice.spring.vo.MemberVO;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{

	private final BoardDAO boardDao;
	
	@Override
	public boolean insertBoard(BoardVO board, MemberVO user) {
		if(user == null)
			return false;
		if(!checkBoard(board))
			return false;
		board.setBo_me_id(user.getMe_id());
		boardDao.insertBoard(board);
		return true;
	}

	private boolean checkBoard(BoardVO board) {
		if(board == null || 
				board.getBo_title() == null || board.getBo_title().trim().length() == 0 ||
				board.getBo_content() == null || board.getBo_content().trim().length() == 0)
			return false;
		return true;
	}

	@Override
	public ArrayList<BoardVO> getBoardList() {
		return boardDao.getBoardList();
	}

	@Override
	public ArrayList<BoardTypeVO> getBoardType(int authority) {
		return boardDao.selectAllBoardType(authority);
	}

}
