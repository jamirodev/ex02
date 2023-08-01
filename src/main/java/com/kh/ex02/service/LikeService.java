package com.kh.ex02.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.ex02.dao.LikeDao;
import com.kh.ex02.vo.LikeVo;

@Service
public class LikeService {

	@Autowired
	private LikeDao likeDao;
	
	public boolean addLike(LikeVo likeVo) {
		boolean result = likeDao.addLike(likeVo);
		return result;
	}
	
	public boolean deleteLike(LikeVo likeVo) {
		boolean result = likeDao.deleteLike(likeVo);
		return result;
	}
	
	public boolean checkLike(LikeVo likeVo) {
		boolean result = likeDao.checkLike(likeVo);
		return result;
	}
	
	public int getLikeCount(int bno) {
		int count = likeDao.getLikeCount(bno);
		return count;
	}
}
