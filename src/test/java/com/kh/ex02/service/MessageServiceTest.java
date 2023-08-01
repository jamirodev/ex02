package com.kh.ex02.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kh.ex02.vo.MessageVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class MessageServiceTest {

	@Autowired
	private MessageService messageService;
	
	@Test
	public void testSendMessage() {
		// user01 -> user02 : "Hello"
		MessageVo messageVo = new MessageVo();
		messageVo.setSender("user01");
		messageVo.setTargetid("user02");
		messageVo.setMessage("Hello");
		
		messageService.sendMessage(messageVo);
	}
	
	@Test
	public void testReadMessage() {
		MessageVo messageVo = new MessageVo();
		messageVo.setTargetid("user02");
		messageVo.setM_id(1);
		messageVo = messageService.readMessage(messageVo);
		System.out.println(messageVo);
	}
}
