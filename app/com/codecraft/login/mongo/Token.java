package com.codecraft.login.mongo;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Property;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity("tokens")
public class Token {
	
	@Id
	private ObjectId id;

	@Indexed
	@Property("auth_token")
	private Long authToken;

	@Indexed
	@Property("user_id")
	private Long userId;

	@Indexed
	@Property("client_id")
	private String clientId;
	
	@Indexed(expireAfterSeconds = 60*30)
	private Date ttl;

	@Property("created_on")
	@JsonIgnore
	private Date createdOn;


	public Long getAuthToken() {
		return authToken;
	}

	public void setAuthToken(long l) {
		this.authToken = l;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Date getTtl() {
		return ttl;
	}

	public void setTtl(Date ttl) {
		this.ttl = ttl;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
}
