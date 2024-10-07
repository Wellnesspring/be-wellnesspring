package com.bewellnesspring.certification.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bewellnesspring.certification.model.vo.UserEdit;
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
			@RequestParam("userId") String userId,
			@RequestParam("code") String code
			) throws IOException {
		return ResponseEntity.ok(new UserFront(service.useKakao(code, userId)));
	}
	
	/**
	 * 프로필 사진용 업로드 테스트
	 * @param profileImg 새 프로필 이미지
	 * @param profile 그 외 변경할 사용자 정보
	 * @return
	 * @throws IllegalStateException 이미지 저장 중 에러 발생
	 * @throws IOException 잘못된 사용자 정보로 인해 업데이트 실패
	 * @throws IllegalAccessException 사용자 정보 접근 중 에러 발생
	 * @throws GeneralSecurityException 사용자 정보 암호화 중 에러 발생
	 */
	@PutMapping("/profile/edit")
	public ResponseEntity<UserFront> uploadProfile(HttpServletRequest request,
			@RequestPart(name = "profileImgFile", required = false) MultipartFile profileImgFile,
			@RequestPart("profile") UserEdit profile
			) throws IllegalStateException, IOException, IllegalAccessException, GeneralSecurityException {
		return ResponseEntity.ok(new UserFront(service.uploadProfile(profile, profileImgFile, request)));
	}
	
	/**
	 * 프로필 수정에 필요한 사용자 정보 조회
	 * @param userId 사용자 ID
	 * @return 해당 id에 맞는 사용자 정보
	 */
	@GetMapping("data")
	public UserEdit loadProfile(@RequestParam("userId") String userId) {
		return new UserEdit(service.signIn(userId));
	}
	
	/**
	 * 계정 삭제
	 * @param userId 삭제할 계정의 Id
	 * @return
	 */
	@DeleteMapping("goodbye")
	public ResponseEntity<Object> deleteProfile(@RequestParam("userId") String userId) {
		service.deleteProfile(userId);
		return ResponseEntity.noContent().build();
	}
	
}
