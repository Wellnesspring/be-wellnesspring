package com.bewellnesspring.certification.service;

import com.bewellnesspring.certification.model.dao.CertificationDao;
import com.bewellnesspring.certification.model.vo.EncodeField;
import com.bewellnesspring.certification.model.vo.User;
import com.bewellnesspring.common.AESCodec;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.security.GeneralSecurityException;

@Service
@RequiredArgsConstructor
public class CertificationService {

	private CertificationDao dao;
	private BCryptPasswordEncoder bCryptEncoder;


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

	public boolean signUp(User u) {
		try {
			return dao.signUp(userEncoding(u));
		} catch (Exception e) {
			return false;
		}
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
}
