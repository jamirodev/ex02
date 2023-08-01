package com.kh.ex02.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.ex02.commons.MyConstants;
import com.kh.ex02.dao.MessageDao;
import com.kh.ex02.dao.PointDao;
import com.kh.ex02.vo.MessageVo;

@Service
public class MessageService {
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private PointDao pointDao;
	
	@Transactional
	public void sendMessage(MessageVo messageVo) {
		// 메시지 테이블에 insert
		messageDao.create(messageVo);
		// 유저 테이블에 포인트 update
		pointDao.updatePoint(messageVo.getSender(), MyConstants.SEND_MESSAGE_POINT);
	}
	
	@Transactional
	public MessageVo readMessage(MessageVo messageVo) {
		// 메시지 테이블 - 쪽지 읽은 시간 업데이트
		messageDao.updateState(messageVo.getM_id());
		// 유저 테이블 - targetid 포인트 업데이트
		pointDao.updatePoint(messageVo.getTargetid(), MyConstants.READ_MESSAGE_POINT);
		messageVo = messageDao.readMessage(messageVo.getM_id());
		return messageVo;
	}
}
