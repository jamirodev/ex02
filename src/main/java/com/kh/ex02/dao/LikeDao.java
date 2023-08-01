package com.kh.ex02.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.ex02.vo.LikeVo;

@Repository
public class LikeDao {
	
	private static final String NAMESPACE= "com.kh.ex02.LikeMapper.";
	
	@Autowired
	private SqlSession sqlSession;
	
	public boolean addLike(LikeVo likeVo) {
		int count = sqlSession.insert(NAMESPACE + "addLike", likeVo);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	public boolean deleteLike(LikeVo likeVo) {
		int count = sqlSession.insert(NAMESPACE + "deleteLike", likeVo);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	public boolean checkLike(LikeVo likeVo) {
		int count = sqlSession.selectOne(NAMESPACE + "checkLike", likeVo);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	public int getLikeCount(int bno) {
		int count = sqlSession.selectOne(NAMESPACE + "getLikeCount", bno);
		return count;
	}
}
