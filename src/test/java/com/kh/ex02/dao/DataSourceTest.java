package com.kh.ex02.dao;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})

public class DataSourceTest {

	@Autowired
	private DataSource dataSource;
	
	@Test
	public void testConnection() throws Exception {
		try(Connection conn = dataSource.getConnection()) {
			System.out.println("conn:" + conn);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
