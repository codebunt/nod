package com.codecraft.login;

import java.util.Map;

import com.codecraft.login.dto.AuthData;
import com.codecraft.login.exception.InvalidDataException;

public interface LoginProvider {
	public long signup(Map<String, String[]> data) throws InvalidDataException;
	
	public AuthData signin(Map<String, String[]> data) throws InvalidDataException;
	
	public long link(String userid , Map<String, String[]> data) throws InvalidDataException;
	
	public long unlink(String userid , Map<String, String[]> data) throws InvalidDataException;
}
