package com.kh.ex02.controller;

import java.util.UUID;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.ex02.dto.LoginDto;
import com.kh.ex02.service.UserService;
import com.kh.ex02.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	@Autowired
	private UserService userService;
	
	// 로그인 폼 - get
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void loginForm() {
		
	}
	
	// 로그인 처리 - post
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginRun(LoginDto loginDto, HttpSession session,
			RedirectAttributes rttr) {
//		System.out.println("loginDto:" + loginDto);
		UserVo userVo = userService.login(loginDto);
		String returnPage = "redirect:/board/list";
		if (userVo != null) {
			String targetLocation = 
					(String)session.getAttribute("targetLocation");
//			System.out.println("targetLocation:" + targetLocation);
			if (targetLocation != null) {
				returnPage = "redirect:" + targetLocation;
			}
			session.removeAttribute("targetLocation");
			session.setAttribute("loginInfo", userVo);
		} else {
			rttr.addFlashAttribute("loginResult", "FAIL");
			returnPage = "redirect:/user/login";
		}
		return returnPage;
	}
	
	// 로그아웃
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate(); // 현재 세션 무효화
		return "redirect:/user/login"; // 로그인폼
	}
	
	@RequestMapping(value = "/forgotPassword", 
			method = RequestMethod.GET)
	public String forgotPassword() {
		return "/user/forgotPassword";
	}
	
	@RequestMapping(value = "/sendPassword", 
			method = RequestMethod.POST)
	public String sendPassword(String id, String email) {
		System.out.println("id:" + id);
		System.out.println("email:" + email);
		
		// 비밀번호 새로 생성 -> 테이블
		// 사용자에게 이메일 전송
		String uuid = UUID.randomUUID().toString();
		String newPass = uuid.substring(0, uuid.indexOf("-"));
		System.out.println("newPass:" + newPass);
		
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(
						mimeMessage,
						false, // multipart
						"utf-8"
						);
				
				helper.setFrom("jamirodev@gmail.com"); // 보내는이"
				helper.setTo(email); // 받는이
				helper.setSubject("비밀번호 변경 안내"); // 제목
				helper.setText("변경된 비밀번호:" + newPass); // 내용
				
			}
		};
		
		mailSender.send(preparator);
		
		return "redirect:/user/login";
	}
}
