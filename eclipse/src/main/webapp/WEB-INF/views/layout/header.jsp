<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
 
<nav class="navbar navbar-expand-md bg-dark navbar-dark">
	<div class="container">
	  	<a class="navbar-brand" href="/test" style="padding: 0">Navbar</a>
	  		<img height="40" alt="로고" src="<c:url value="/resources/img/bird.jpg"></c:url>">
	  	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
	    	<span class="navbar-toggler-icon"></span>
	  	</button>
	  	<div class="collapse navbar-collapse" id="collapsibleNavbar">
	    	<ul class="navbar-nav">
		      	<c:if test="${user == null}">
		      	<li class="nav-item">
		      	
		        	<a class="nav-link" href="<c:url value="/member/register"></c:url>">회원가입</a>
		      	</li>
		      	<li class="nav-item">
		        	<a class="nav-link" href="<c:url value="/member/login"></c:url>">로그인</a>
		      	</li>
		      	</c:if>
		      	<c:if test="${user != null}">
		      	<li class="nav-item">
		        	<a class="nav-link" href="<c:url value="/member/logout"></c:url>">로그아웃</a>
		      	</li>
		      	</c:if>
	    	</ul>
		</div> 
	</div> 
</nav>