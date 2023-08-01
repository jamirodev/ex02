package com.kh.ex02.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kh.ex02.vo.ReplyVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class ReplyDaoTest {

	@Autowired
	private ReplyDao replyDao;
	
	// 입력 테스트
	@Test
	public void testInsert() {
		ReplyVo replyVo = new ReplyVo();
		replyVo.setBno(501); // 글번호 501번인 글의 댓글
		replyVo.setReplytext("댓글-1");
		replyVo.setReplyer("댓글러-1");
		replyDao.insert(replyVo);
	}
	
	
	// 목록 테스트
	@Test
	public void testList() {
		List<ReplyVo> list = replyDao.list(501);
		System.out.println(list);
	}
	
	// 갱신 테스트
	@Test
	public void testUpdate() {
		ReplyVo replyVo = new ReplyVo();
		replyVo.setReplytext("댓글수정");
		replyVo.setReplyer("수정자");
		replyVo.setRno(2);
		replyDao.update(replyVo);
	}
	
	
	// 삭제 테스트
	@Test
	public void testDelete() {
		replyDao.delete(2);
	}
}
