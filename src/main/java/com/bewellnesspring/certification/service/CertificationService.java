package com.bewellnesspring.certification.service;

import com.bewellnesspring.certification.model.dao.CertificationDao;
import com.bewellnesspring.certification.model.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificationService {

	@Autowired
	private CertificationDao dao;


	public User signIn(User u) {
		User dbUser = dao.signIn(u.getUserId());

//		사용자가 입력한 정보와 db에 있는 내용이 일치하면
		if (u.getUserId().equals(dbUser.getUserId()) && u.getUserPw().equals(dbUser.getUserPw())) {
			return dbUser;
		}

		return null;
	}
}
