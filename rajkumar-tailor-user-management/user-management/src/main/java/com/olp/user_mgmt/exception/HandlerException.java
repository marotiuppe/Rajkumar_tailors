package com.olp.user_mgmt.exception;

public class HandlerException extends Exception{

	private static final long serialVersionUID = -2210096508895209008L;

	 private int errorCode;
	    
	    public HandlerException(String message) {
	    	super(message);
	    }
	    
	    public HandlerException(String message, Throwable e){
	    	super(message,e);
	    }
	    public HandlerException(int code, Throwable e){
	    	super(e);
	    	this.errorCode=code;
	    }
	    
	    public HandlerException(Throwable e) {
	        super(e);
	    }
	    
	    public HandlerException(int errorCode,String message) {
	    	super(message);
	    	this.errorCode = errorCode;
	    }
	  
	   public int getErrorCode() {
	        return this.errorCode;
	   }
}
