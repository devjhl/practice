package com.practice.spring.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.practice.spring.dao.BoardDAO;
import com.practice.spring.utils.UploadFileUtils;
import com.practice.spring.vo.BoardTypeVO;
import com.practice.spring.vo.BoardVO;
import com.practice.spring.vo.FileVO;
import com.practice.spring.vo.MemberVO;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{

	private final BoardDAO boardDao;
	
	String uploadPath = "C:\\uploadfiles";
	
	@Override
	public boolean insertBoard(BoardVO board, MemberVO user,MultipartFile[] files) {
		if(user == null)
			return false;
		if(!checkBoard(board))
			return false;
		board.setBo_me_id(user.getMe_id());
		//게시글 등록
		boardDao.insertBoard(board);
		
		uploadFiles(files, board.getBo_num());
		return true;
	}

	private void uploadFiles(MultipartFile[] files, int bo_num) {
		if(files == null || files.length == 0) 
			return;
		//반복문
		for (MultipartFile file : files) {
			if(file == null || file.getOriginalFilename().length() == 0)
				continue;
			String fileName = "";
			// 첨부파일 서버에 업로드
			try {
				fileName = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), // 파일 명
						file.getBytes()); // 실제 파일 데이터 
			}catch (Exception e) {
				e.printStackTrace();
			}
			//첨부파일 객체를 생성
			FileVO fileVO = new FileVO(file.getOriginalFilename(), fileName, bo_num);
			//다오에게 첨부파일 정보를 주면서 추가하라고 요청
			boardDao.insertFile(fileVO);
		}
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

	@Override
	public BoardVO getBoard(int bo_num, MemberVO user) {
		
		//조회수 증가
		boardDao.updateBoardViews(bo_num);
		
		BoardVO board = boardDao.selectBoard(bo_num);
		
		return board;
	}

	@Override
	public ArrayList<FileVO> getFileList(int bo_num) {
		return boardDao.selectFileList(bo_num);
	}

}
