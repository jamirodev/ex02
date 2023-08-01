package com.kh.ex02.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.ex02.vo.MessageVo;

@Repository
public class MessageDao {
	
	private static final String NAMESPACE = "com.kh.ex02.MessageMapper.";
	
	@Autowired
	private SqlSession sqlSession;

	// 생성
	public void create(MessageVo messageVo) { 
		sqlSession.insert(NAMESPACE + "create", messageVo);
	}
	
	// 읽기
	public MessageVo readMessage(int m_id) { 
		MessageVo messageVo = sqlSession.selectOne(NAMESPACE + "readMessage", m_id);
		return messageVo;
	}
	
	// 읽은 날짜 변경
	public void updateState(int m_id) { 
		sqlSession.update(NAMESPACE + "updateState", m_id);
	}
}
