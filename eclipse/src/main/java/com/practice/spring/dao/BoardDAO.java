package com.practice.spring.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.practice.spring.vo.BoardTypeVO;
import com.practice.spring.vo.BoardVO;

public interface BoardDAO {

	ArrayList<BoardTypeVO> selectAllBoardType(@Param("authority") int authority);

	boolean insertBoardType(@Param("bt") BoardTypeVO bt);

	boolean updateBoardType(@Param("bt") BoardTypeVO bt);
	
	boolean deleteBoardType(@Param("bt_num") int bt_num);
	
	void insertBoard(@Param("bo") BoardVO board);
	
	ArrayList<BoardVO> getBoardList();

}
