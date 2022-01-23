package com.example.practice.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class UserMapper implements Mapper{
	
	private Map<String, UserInfo> users = new HashMap<>();

	@Override
	public UserInfo getUserInfo(String insertedId) throws RuntimeException {
		if (users.get(insertedId) == null) {
			return null;
		}
		
		return users.get(insertedId);
	}

	@Override
	public void createUser(UserInfo user) {
		users.put(user.getId(), user);
	}
}
