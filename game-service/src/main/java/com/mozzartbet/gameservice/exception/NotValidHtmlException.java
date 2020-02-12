package com.mozzartbet.gameservice.exception;

public class NotValidHtmlException extends Exception {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private String html;
	
	public NotValidHtmlException(String html) {
		this.html = html;
	}
	
	public String toString() {
		return "Not Valid HTML " + html;
	}
}
