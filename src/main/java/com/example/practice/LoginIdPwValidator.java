package com.example.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.practice.client.UserInfo;
import com.example.practice.client.UserMapper;

@Service
public class LoginIdPwValidator implements UserDetailsService{
	
	@Bean
	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
		return new SHA512PasswordEncoder();
	}
	
	@Autowired
	private UserMapper mapper;

	@Override
	public UserDetails loadUserByUsername(String insertedId) throws UsernameNotFoundException {
		UserInfo user = mapper.getUserInfo(insertedId);
		
		if (user == null) {
			return null;
		}
		
		String pw = user.getPw();
		
		String roles = user.getRoles();
		return User.builder()
					.username(insertedId)
					.password(pw)
					.roles(roles)
					.build()
				;
	}

}
