package com.kh.ex02.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.ex02.dao.BoardDao;
import com.kh.ex02.dao.ReplyDao;
import com.kh.ex02.vo.ReplyVo;

@Service
public class ReplyService {

	@Autowired
	private ReplyDao replyDao;
	
	@Autowired
	private BoardDao boardDao;
	
	// 목록
	public List<ReplyVo> list(int bno) {
		List<ReplyVo> list = replyDao.list(bno);
		return list;
	}
	
	// 추가
	@Transactional
	public void insert(ReplyVo replyVo) {
		replyDao.insert(replyVo);
		boardDao.updateReplycnt(replyVo.getBno(), 1);
	}
	
	// 수정
	public void update(ReplyVo replyVo) {
		replyDao.update(replyVo);
	}
	
	// 삭제
	@Transactional
	public void delete(int rno, int bno) {
		replyDao.delete(rno);
		boardDao.updateReplycnt(bno, -1);
	}
	
	// 수정 시간 얻기
	public Timestamp getUpdatedate(int rno) {
		Timestamp updatedate = replyDao.getUpdatedate(rno);
		return updatedate;
	}
	
}
