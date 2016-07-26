package com.codecraft.login;

import java.util.List;
import java.util.Map;

import org.mongodb.morphia.query.Query;

import com.codecraft.login.dto.AuthData;
import com.codecraft.login.exception.InvalidDataException;
import com.codecraft.login.mongo.Local;
import com.codecraft.login.mongo.User;

import settings.Global;

public class LocalProvider extends AbstractLoginProvider implements LoginProvider {

	@Override
	public long signup(Map<String, String[]> data) throws InvalidDataException {
		Local local = getByEmail(getValue(data, "email"));
		if (local != null) {
			throw new InvalidDataException("Email already exists");
		}
		local = new Local();
		local.setEmail(getValue(data, "email"));
		local.setName(getValue(data, "name"));
		local.setPassword(getValue(data, "password"));
		local.setPhone(getValue(data, "phone"));
		local.setUserId(Utils.generateId());

		User user = new User();
		user.setId(local.getUserId());
		Global.getMorphiaObject().getDatastore().save(user);
		Global.getMorphiaObject().getDatastore().save(local);
		return user.getId();
	}

	private Local getByEmail(String email) {
		final Query<Local> query = Global.getMorphiaObject().getDatastore().createQuery(Local.class)
				.filter("email", email).limit(2);
		List<Local> asList = query.asList();
		if (asList.size() == 1) {
			return asList.get(0);
		}
		return null;
	}

	private Local validate(String username, String pwd) {
		Local user = getByEmail(username);
		if (user != null && user.getPassword().equals(pwd)) {
			return user;
		}
		return null;
	}

	@Override
	public long link(String userid, Map<String, String[]> data) throws InvalidDataException {
		User user = getUserById(Long.parseLong(userid));
		if (user == null) {
			throw new InvalidDataException("Invalid User");
		}
		Local local = validate(getValue(data, "email"), getValue(data, "password"));
		if (local == null)
			throw new InvalidDataException("Invalid email or password");
		local.setUserId(user.getId());
		Global.getMorphiaObject().getDatastore().save(local);
		return user.getId();
	}

	@Override
	public long unlink(String userid, Map<String, String[]> data) throws InvalidDataException {
		User user = getUserById(Long.parseLong(userid));
		if (user == null) {
			throw new InvalidDataException("Invalid User");
		}
		Local local = validate(getValue(data, "email"), getValue(data, "password"));
		if (local == null)
			throw new InvalidDataException("Invalid email or password");
		if (local.getUserId() != Long.parseLong(userid)) {
			throw new InvalidDataException("Invalid User");
		}
		local.setUserId(null);
		Global.getMorphiaObject().getDatastore().save(local);
		return user.getId();
	}

	@Override
	public AuthData signin(Map<String, String[]> data) throws InvalidDataException {
		Local local = validate(getValue(data, "email"), getValue(data, "password"));
		if (local == null)
			throw new InvalidDataException("Invalid email or password");
		User user = getUserById(local.getUserId());
		if (user != null) {
			return token(user);
		}
		return new AuthData();
	}
}
