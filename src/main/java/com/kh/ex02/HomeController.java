package com.kh.ex02;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.ex02.vo.BoardVo;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		
//		String formattedDate = dateFormat.format(date);
//		
//		model.addAttribute("serverTime", formattedDate );
		
//		return "home";
		return "redirect:/board/list";
	}
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	@ResponseBody  // jsp포워딩/redirect 가 아니고 데이터 자체를 응답한다.
	public String hello() {
		return "hello";
	}
	
	@RequestMapping(value = "/hello2", method = RequestMethod.POST)
	@ResponseBody  // jsp포워딩/redirect 가 아니고 데이터 자체를 응답한다.
	public String hello2(BoardVo boardVo) {
		System.out.println("boardVo:" + boardVo);
		return "hello";
	}
	
	@RequestMapping(value = "/doA", method = RequestMethod.GET)
	@ResponseBody
	public String doA() {
		System.out.println("doA 실행됨");
		
		return "doA";
	}
	
	@RequestMapping(value = "/doB", method = RequestMethod.GET)
	@ResponseBody
	public String doB() {
		System.out.println("doB 실행됨");
		return "doB";
	}
	
}
