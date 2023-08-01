package com.kh.ex02.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kh.ex02.dto.PagingDto;
import com.kh.ex02.vo.BoardVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class BoardDaoTest {

	@Autowired
	private BoardDao boardDao;
	
	@Test
	public void testCreate() throws Exception {
		BoardVo boardVo = new BoardVo();
		boardVo.setTitle("제목-1");
		boardVo.setContent("내용-1");
		boardVo.setWriter("홍길동");
		boardDao.create(boardVo);
	}
	
	@Test
	public void testCreate500() throws Exception {
		for (int i = 1; i <= 500; i++) {
			BoardVo boardVo = new BoardVo();
			boardVo.setTitle("제목-" + i);
			boardVo.setContent("내용-" + i);
			boardVo.setWriter("작성자-" + i);
			boardDao.create(boardVo);
			Thread.sleep(100);
		}
	}
	
	@Test
	public void testListAll() throws Exception {
		PagingDto pagingDto = new PagingDto(1, 10, 10, "t", "목-4");
		List<BoardVo> list = boardDao.listAll(pagingDto);
		for (BoardVo boardVo : list) {
			System.out.println(boardVo);
		}
	}
	
	@Test
	public void testRead() throws Exception {
		boardDao.read(1);
	}
	
	@Test
	public void testUpdate() throws Exception {
		BoardVo boardVo = boardDao.read(1);
		boardVo.setTitle("제목-1-수정");
		boardVo.setContent("내용-1-수정");
		boardVo.setWriter("김길동");
		boardDao.update(boardVo);
	}
	
	@Test
	public void testDelete() throws Exception {
		boardDao.delete(1);
	}
}
