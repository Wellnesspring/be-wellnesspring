package com.bewellnesspring.certification.model.repository;

import com.bewellnesspring.certification.model.vo.Social;
import com.bewellnesspring.certification.model.vo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CertificationMapper {

	/**
	 * 사용자 정보 조회
	 * @param userId 사용자가 입력한 id
	 * @return 해당 아이디로 검색된 사용자 정보
	 */
	User signIn(String userId);
	
	User signInAtIdNum(long idNum);

	/**
	 * 새 사용자 등록
	 * @param u 새로이 등록되는 사용자 객체
	 * @return 등록 성공 여부
	 */
	boolean signUp(User u);

	/*
	*
	 */
	List<User> findUserIdWhoAgreeAlram();

	/**
	 * 카카오를 이용하는 소셜로그인 추가
	 * @param name userId
	 * @param idNum 카카오측 사용자 식별값
	 */
	int addKakao(Social social);

	int uploadProfile(User userEncoding);
}
