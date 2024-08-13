package com.bewellnesspring.certification.model.dao;

import com.bewellnesspring.certification.model.vo.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CertificationDao {

	@Autowired
	private SqlSession session;
	private final String MAP = "certificationMapper.";


	/**
	 * 사용자 정보 조회
	 * @param userId 사용자가 입력한 id
	 * @return 해당 아이디로 검색된 사용자 정보
	 */
	public User signIn(String userId) {
		return session.selectOne(MAP + "signin", userId);
	}

	public boolean signUp(User u) {
		return session.insert(MAP + "signUp", u) > 0;
	}
}
