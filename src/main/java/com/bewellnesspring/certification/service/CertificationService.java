package com.bewellnesspring.certification.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.bewellnesspring.certification.model.repository.CertificationMapper;
import com.bewellnesspring.certification.model.vo.EncodeField;
import com.bewellnesspring.certification.model.vo.Social;
import com.bewellnesspring.certification.model.vo.User;
import com.bewellnesspring.certification.model.vo.UserFront;
import com.bewellnesspring.common.AESCodec;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificationService implements UserDetailsService {

	private final BCryptPasswordEncoder bCryptEncoder;
	private final CertificationMapper dao;

	@Value("${social.kakao.api-key}")
	private String apiKey;
	@Value("${social.kakao.redirect-url}")
	private String redirectUrl;

	public UserFront signIn(Authentication authentication) {
		try {
			User user = userDecoding(dao.signIn(authentication.getName()));
			return new UserFront(user);
		} catch (Exception ignored) {return null;}
	}

	public UserFront signIn(long idNum) {
		try {
			User user = userDecoding(dao.signInAtIdNum(idNum));
			return new UserFront(user);
		} catch (Exception ignored) {}
		return null;
	}

	public boolean signUp(User u) {
		if(u == null) return false;
		try {
			return dao.signUp(userEncoding(u));
		} catch (Exception e) {
			return false;
		}
	}

	public boolean idCheck(String idck) {
		return dao.signIn(idck) != null;
	}

	public UserFront useKakao(String code, String userId) throws IOException {
		long idNum = 0;
		
		if(code != null && !code.isEmpty()) {
			idNum = getUserFromKakao(getTokenFromKakao(code));
		}
		
		if(idNum != 0 && userId != null && userId.length() > 0 ) {	// 카카오를 이용한 소셜 로그인 등록
			if(dao.addKakao(new Social(userId, idNum, "kakao")) == 0) {
				throw new IOException("소셜 로그인 등록 실패");
			}
		}
		return signIn(idNum);
	}

	/**
	 * 회원가입시 입력된 사용자 개인정보 암호화
	 * @param u 사용자가 입력한 정보 객체
	 * @return 주요 개인정보가 암호화된 객체
	 */
	private User userEncoding(User u) throws IllegalAccessException, GeneralSecurityException {
		if(u == null) return null;

		String AESKey = AESCodec.generateAESKey();
		Field[] fields = User.class.getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true); // private 필드 접근 허용
			Object value = field.get(u); // 필드 값 가져오기

			if(value != null) {
				if(field.isAnnotationPresent(EncodeField.class)) { // 인코딩 하고자 하는 필드라면
					field.set(u, AESCodec.AESEncrypt(AESKey, (String) value)); // 인코딩된 값 추가
				}
			}
		}
		u.setUserPw(bCryptEncoder.encode(u.getUserPw()));
		u.setLocker(AESKey);

		return u;
	}

	/**
	 * DB에서 조회한 암호화된 사용자 정보 목호화
	 * @param u DB에서 조회한 암호화된 사용자 정보
	 * @return 복호화된 사용자 정보
	 */
	public User userDecoding(User u) throws IllegalAccessException, GeneralSecurityException {
		if(u == null) return null;

		Field[] fields = User.class.getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true); // private 필드 접근 허용
			Object value = field.get(u); // 필드 값 가져오기

			if(value != null) {
				if(field.isAnnotationPresent(EncodeField.class)) { // 디코딩 하고자 하는 필드라면
					field.set(u, AESCodec.AESDecrypt(u.getLocker(), (String) value)); // 디코딩된 값 추가
				}
			}
		}
		return u;
	}

	/**
	 * 사용자가 받아온 인증코드로 사용자 정보에 접근할 수 있는 토큰 받아오기
	 * @param code 사용자가 받아온 인증코드
	 * @return 사용자 정보에 접근할 수 있는 토큰
	 */
	private String getTokenFromKakao(String code) {
		String authTokenProvider = "https://kauth.kakao.com/oauth/token";
		HttpHeaders header = new HttpHeaders();
		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		RestTemplate rt = new RestTemplate();
		Gson gson = new Gson();

		header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		param.add("grant_type", "authorization_code");
		param.add("client_id", apiKey);
		param.add("redirect_uri", redirectUrl);
		param.add("code", code);
		
		String responseBody = rt.exchange(
                authTokenProvider,
                HttpMethod.POST,
                new HttpEntity<>(param, header),
                String.class
			).getBody();
		return gson.fromJson(responseBody, JsonObject.class).get("access_token").getAsString();
	}

	/**
	 * 토큰으로 사용자 정보(id num) 받아오기
	 * @param token 사용자 정보에 접근할 수 있는 토큰
	 * @return 사용자 정보가 담긴 객체
	 */
	private long getUserFromKakao(String token) {
		String userInfoProvider = "https://kapi.kakao.com/v2/user/me";
		HttpHeaders header = new HttpHeaders();
		RestTemplate rt = new RestTemplate();
		Gson gson = new Gson();
		
		header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		header.add("Authorization", "Bearer " + token);
		
		String responseBody = rt.exchange(userInfoProvider, HttpMethod.GET, new HttpEntity<>(header), String.class).getBody();
		return gson.fromJson(responseBody, JsonObject.class).get("id").getAsLong();
	}

	/**
	 * spring security가 내부적으로 로그인할 때 사용하는 메서드(절대 삭제 금지)
	 * @param username 사용자가 입력한 id
	 * @return 인증된 사용자임을 증명하는 시큐리티가 발급하는 토큰?
	 */
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		User user = dao.signIn(userId);
		GrantedAuthority authority = null;

		if(user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		authority = new SimpleGrantedAuthority((user.getRule() == 0)? "ROLE_USER" : "ROLE_ADMIN");

		return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getUserPw(), Collections.singletonList(authority));
	}
}
