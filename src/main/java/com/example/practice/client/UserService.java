package com.example.practice.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.practice.LoginIdPwValidator;

@Service
public class UserService {

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	LoginIdPwValidator security;
	
	public UserInfo createUser (String id, String pw) {
		return null;
	}
}
