package com.kh.ex02.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BoardVo {
	private int bno;
	private String title;
	private String content;
	private String writer;
	private Timestamp regdate;
	private int viewcnt;
	private int replycnt;
	
	// 첨부파일
	private String[] files;
}
