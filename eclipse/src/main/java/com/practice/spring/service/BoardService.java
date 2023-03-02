package com.practice.spring.service;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.practice.spring.vo.BoardTypeVO;
import com.practice.spring.vo.BoardVO;
import com.practice.spring.vo.FileVO;
import com.practice.spring.vo.MemberVO;

public interface BoardService {
	
	boolean insertBoard(BoardVO boardVO, MemberVO user, MultipartFile[] files);

	ArrayList<BoardVO> getBoardList();
	
	ArrayList<BoardTypeVO> getBoardType(int authority);

	BoardVO getBoard(int bo_num, MemberVO user);

	ArrayList<FileVO> getFileList(int bo_num);

}
