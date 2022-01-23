package com.example.practice.client;

public interface Mapper {

	public UserInfo getUserInfo(String insertedId) throws RuntimeException;
	
	public void createUser(UserInfo user);
}
