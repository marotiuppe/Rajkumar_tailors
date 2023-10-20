package com.olp.user_mgmt.exception;

public class AppMenuException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppMenuException(String message) {
        super(message);
    }

	public AppMenuException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public AppMenuException(Throwable cause) {
        super(cause);
    }

}