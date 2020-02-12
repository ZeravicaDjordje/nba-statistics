package com.mozzartbet.gameservice.exception;

public class ScoreException extends Exception {

	private static final long serialVersionUID = -3371648782872073948L;

	private String message;
	
	public ScoreException(String message) {
		this.message = message;
				
	}
}
