package com.codecraft.login.mongo;

import org.mongodb.morphia.annotations.Id;

public class Facebook {
	@Id
	private long fbid;
	private String email;
	private String name;
	
	private Long userId;

	public long getFbid() {
		return fbid;
	}

	public void setFbid(long fbid) {
		this.fbid = fbid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
