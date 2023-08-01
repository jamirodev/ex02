package com.kh.ex02.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.ex02.service.LikeService;
import com.kh.ex02.vo.LikeVo;
import com.kh.ex02.vo.UserVo;

@Controller
@RequestMapping("/like")
public class LikeController {
	
	@Autowired
	private LikeService likeService;
	
	@RequestMapping(value = "/addLike/{bno}", method = RequestMethod.POST)
	@ResponseBody
	public String addLike(@PathVariable int bno, HttpSession session) {
		System.out.println("LikeController, addLike, bno:" + bno);
		UserVo userVo = (UserVo)session.getAttribute("loginInfo");
		System.out.println("LikeController, addLike, userVo:" + userVo);
		LikeVo likeVo = new LikeVo();
		System.out.println("LikeController, addLike, likeVo1:" + likeVo);
		likeVo.setBno(bno);
		likeVo.setU_id(userVo.getU_id());
		System.out.println("LikeController, addLike, likeVo2:" + likeVo);
		boolean result = likeService.addLike(likeVo);
		return String.valueOf(result);
	}
	
	@RequestMapping(value = "/deleteLike/{bno}", method = RequestMethod.POST)
	@ResponseBody
	public String deleteLike(@PathVariable int bno, HttpSession session) {
		System.out.println("LikeController, deleteLike, bno:" + bno);
		UserVo userVo = (UserVo)session.getAttribute("loginInfo");
		LikeVo likeVo = new LikeVo();
		likeVo.setBno(bno);
		likeVo.setU_id(userVo.getU_id());
		System.out.println("LikeController, deleteLike, likeVo:" + likeVo);
		boolean result = likeService.deleteLike(likeVo);
		return String.valueOf(result);
	}
}
