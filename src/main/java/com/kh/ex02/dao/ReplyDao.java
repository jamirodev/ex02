package com.kh.ex02.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.ex02.vo.ReplyVo;

@Repository
public class ReplyDao {
	
	private final String NAMESPACE = "com.kh.ex02.ReplyMapper.";

	@Autowired
	private SqlSession sqlSession;
	
	// 댓글 목록
	public List<ReplyVo> list(int bno) {
		List<ReplyVo> list = sqlSession.selectList(
				NAMESPACE + "list", bno);
		return list;
	}
	
	// 댓글 추가
	public void insert(ReplyVo replyVo) {
		sqlSession.insert(NAMESPACE + "insert", replyVo);
	}
	
	// 댓글 수정
	public void update(ReplyVo replyVo) {
		sqlSession.update(NAMESPACE + "update", replyVo);
	}
	
	// 댓글 삭제
	public void delete(int rno) {
		sqlSession.delete(NAMESPACE + "delete", rno);
	}
	
	// 수정날짜 얻기
	public Timestamp getUpdatedate(int rno) {
		Timestamp updatedate = sqlSession.selectOne(NAMESPACE + "getUpdatedate", rno);
		return updatedate;
	}
}
