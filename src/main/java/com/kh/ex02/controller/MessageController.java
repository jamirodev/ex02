package com.kh.ex02.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.ex02.commons.MyConstants;
import com.kh.ex02.service.MessageService;
import com.kh.ex02.vo.MessageVo;
import com.kh.ex02.vo.UserVo;

@Controller
@RequestMapping("/message")
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
	@ResponseBody
	public String sendMessage(MessageVo messageVo, HttpSession session) {
		
		UserVo userVo = (UserVo)session.getAttribute(MyConstants.LOGIN);
		messageVo.setSender(userVo.getU_id());
		messageService.sendMessage(messageVo);
		userVo.setU_point(userVo.getU_point() + MyConstants.SEND_MESSAGE_POINT);
		session.setAttribute(MyConstants.LOGIN, userVo);
		return MyConstants.SUCCESS_MESSAGE;
	}
}
