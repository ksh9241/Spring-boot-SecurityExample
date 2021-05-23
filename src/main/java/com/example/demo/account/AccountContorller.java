package com.example.demo.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountContorller {
	@Autowired
	AccountService accountSvc;
	
	// 새로운 Account를 만드는 과정. 실제론 setEmail, password 는 클라이언트에서 받는 값.
	@GetMapping("/create")
	public Account create() {
		Account account = new Account();
		account.setEmail("ksh9241@naver.com");
		account.setPassword("password");
		
		return accountSvc.save(account);
	}
}
