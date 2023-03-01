package com.practice.spring.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.practice.spring.dao.BoardDAO;
import com.practice.spring.vo.BoardTypeVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	
	private final BoardDAO boardDao;

	@Override
	public ArrayList<BoardTypeVO> getBoardType() {
		return boardDao.selectAllBoardType(9);
	}

	@Override
	public boolean insertBoardType(BoardTypeVO bt) {
		if(bt == null || bt.getBt_name().trim().length() == 0) 
			return false;
		try {
			return boardDao.insertBoardType(bt);
		}catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean updateBoardType(BoardTypeVO bt) {
		if(bt == null || bt.getBt_name().trim().length() == 0) 
			return false;
		try {
			return boardDao.updateBoardType(bt);			
		}catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean deleteBoardType(int bt_num) {
		if(bt_num <= 0)
				return false;
		return boardDao.deleteBoardType(bt_num);
	}
}
