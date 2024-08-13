package com.bewellnesspring.certification.controller;

import com.bewellnesspring.certification.model.vo.User;
import com.bewellnesspring.certification.service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CertificationController {

	@Autowired
	private CertificationService service;


	@PostMapping("/signin")
	public User signIn(@RequestBody User u) {
		return service.signIn(u);
	}

	@PostMapping("/signup")
	public boolean signIUp(@RequestBody User u) {
		return service.signUp(u);
	}
}
