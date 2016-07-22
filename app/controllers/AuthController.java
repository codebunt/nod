package controllers;

import java.net.URLEncoder;

import javax.inject.Inject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import play.Play;
import play.libs.ws.WSClient;
import play.mvc.Controller;
import play.mvc.Result;

public class AuthController extends Controller {
	@Inject WSClient ws;
	@SuppressWarnings("deprecation")
	public Result signup(String provider) throws Exception {
        if(provider.equals("facebook")) {
        	String redirectUrl = URLEncoder.encode("https://www.facebook.com/dialog/oauth?client_id= 1731336217125828&redirect_uri=http://halageri.com:9000/signup/facebook");
        	String code = URLEncoder.encode(request().queryString().get("code")[0]);
        	String encoded = "https://graph.facebook.com/v2.3/oauth/access_token?client_id="+Play.application().configuration().getString("fb.client.id")
        			+"&redirect_uri="+redirectUrl
        			+"&client_secret="+Play.application().configuration().getString("fb.client.secret")
        			+"&code="+code;
        	HttpResponse<JsonNode> asJson = Unirest.get(encoded).asJson();
        	JsonNode body = asJson.getBody();
			System.out.println(body.toString());
    		return ok(request().queryString().get("code")[0]);
        }
        return ok();
    }
}
