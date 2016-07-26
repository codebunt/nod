package com.codecraft.login.exception;

public class InvalidDataException extends Exception {

	public InvalidDataException(Exception e) {
		super(e);
	}

	public InvalidDataException(String msg) {
		super(msg);
	}

	public InvalidDataException() {
		// TODO Auto-generated constructor stub
	}

}
