package com.olp.user_mgmt.exception;

public class DAOException extends Exception {
	 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message) {
		super(message);
	}

}
