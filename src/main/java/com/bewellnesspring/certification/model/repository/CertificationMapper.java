package com.bewellnesspring.certification.model.repository;

import com.bewellnesspring.certification.model.vo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CertificationMapper {

	/**
	 * 사용자 정보 조회
	 * @param userId 사용자가 입력한 id
	 * @return 해당 아이디로 검색된 사용자 정보
	 */
	User signIn(String userId);

	/**
	 * 새 사용자 등록
	 * @param u 새로이 등록되는 사용자 객체
	 * @return 등록 성공 여부
	 */
	boolean signUp(User u);

	User signInAtIdNum(int idNum);
}
