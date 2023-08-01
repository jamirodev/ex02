package com.kh.ex02.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.ex02.dto.PagingDto;
import com.kh.ex02.vo.BoardVo;

@Repository
public class BoardDao {
	
	private final String NAMESPACE = "com.kh.ex02.BoardMapper.";

	@Autowired
	private SqlSession sqlSession;

	public void create(BoardVo boardVo) throws Exception {
		System.out.println("dao, boardVo:" + boardVo);
		sqlSession.insert(NAMESPACE + "create", boardVo);
	}

	public List<BoardVo> listAll(PagingDto pagingDto) throws Exception {
		List<BoardVo> list = sqlSession.selectList(NAMESPACE + "listAll", pagingDto);
		return list;
	}

	public BoardVo read(int bno) throws Exception {
		BoardVo boardVo = sqlSession.selectOne(NAMESPACE + "read", bno);
		return boardVo;
	}

	public void update(BoardVo boardVo, String u_id) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("boardVo", boardVo);
		map.put("u_id", u_id);
		sqlSession.update(NAMESPACE + "update", map);
	}

	public void delete(int bno) throws Exception {
		sqlSession.delete(NAMESPACE + "delete", bno);
	}
	
	public int getCount(PagingDto pagingDto) throws Exception {
		int count = sqlSession.selectOne(NAMESPACE + "getCount", pagingDto);
		return count;
	}
	
	public void updateReplycnt(int bno, int amount) {
		Map<String, Integer> map = new HashMap<>();
		map.put("amount", amount);
		map.put("bno", bno);
		sqlSession.update(NAMESPACE + "updateReplycnt", map);
	}
	
	public int getNextSeq() {
		int bno = sqlSession.selectOne(NAMESPACE + "getNextSeq");
		return bno;
	}
	
	public void insertAttach(String fullname, int bno) {
		Map<String, Object> map = new HashMap<>();
		map.put("fullname", fullname);
		map.put("bno", bno);
		sqlSession.insert(NAMESPACE + "insertAttach", map);
	}
	
	public List<String> getAttachList(int bno) {
		List<String> attachList = sqlSession.selectList(
				NAMESPACE + "getAttachList", bno);
		return attachList;
	}
	
	public void deleteAttach(String fullname) {
		sqlSession.delete(NAMESPACE + "deleteAttach", fullname);
	}

}
