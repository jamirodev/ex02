package com.kh.ex02.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.ex02.dto.PagingDto;
import com.kh.ex02.service.BoardService;
import com.kh.ex02.service.LikeService;
import com.kh.ex02.util.FileUploadUtil;
import com.kh.ex02.vo.BoardVo;
import com.kh.ex02.vo.LikeVo;
import com.kh.ex02.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Resource(name = "uploadPath")
	private String uploadPath; // C:/zzz/upload
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private LikeService likeService;
	
	// localhost/board/register
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registGet() {
		System.out.println("registGet() 호출됨.");
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registPost(/* MultipartFile file, */BoardVo boardVo, RedirectAttributes rttr,
			HttpSession session) throws Exception {
		System.out.println("controller, register, boardVo:" + boardVo);
		// 작성자는 로그인한 사용자의 아이디
		UserVo userVo = (UserVo)session.getAttribute("loginInfo");
		boardVo.setWriter(userVo.getU_id());
		boardService.create(boardVo);
		rttr.addFlashAttribute("msg", "SUCCESS");
		return "redirect:/board/list";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(PagingDto pagingDto, Model model) throws Exception {
//		System.out.println("/board/list");
		int count = boardService.getCount(pagingDto);
		pagingDto = new PagingDto(
				pagingDto.getPage(), pagingDto.getPerPage(), count,
				pagingDto.getSearchType(), pagingDto.getKeyword());
//		System.out.println("pagingDto:" + pagingDto);
		List<BoardVo> list = boardService.listAll(pagingDto);
		model.addAttribute("list", list);
		model.addAttribute("pagingDto", pagingDto);
		return "board/list";
	}
	
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public String read(int bno, PagingDto pagingDto, Model model,
			HttpSession session) throws Exception {
//		System.out.println("read, pagingDto:" + pagingDto);
		UserVo userVo = (UserVo)session.getAttribute("loginInfo");
		BoardVo boardVo = boardService.read(bno);
		LikeVo likeVo = new LikeVo();
		likeVo.setBno(bno);
		likeVo.setU_id(userVo.getU_id());
		boolean likeResult = likeService.checkLike(likeVo);
		int likeCount = likeService.getLikeCount(bno);
		Map<String, Object> likeMap = new HashMap<>();
		likeMap.put("likeCount", likeCount);
		likeMap.put("likeResult", likeResult);
		model.addAttribute("boardVo", boardVo);
		model.addAttribute("likeMap", likeMap);
		return "board/read";
	}

	
	@RequestMapping(value = "/mod", method = RequestMethod.POST)
	public String mod(BoardVo boardVo, PagingDto pagingDto,
			HttpSession session) throws Exception {
		System.out.println("mod, boardVo:" + boardVo);
//		System.out.println("mod, pagingDto:" + pagingDto);
		UserVo userVo = (UserVo)session.getAttribute("loginInfo");
		boardService.update(boardVo, userVo.getU_id());
		String url = "redirect:/board/read";
		url += "?bno=" + boardVo.getBno();
		url += "&page=" + pagingDto.getPage();
		url += "&perPage=" + pagingDto.getPerPage();
		url += "&searchType=" + pagingDto.getSearchType();
		url += "&keyword=" + pagingDto.getKeyword();
		return url;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(int bno, PagingDto pagingDto, RedirectAttributes rttr) throws Exception {
		List<String> filenames = boardService.getAttachList(bno);
		for (String filename : filenames) {
			FileUploadUtil.deleteFile(uploadPath, filename);
		}
		boardService.delete(bno, filenames);
		rttr.addFlashAttribute("msg", "SUCCESS");
		String url = "redirect:/board/list";
		url += "?page=" + pagingDto.getPage();
		url += "&perPage=" + pagingDto.getPerPage();
		url += "&searchType=" + pagingDto.getSearchType();
		url += "&keyword=" + pagingDto.getKeyword();
		return url;
	}
	
	@RequestMapping(value = "/getAttachList/{bno}", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getAttachList(@PathVariable int bno) {
		List<String> attatchList = boardService.getAttachList(bno);
		return attatchList;
	}
}
