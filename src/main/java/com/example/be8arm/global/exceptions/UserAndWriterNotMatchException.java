package com.example.be8arm.global.exceptions;

public class UserAndWriterNotMatchException extends RuntimeException {
	public UserAndWriterNotMatchException(String msg) {
		super(msg);
	}
}
