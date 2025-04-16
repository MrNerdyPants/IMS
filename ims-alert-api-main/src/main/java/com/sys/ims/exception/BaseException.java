package com.sys.ims.exception;

public class BaseException extends Exception {
    public BaseException(String message) {
	super(message);
    }

    public static class EmailExistException extends RuntimeException {
	public EmailExistException(String message) {
	    super(message);
	}

	public EmailExistException(String message, Throwable throwable) {
	}
    }

    public static class NameExistException extends RuntimeException {
	public NameExistException(String message) {
	    super(message);
	}

	public NameExistException(String message, Throwable throwable) {
	}
    }

    public static class ContactExistException extends RuntimeException {
	public ContactExistException(String message) {
	    super(message);
	}

	public ContactExistException(String message, Throwable throwable) {

	}
    }
}
