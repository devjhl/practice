package com.practice.spring.service;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.practice.spring.vo.BoardTypeVO;
import com.practice.spring.vo.BoardVO;
import com.practice.spring.vo.FileVO;
import com.practice.spring.vo.LikesVO;
import com.practice.spring.vo.MemberVO;

public interface BoardService {
	
	boolean insertBoard(BoardVO boardVO, MemberVO user, MultipartFile[] files);

	ArrayList<BoardVO> getBoardList();
	
	ArrayList<BoardTypeVO> getBoardType(int authority);

	BoardVO getBoard(int bo_num, MemberVO user);

	ArrayList<FileVO> getFileList(int bo_num);

	int updateLikes(MemberVO user, int bo_num, int li_state);

	LikesVO getLikes(int bo_num, MemberVO user);
	
	void updateBoardByLikes(int bo_num);
	
	boolean deleteBoard(int bo_num,MemberVO user);

	BoardVO getBoardByWriteAuthority(int bo_num, MemberVO user);

	boolean updateBoard(BoardVO board, MultipartFile[] files, int[] fileNums, MemberVO user);

}
