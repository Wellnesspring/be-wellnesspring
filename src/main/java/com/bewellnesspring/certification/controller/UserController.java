package com.bewellnesspring.certification.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("test")
public class UserController {

	/**
	 * 파일 업로드 테스트
	 * @param file
	 * @param request
	 * @return
	 */
	@PostMapping("/upload")
	public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException {
		String path = request.getSession().getServletContext().getRealPath("upload");
		File img = new File(path, file.getOriginalFilename());
		
		img.mkdirs();
		
		if(file.getContentType().contains("image"))
			file.transferTo(img);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
