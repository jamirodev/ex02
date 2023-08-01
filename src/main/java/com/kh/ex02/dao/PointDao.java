package com.kh.ex02.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PointDao {
	
	private final String NAMESPACE = "com.kh.ex02.PointMapper.";
	
	@Autowired
	private SqlSession sqlSession;
	
	public void updatePoint(String u_id, int point) {
		Map<String, Object> map = new HashMap<>();
		map.put("u_id", u_id);
		map.put("point", point);
		sqlSession.update(NAMESPACE + "updatePoint", map);
	}
}
