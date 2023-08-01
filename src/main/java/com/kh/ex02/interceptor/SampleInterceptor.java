package com.kh.ex02.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

// 요청을 중간에 가로채서 뭔가를 하고자 할때
public class SampleInterceptor extends HandlerInterceptorAdapter {

	// 실제 요청({"/doA", "/doB"}) 처리 전
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandle 실행됨");
		return super.preHandle(request, response, handler);
	}
	
	// 실제 요청({"/doA", "/doB"}) 처리 후
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("postHandle 실행됨");
		super.postHandle(request, response, handler, modelAndView);
	}
}
