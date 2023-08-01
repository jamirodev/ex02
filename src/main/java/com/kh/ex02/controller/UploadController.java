package com.kh.ex02.controller;

import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.ex02.service.BoardService;
import com.kh.ex02.util.FileUploadUtil;

@RestController
@RequestMapping("/upload")
public class UploadController {
	
	@Resource(name = "uploadPath")
	private String uploadPath; // C:/zzz/upload
	
	@Autowired
	private BoardService boardService;
		
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST,
			produces = "text/plain;charset=utf-8")
	public String uploadFile(MultipartFile file) {
		System.out.println("originalFileName:" + file.getOriginalFilename());
		try {
			byte[] bytes = file.getBytes(); // 바이너리 데이터
			String saveFilename = FileUploadUtil.upload(
					bytes, uploadPath, file.getOriginalFilename());
			return saveFilename;
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	// /upload/displayImage
	@RequestMapping(value = "/displayImage", method = RequestMethod.GET)
	public byte[] displayImage(String filePath) {
		System.out.println("filePath:" + filePath);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(uploadPath + filePath);
			// ->  C:/zzz/upload/2023/7/24/ab416116-a925-478e-aa7e-872546ae183b_test.txt
			
			// org.apache.commons.io.IOUtils
			byte[] data = IOUtils.toByteArray(fis);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	// 첨부파일 삭제
	@RequestMapping(value = "/deleteFile", method = RequestMethod.DELETE)
	public String deleteFile(@RequestBody String filename) {
		System.out.println("deletefile, filename:" + filename);
		FileUploadUtil.deleteFile(uploadPath, filename);
		System.out.println("deleted");
		return "SUCCESS";
	}
	
	@RequestMapping(value = "/deleteAttach", method = RequestMethod.DELETE)
	public String deleteAttach(@RequestBody String filename) {
		System.out.println("filename:" + filename);
		FileUploadUtil.deleteFile(uploadPath, filename);
		boardService.deleteAttach(filename);
		return "SUCCESS";
	}
	
}
