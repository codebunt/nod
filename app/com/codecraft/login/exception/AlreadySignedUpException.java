package com.codecraft.login.exception;

public class AlreadySignedUpException extends InvalidDataException {

	public AlreadySignedUpException(String msg) {
		super(msg);
	}

	public AlreadySignedUpException() {
		super();
	}

}
