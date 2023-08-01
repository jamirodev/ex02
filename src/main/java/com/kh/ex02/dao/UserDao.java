package com.kh.ex02.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.ex02.dto.LoginDto;
import com.kh.ex02.vo.UserVo;

@Repository
public class UserDao {
	
	private static final String NAMESPACE = "com.kh.ex02.UserMapper.";
	
	@Autowired
	private SqlSession sqlSession;

	public UserVo login(LoginDto loginDto) {
		UserVo userVo = sqlSession.selectOne(
				NAMESPACE + "login", loginDto);
		return userVo;
	}
}
