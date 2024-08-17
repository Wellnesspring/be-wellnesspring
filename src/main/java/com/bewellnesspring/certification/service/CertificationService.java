package com.bewellnesspring.certification.service;

import com.bewellnesspring.certification.model.dao.CertificationDao;
import com.bewellnesspring.certification.model.vo.EncodeField;
import com.bewellnesspring.certification.model.vo.User;
import com.bewellnesspring.common.AESCodec;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CertificationService {

	private CertificationDao dao;
	private BCryptPasswordEncoder bCryptEncoder;

	@Value("@{social.kakao.api-key}")
	private String apiKey;
	@Value("@{social.kakao.redirect-url}")
	private String redirectUrl;


	public User signIn(User u) {
		try {
			User dbUser = userDecoding(dao.signIn(u.getUserId()));

//			사용자가 입력한 정보와 db에 있는 내용이 일치하면
			if (u.getUserId().equals(dbUser.getUserId()) && u.getUserPw().equals(dbUser.getUserPw())) {
				return dbUser;
			}
		} catch (Exception ignored) {}
		return null;
	}

	public User signIn(int idNum) {
		try {
			return userDecoding(dao.signIn(idNum));
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

	public User useKakao(String code, String state) {
		if(state.equals("wellnesspring") && code != null && !code.isEmpty()) {
			int idNum = getUserFromKakao(getTokenFromKakao(code));
			return signIn(idNum);
		}
		return null;
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
			String value = (String) field.get(u); // 필드 값 가져오기

			if(value != null) {
				if(field.isAnnotationPresent(EncodeField.class)) { // 인코딩 하고자 하는 필드라면
					field.set(u, AESCodec.AESEncrypt(AESKey, value)); // 인코딩된 값 추가
				} else {
					field.set(u, value);
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
	private User userDecoding(User u) throws IllegalAccessException, GeneralSecurityException {
		if(u == null) return null;

		Field[] fields = User.class.getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true); // private 필드 접근 허용
			String value = (String) field.get(u); // 필드 값 가져오기

			if(value != null) {
				if(field.isAnnotationPresent(EncodeField.class)) { // 인코딩 하고자 하는 필드라면
					field.set(u, AESCodec.AESDecrypt(u.getLocker(), value)); // 인코딩된 값 추가
				} else {
					field.set(u, value);
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
		HashMap<String, String> header = new HashMap<>();
		header.put("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		String param = "grant_type=authorization_code" +
				"&client_id=" + apiKey +
				"&redirect_uri=" + redirectUrl +
				"&code=" + code;

		return sendHttpRequest(authTokenProvider, "POST", header, param).getAsJsonObject().get("access_token").getAsString();
	}

	/**
	 * 토큰으로 사용자 정보(id num) 받아오기
	 * @param token 사용자 정보에 접근할 수 있는 토큰
	 * @return 사용자 정보가 담긴 객체
	 */
	private int getUserFromKakao(String token) {
		String userInfoProvider = "https://kapi.kakao.com/v2/user/me";
		HashMap<String, String> header = new HashMap<>();
		header.put("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		header.put("Authorization", "Bearer " + token);

		return sendHttpRequest(userInfoProvider, "GET", header, null).get("id").getAsInt();
	}

	/**
	 * http 요청 수행(응답이 json 객체일 경우)
	 * @param url    요청을 보낼 주소
	 * @param method 요청 방식
	 * @param header 요청의 header에 추가 할 욥션
	 * @param body   요청의 body
	 * @return 응답인 InputStreamReader 객체
	 */
	private JsonObject sendHttpRequest(String url, String method, HashMap<String, String> header, String body) {
		URL urlObj;
		Set<String> methods = new HashSet<>(Arrays.asList("POST", "PUT", "DELETE"));

		try {
			urlObj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();

			conn.setRequestMethod(method);
			if(methods.contains(method) && body != null) {
				conn.setDoOutput(true); // 이 요청에 본문을 포함하겠다는 옵션.
			}

//			헤더가 있으면 헤더 설정
			if(header != null && !header.isEmpty()) {
				Set<String> keys = header.keySet();
				for(String key : keys) {
					conn.setRequestProperty(key, header.get(key));
				}
			}

			try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {
				if(body != null) {bw.write(body);}
				bw.flush();

				Reader reader = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
				return JsonParser.parseReader(reader).getAsJsonObject();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
