package com.bewellnesspring.certification.controller;

import com.bewellnesspring.certification.model.vo.User;
import com.bewellnesspring.certification.service.CertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CertificationController {

	private CertificationService service;

	/**
	 * 사용자 정보 조회
	 * @param u 사용자가 입력한 id, pw
	 * @return 해당 아이디로 검색된 사용자 정보
	 */
	@PostMapping("/signin")
	public ResponseEntity<User> signIn(@RequestBody User u) {
		if(u.getUserId() == null || u.getUserPw() == null) { // 사용자가 입력한 아이디, 비밀번호가 하나라도 누락된 경우
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		User dbUser = service.signIn(u);
		return dbUser != null ? new ResponseEntity<>(dbUser, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * 새 사용자 등록
	 * @param u 새로이 등록되는 사용자 객체
	 * @return 등록 성공 여부
	 */
	@PostMapping("/signup")
	public ResponseEntity<Object> signUp(@RequestBody User u) {
		return service.signUp(u)? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
