package com.codecraft.login.dto;

public class AuthData {
	private long userId;
	private long token;

	public long getUserId() {
		return userId;
	}

	public void setUserId(Long uid) {
		this.userId = uid;
	}

	public long getToken() {
		return token;
	}

	public void setToken(Long token) {
		this.token = token;
	}
}
