package com.practice.spring.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.practice.spring.vo.BoardTypeVO;
import com.practice.spring.vo.BoardVO;
import com.practice.spring.vo.FileVO;
import com.practice.spring.vo.LikesVO;

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

	ArrayList<FileVO> selectFileList(@Param("bo_num") int bo_num);
	
	LikesVO selectLikesById(@Param("li_me_id")String me_id, @Param("li_bo_num") int bo_num);

	void insertLikes(@Param("li")LikesVO likesVO);

	void updateLikes(@Param("li") LikesVO likesVO);

	void updateBoardByLikes(@Param("bo_num") int bo_num);
	
	int deleteBoard(@Param("bo_num") int bo_num);

	void deleteFile(@Param("file") FileVO file);

	int updateBoard(@Param("bo")BoardVO board);

	FileVO selectFile(@Param("fi_num")int fileNum);
}
