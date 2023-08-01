package com.kh.ex02.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.ex02.dao.BoardDao;
import com.kh.ex02.dto.PagingDto;
import com.kh.ex02.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
	@Transactional
	public void create(BoardVo boardVo) throws Exception {
		int bno = boardDao.getNextSeq();
		boardVo.setBno(bno);
		boardDao.create(boardVo); // tbl_board
		
		if (boardVo.getFiles() != null) {
			for (String filename : boardVo.getFiles()) {
				boardDao.insertAttach(filename, bno); // tbl_attach
			}
		}
	}

	
	public List<BoardVo> listAll(PagingDto pagingDto) throws Exception {
		List<BoardVo> list = boardDao.listAll(pagingDto);
		return list;
	}

	
	public BoardVo read(int bno) throws Exception {
		BoardVo boardVo = boardDao.read(bno);
		return boardVo;
	}

	@Transactional
	public void update(BoardVo boardVo, String u_id) throws Exception {
		boardDao.update(boardVo, u_id);
		if (boardVo.getFiles() != null) {
			for (String fullname : boardVo.getFiles()) {
				boardDao.insertAttach(fullname, boardVo.getBno());
			}
		}
	}

	@Transactional
	public void delete(int bno, List<String> filenames) throws Exception {
		for (String fullname : filenames) {
			boardDao.deleteAttach(fullname);
		}
		boardDao.delete(bno);
	}
	
	public int getCount(PagingDto pagingDto) throws Exception {
		int count = boardDao.getCount(pagingDto);
		return count;
	}
	
	public List<String> getAttachList(int bno) {
		List<String> attachList = boardDao.getAttachList(bno);
		return attachList;
	}
	
	public void deleteAttach(String fullname) {
		boardDao.deleteAttach(fullname);
	}

}
