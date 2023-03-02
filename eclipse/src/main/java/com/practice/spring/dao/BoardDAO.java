package com.practice.spring.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.practice.spring.vo.BoardTypeVO;
import com.practice.spring.vo.BoardVO;
import com.practice.spring.vo.FileVO;

public interface BoardDAO {

	ArrayList<BoardTypeVO> selectAllBoardType(@Param("authority") int authority);

	boolean insertBoardType(@Param("bt") BoardTypeVO bt);

	boolean updateBoardType(@Param("bt") BoardTypeVO bt);
	
	boolean deleteBoardType(@Param("bt_num") int bt_num);
	
	void insertBoard(@Param("bo") BoardVO board);
	
	ArrayList<BoardVO> getBoardList();

	BoardVO selectBoard(@Param("bo_num") int bo_num);
	
	void updateBoardViews(@Param("bo_num") int bo_num);
	
	void insertFile(@Param("file")FileVO fileVo);

	ArrayList<FileVO> selectFileList(int bo_num);

}
