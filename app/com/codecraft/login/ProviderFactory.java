package com.codecraft.login;

public class ProviderFactory {
	public static LoginProvider get(String provider) {
		if(provider.equals("facebook"))
			return new FacebookProvider();
		else if(provider.equals("local"))
			return new LocalProvider();
		else if(provider.equals("otp"))
			return new OTPProvider();
		return null;
	}
}
