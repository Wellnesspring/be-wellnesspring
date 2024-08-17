package com.bewellnesspring.certification.model.dao;

import com.bewellnesspring.certification.model.vo.User;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CertificationDao {

	private SqlSession session;
	private final String MAP = "certificationMapper.";


	/**
	 * 사용자 정보 조회
	 * @param userId 사용자가 입력한 id
	 * @return 해당 아이디로 검색된 사용자 정보
	 */
	public User signIn(String userId) {
		return session.selectOne(MAP + "signIn", userId);
	}

	/**
	 * 새 사용자 등록
	 * @param u 새로이 등록되는 사용자 객체
	 * @return 등록 성공 여부
	 */
	public boolean signUp(User u) {
		return session.insert(MAP + "signUp", u) > 0;
	}

	public User signIn(int idNum) {
		return session.selectOne(MAP + "signInAtIdNum", idNum);
	}
}
