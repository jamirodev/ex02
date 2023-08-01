package com.kh.ex02.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReplyVo {
	private int rno;
	private int bno;
	private String replytext;
	private String replyer;
	private Timestamp regdate;
	private Timestamp updatedate;
}
