package com.example.hackathontest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hackathontest.User;
import com.example.hackathontest.service.LoginService;

@RestController
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	@PostMapping("/Validation")
	public User checkValidation(@RequestBody User user ) {
//		System.out.println(user);
		String UserID=(String)user.getUSER_IDENTIFIER();
		String Pass=(String)user.getPASSWORD();

		return loginService.ValidateLogin(UserID, Pass);
	}
	
}

