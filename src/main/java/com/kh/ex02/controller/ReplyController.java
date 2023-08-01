package com.kh.ex02.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kh.ex02.service.ReplyService;
import com.kh.ex02.vo.ReplyVo;
import com.kh.ex02.vo.UserVo;

@RestController
@RequestMapping("/reply")
public class ReplyController {
	
	@Autowired
	private ReplyService replyService;
	
	// 목록
	// @RequestParam : ?bno=501
	// @PathVariable : /{bno}
	@RequestMapping(value = "/list/{bno}", method = RequestMethod.GET)
	public List<ReplyVo> list(@PathVariable("bno") int bno) {
		List<ReplyVo> list = replyService.list(bno);
		return list;
	}
	
	// 추가
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insert(@RequestBody ReplyVo replyVo, 
			HttpSession session) {
		System.out.println("controller, replyVo:" + replyVo);
		UserVo userVo = (UserVo)session.getAttribute("loginInfo");
		replyVo.setReplyer(userVo.getU_id());
		replyService.insert(replyVo);
		return "success";
	}
	
	// 수정 ("/reply/update")
	@RequestMapping(value = "/update", method = RequestMethod.PATCH)
	public Timestamp update(@RequestBody ReplyVo replyVo) {
//		System.out.println(replyVo);
		replyService.update(replyVo);
		Timestamp updatedate = replyService.getUpdatedate(replyVo.getRno());
		return updatedate;
	}
	
	// 삭제
	@RequestMapping(value = "/delete/{rno}/{bno}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("rno") int rno,
						 @PathVariable("bno") int bno) {
//		System.out.printf("rno:%d, bno:%d\n", rno, bno);
		replyService.delete(rno, bno);
		return "success";
	}
}
