package com.practice.spring.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.practice.spring.dao.BoardDAO;
import com.practice.spring.utils.UploadFileUtils;
import com.practice.spring.vo.BoardTypeVO;
import com.practice.spring.vo.BoardVO;
import com.practice.spring.vo.FileVO;
import com.practice.spring.vo.LikesVO;
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

	@Override
	public int updateLikes(MemberVO user, int bo_num, int li_state) {
		// 기존에 추천/비추천 정보를 가져옴
		LikesVO likesVO = boardDao.selectLikesById(user.getMe_id(),bo_num);
		// 없으면 추가
		if(likesVO == null) {
			//LikesVO 객체를 생성
			likesVO = new LikesVO(li_state, user.getMe_id(), bo_num);
			//생성된 객체를 다오에게 전달해서 insert 하라고 시킴
			boardDao.insertLikes(likesVO);
			//li_state를 리턴
			return li_state;
		}
			// 있으면 수정
			if(li_state != likesVO.getLi_state()) {
				//현재 상태와 기존 상태가 다르면 => 상태를 바꿔야함
				likesVO.setLi_state(li_state);
				//업데이트
				boardDao.updateLikes(likesVO);
				return li_state;
			}
		
			//현재 상태와 기존상태가 같으면 => 취소
			likesVO.setLi_state(0);
			boardDao.updateLikes(likesVO);
			return 0;
	}

	@Override
	public LikesVO getLikes(int bo_num, MemberVO user) {
		if(user == null)
			return null;
		return boardDao.selectLikesById(user.getMe_id(), bo_num);
	}

	@Override
	public void updateBoardByLikes(int bo_num) {
		boardDao.updateBoardByLikes(bo_num);
	}
	
	private void deleteFileList(ArrayList<FileVO> fileList) {
		if(fileList == null || fileList.size() == 0) 
			return;
			 for (FileVO file : fileList) {
				if(file == null)
					continue;
				 UploadFileUtils.removeFile(uploadPath, file.getFi_name());
				boardDao.deleteFile(file); // 수정에서 첨부파일 교체할때 디비에서 삭제
			}
		}

	@Override
	public boolean deleteBoard(int bo_num, MemberVO user) {
		if(user == null)
			return false;
		BoardVO boardVO = boardDao.selectBoard(bo_num);
		if(boardVO == null)
			return false;
		if(!boardVO.getBo_me_id().equals(user.getMe_id()))
			return false;
		//첨부파일 목록들을 가져옴 
		ArrayList<FileVO> fileList = boardDao.selectFileList(bo_num);
		deleteFileList(fileList);
		return boardDao.deleteBoard(bo_num) != 0;
		}

}
