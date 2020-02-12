package com.mozzartbet.gameservice.exception;

public class TimeOutConnectionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	
	public TimeOutConnectionException(String url) {
		this.url = url;
	}
	
	public String toString() {
		return "This URL can not be reached: " + this.url;
	}
}
