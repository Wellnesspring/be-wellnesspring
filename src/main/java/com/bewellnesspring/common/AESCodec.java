package com.bewellnesspring.common;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;

public class AESCodec {

	/**
	 * AES 알고리즘을 이용한 인증키 발급
	 * @return 발급된 암호화 키
	 */
	public static String generateAESKey() throws GeneralSecurityException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(256);

		return SecretKeyToString(keyGenerator.generateKey());
	}

	/**
	 * AES 알고리즘을 이용하여 입력된 값을 암호화 한다
	 * @param AESKey 암호화 키
	 * @param originalData 암호화할 값
	 * @return 암호화된 값
	 * @exception GeneralSecurityException 사용자 정보 암호화 중 에러 발생
	 */
	public static String AESEncrypt(String AESKey, String originalData) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, StringToSecretKey(AESKey));

		byte[] encryptedBytes = cipher.doFinal(originalData.getBytes());

		return Base64.getEncoder().encodeToString(encryptedBytes); // Base64 인코딩하여 문자열로 반환
	}

	/**
	 * AES 알고리즘을 이용하여 암호화된 값을 복호화 한다
	 * @param AESKey 암호화에 사용했던 암호화 키
	 * @param encryptedData 복호화 할 값
	 * @return 복호화된 값
	 * @exception GeneralSecurityException 사용자 정보 복호화 중 에러 발생
	 */
	public static String AESDecrypt(String AESKey, String encryptedData) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, StringToSecretKey(AESKey));

		return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedData)), StandardCharsets.UTF_8);
	}

	/**
	 * SecretKey 객체를 String 으로 변환한다
	 * @param AESKey 변환할 SecretKey
	 * @return String 으로 변환된 SecretKey
	 */
	private static String SecretKeyToString(SecretKey AESKey) {
		return Base64.getEncoder().encodeToString(AESKey.getEncoded());
	}

	/**
	 * String 객체를 SecretKey 으로 변환한다
	 * @param AESKey 변환할 SecretKey(String)
	 * @return SecretKey 으로 변환된 SecretKey
	 */
	private static SecretKey StringToSecretKey(String AESKey) {
		return new SecretKeySpec(Base64.getDecoder().decode(AESKey),"AES");
	}
}
