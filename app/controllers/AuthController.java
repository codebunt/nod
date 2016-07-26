package controllers;

import java.util.Map;

import com.codecraft.login.ProviderFactory;
import com.codecraft.login.dto.AuthData;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class AuthController extends Controller {
	public Result signup(String provider) throws Exception {
		try {
			long signup = ProviderFactory.get(provider).signup(getRequestData());
			return ok("" + signup);
		} catch (Exception e) {
			e.printStackTrace();
			return unauthorized();
		}
	}
	
	public Result link(String provider, String user) throws Exception {
		try {
			long signup = ProviderFactory.get(provider).link(user,getRequestData());
			return ok("" + signup);
		} catch (Exception e) {
			return unauthorized();
		}
	}
	
	public Result unlink(String provider, String user) throws Exception {
		try {
			long signup = ProviderFactory.get(provider).unlink(user,getRequestData());
			return ok("" + signup);
		} catch (Exception e) {
			return unauthorized();
		}
	}
	
	public Result signin(String provider) throws Exception {
		try {
			AuthData authdata = ProviderFactory.get(provider).signin(getRequestData());
			return ok(Json.toJson(authdata));
		} catch (Exception e) {
			return unauthorized();
		}
	}

	private Map<String, String[]> getRequestData() {
		if (request().method().equalsIgnoreCase("post") || request().method().equalsIgnoreCase("put")) {
			return request().body().asFormUrlEncoded();
		} else {
			return request().queryString();
		}
	}
}
