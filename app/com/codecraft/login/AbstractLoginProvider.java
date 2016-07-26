package com.codecraft.login;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.mongodb.morphia.query.Query;

import com.codecraft.login.dto.AuthData;
import com.codecraft.login.mongo.Token;
import com.codecraft.login.mongo.User;

import settings.Global;

public class AbstractLoginProvider {
	
	String getValue(Map<String, String[]> data, String key) {
		if(data.get(key) != null && data.get(key).length > 0) {
			return data.get(key)[0];
		}
		return null;
	}
	
	User getUserById(long uid) {
		final Query<User> query = Global.getMorphiaObject().getDatastore().createQuery(User.class)
				.filter("_id", uid).limit(2);
		List<User> asList = query.asList();
		if(asList.size() == 1) return asList.get(0);
		return null;
	}
	
	AuthData token(User user) {
		Token token = new Token();
		token.setAuthToken(Utils.generateId());
		token.setTtl(new Date());
		token.setUserId(user.getId());
		Global.getMorphiaObject().getDatastore().save(token);
		
		AuthData data = new AuthData();
		data.setToken(token.getAuthToken());
		data.setUserId(token.getUserId());
		return data;
	}
}
