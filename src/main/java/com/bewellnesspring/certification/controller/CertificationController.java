package com.bewellnesspring.certification.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bewellnesspring.certification.model.vo.User;
import com.bewellnesspring.certification.service.CertificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class CertificationController {

	private final CertificationService service;

	/**
	 * 로그인 성공 후 실행되는 메서드
	 * @param u 사용자가 입력한 id, pw
	 * @return 해당 아이디로 검색된 사용자 정보
	 * @todo 나중에 사용자 정보들을 그때 그때 필요한 내용을 가져다 쓸건지 아니면 로그인시 한번에 주고 말건지 정해야함
	 */
	@GetMapping("/signinOk")
	public ResponseEntity<User> signIn() {
		User dbUser = service.signIn(SecurityContextHolder.getContext().getAuthentication());
		return ResponseEntity.ok(dbUser);
	}

	/**
	 * 새 사용자 등록
	 * @param u 새로이 등록되는 사용자 객체
	 * @return 등록 성공 여부
	 */
	@PostMapping("/signup")
	public ResponseEntity<Object> signUp(@RequestBody User u) {
		return new ResponseEntity<>(service.signUp(u) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("signoutOk")
	public ResponseEntity<String> signOut() {
		return ResponseEntity.ok("Log Out Successed");
	}
	/**
	 * 카카오 계정을 이용한 로그인
	 * @param code 사용자가 카카오에 요청하여 받아온 인증코드
	 * @param state csrf 대비용
	 * @return 사용자 정보
	 */
	@GetMapping("/kakao")
	public ResponseEntity<User> useKakao(@RequestParam String code, @RequestParam String state) {
		User u = service.useKakao(code, state);
		return u != null ? ResponseEntity.ok(u) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	}
}
