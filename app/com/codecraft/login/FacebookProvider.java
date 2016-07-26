package com.codecraft.login;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.mongodb.morphia.query.Query;

import com.codecraft.login.dto.AuthData;
import com.codecraft.login.exception.InvalidDataException;
import com.codecraft.login.mongo.Facebook;
import com.codecraft.login.mongo.User;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import play.Play;
import settings.Global;

public class FacebookProvider extends AbstractLoginProvider implements LoginProvider {

	@Override
	public AuthData signup(Map<String, String[]> data) throws InvalidDataException {
		String fbcode = getValue(data, "code");
		long fbid;
		if(fbcode == null) {
			String token = getValue(data, "access_token");
			fbid = getFacebookIdByToken(token);
		} else {
			fbid = getFacebookIdByCode(fbcode);
		}
		Facebook fbObj = getByFBId(fbid);
		if (fbObj != null && fbObj.getUserId() != null)
			throw new InvalidDataException("Already Signed Up");
		if (fbObj == null)
			fbObj = new Facebook();
		fbObj.setUserId(Utils.generateId());
		fbObj.setFbid(fbid);
		User user = new User();
		user.setId(fbObj.getUserId());
		Global.getMorphiaObject().getDatastore().save(user);
		Global.getMorphiaObject().getDatastore().save(fbObj);
		return token(user);
	}

	private Facebook getByFBId(long fbid) {
		final Query<Facebook> query = Global.getMorphiaObject().getDatastore().createQuery(Facebook.class)
				.filter("_id", fbid).limit(2);
		List<Facebook> asList = query.asList();
		if (asList.size() == 1)
			return asList.get(0);
		return null;
	}

	private long getFacebookIdByCode(String fbcode) throws InvalidDataException {
		String redirectUrl = URLEncoder.encode("http://halageri.com/signup/facebook");
		String code = URLEncoder.encode(fbcode);
		String encoded = "https://graph.facebook.com/v2.3/oauth/access_token?client_id="
				+ Play.application().configuration().getString("fb.client.id") + "&redirect_uri=" + redirectUrl
				+ "&client_secret=" + Play.application().configuration().getString("fb.client.secret") + "&code="
				+ code;
		HttpResponse<JsonNode> asJson;
		try {
			asJson = Unirest.get(encoded).asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
			throw new InvalidDataException(e);
		}
		JsonNode body = asJson.getBody();
		String token = body.getObject().optString("access_token");
		return getFacebookIdByToken(token);
	}

	private long getFacebookIdByToken(String token) throws InvalidDataException {
		if (token == null) {
			throw new InvalidDataException("Invalid Access Token " + token);
		}
		String url = "https://graph.facebook.com/me?fields=id&access_token=" + URLEncoder.encode(token)
				+ "&access_token=" + token;
		try {
			JSONObject json = Unirest.get(url).asJson().getBody().getObject();
			System.out.println(json.toString());
			boolean hasId = json.has("id");
			if (!hasId) {
				throw new InvalidDataException("Invalid Access Key " + token);
			}
			return json.getLong("id");
		} catch (UnirestException e) {
			throw new InvalidDataException(e);
		}
	}

	@Override
	public long link(String userid, Map<String, String[]> data) throws InvalidDataException {
		User user = getUserById(Long.parseLong(userid));
		if (user == null) {
			throw new InvalidDataException("Invalid User");
		}
		String fbcode = getValue(data, "code");
		long fbid;
		if(fbcode == null) {
			String token = getValue(data, "access_token");
			fbid = getFacebookIdByToken(token);
		} else {
			fbid = getFacebookIdByCode(fbcode);
		}
		Facebook fbObj = getByFBId(fbid);
		if (fbObj == null)
			fbObj = new Facebook();
		fbObj.setUserId(user.getId());
		fbObj.setFbid(fbid);
		Global.getMorphiaObject().getDatastore().save(fbObj);
		return user.getId();
	}

	@Override
	public long unlink(String userid, Map<String, String[]> data) throws InvalidDataException {
		User user = getUserById(Long.parseLong(userid));
		if (user == null) {
			throw new InvalidDataException("Invalid User");
		}
		String fbcode = getValue(data, "code");
		long fbid;
		if(fbcode == null) {
			String token = getValue(data, "access_token");
			fbid = getFacebookIdByToken(token);
		} else {
			fbid = getFacebookIdByCode(fbcode);
		}
		Facebook fbObj = getByFBId(fbid);
		if (fbObj == null)
			throw new InvalidDataException("FB Account wasn't linked");
		if (fbObj.getUserId() != Long.parseLong(userid)) {
			throw new InvalidDataException("Invalid User");
		}
		fbObj.setUserId(null);
		Global.getMorphiaObject().getDatastore().save(fbObj);
		return user.getId();
	}

	@Override
	public AuthData signin(Map<String, String[]> data) throws InvalidDataException {
		String fbcode = getValue(data, "code");
		long fbid;
		if(fbcode == null) {
			String token = getValue(data, "access_token");
			fbid = getFacebookIdByToken(token);
		} else {
			fbid = getFacebookIdByCode(fbcode);
		}
		Facebook fbObj = getByFBId(fbid);
		if (fbObj == null)
			throw new InvalidDataException("Facebook Account not signed up");
		User user = getUserById(fbObj.getUserId());
		if (user != null) {
			return token(user);
		}
		return new AuthData();
	}
}
