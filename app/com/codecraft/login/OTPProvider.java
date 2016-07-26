package com.codecraft.login;

import java.util.Map;

import com.codecraft.login.dto.AuthData;
import com.codecraft.login.exception.InvalidDataException;

public class OTPProvider extends AbstractLoginProvider implements LoginProvider {

	@Override
	public AuthData signup(Map<String, String[]> data) throws InvalidDataException {
		throw new InvalidDataException("Not Implemented!");
	}

	@Override
	public AuthData signin(Map<String, String[]> data) throws InvalidDataException {
		throw new InvalidDataException("Not Implemented!");
	}

	@Override
	public long link(String userid, Map<String, String[]> data) throws InvalidDataException {
		throw new InvalidDataException("Not Implemented!");
	}

	@Override
	public long unlink(String userid, Map<String, String[]> data) throws InvalidDataException {
		throw new InvalidDataException("Not Implemented!");
	}

}
