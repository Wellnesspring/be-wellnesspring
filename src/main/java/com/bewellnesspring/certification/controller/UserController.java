package com.bewellnesspring.certification.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bewellnesspring.certification.model.vo.UserFront;
import com.bewellnesspring.certification.service.CertificationService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

	private final CertificationService service;
	
	/**
	 * 카카오를 이용한 소셜 로그인 추가
	 * @param userId 사용자 계정
	 * @param code 카카오에 있는 사용자 정보에 접근할 인증코드
	 * @return 소셜 로그인 등록 성공
	 * @throws IOException 소셜 로그인 등록 실패
	 */
	@GetMapping("kakao")
	public ResponseEntity<UserFront> addKakao(
			@RequestParam(name = "userId") String userId,
			@RequestParam(name = "code") String code
			) throws IOException {
		return ResponseEntity.ok(service.useKakao(code, userId));
	}
	
	/**
	 * 프로필 사진용 업로드 테스트
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
