package com.codecraft.login.mongo;

import org.mongodb.morphia.annotations.Id;

public class User {
	@Id
	private long id;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
