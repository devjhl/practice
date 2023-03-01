<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="<c:url value='/resources/css/summernote-bs4.min.css'></c:url>" rel="stylesheet">
<script src="<c:url value='/resources/js/summernote-bs4.min.js'></c:url>"></script>
<div class="container">
	<h1>게시글 확인</h1>
	<div class="form-group">
		<label>게시판:</label>
		<div class="form-control" >${board.bt_name}</div>
	</div>
	<div class="form-group">
		<label >제목:</label>
		<div class="form-control">${board.bo_title }</div>
	</div>
	<div class="form-group">
		<label>작성자:</label>
		<div class="form-control">${board.bo_me_id }</div>
	</div>
	<div class="form-group">
		<label for="title">작성일:</label>
		<div class="form-control">${board.bo_register_date}</div>
	</div>
	<div class="form-group">
		<label for="title">조회수:</label>
		<div class="form-control">${board.bo_views}</div>
	</div>
	<c:if test="${board.bt_type =='일반' }">
		<div id="common">
			<div class="form-group">
				<label for="content">내용:</label>
				<div class="form-control" style="min-height: 400px">${board.bo_content}</div>
			</div>
			
		</div>
	</c:if>
	
	<c:if test="${user != null && user.me_id == board.bo_me_id}">
		<div>
			<a href="<c:url value='/board/update/${board.bo_num}'></c:url>">
				<button class="btn btn-outline-primary btn-update">수정</button>
			</a>
			<a href="<c:url value='/board/delete/${board.bo_num}'></c:url>">
				<button class="btn btn-outline-primary btn-delete">삭제</button>
			</a>
		</div>
	</c:if>
</div>
