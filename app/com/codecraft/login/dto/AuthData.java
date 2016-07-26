package com.codecraft.login.dto;

import org.json.JSONObject;

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
	
	public static String error(Exception ex) {
		JSONObject error = new JSONObject();
		error.put("error", true);
		error.put("message", ex.getMessage());
		return error.toString();
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setToken(long token) {
		this.token = token;
	}
}
