package com.practice.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.practice.spring.vo.MemberVO;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//ModelMap은 컨트롤러에서 처리한 결과를 뷰에 전달할 때 사용되는 객체 modelAndView.getModelMap()을 호출하여 ModelMap 객체를 가져옴
		ModelMap modelMap = modelAndView.getModelMap();
		//ModelMap 객체에서 "user"라는 이름으로 저장된 객체를 가져옴
		MemberVO user = (MemberVO) modelMap.get("user");
		
		if(user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
		}
	}
}
