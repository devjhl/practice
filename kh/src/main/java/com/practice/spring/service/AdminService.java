package com.practice.spring.service;

import java.util.ArrayList;

import com.practice.spring.vo.BoardTypeVO;

public interface AdminService {

	ArrayList<BoardTypeVO> getBoardType();

	boolean insertBoardType(BoardTypeVO bt);

	boolean updateBoardType(BoardTypeVO bt);

	boolean deleteBoardType(int bt_num);

}
