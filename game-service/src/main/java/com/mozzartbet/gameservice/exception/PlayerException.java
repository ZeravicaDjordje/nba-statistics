package com.mozzartbet.gameservice.exception;

public class PlayerException extends ApplicationException {

	private static final long serialVersionUID = -6682493199610630428L;

	public PlayerException(PlayerExceptionCode code, String pattern, Object...args) {
		super(code, pattern, args);
		// TODO Auto-generated constructor stub
	}

	public enum PlayerExceptionCode implements ApplicationExceptionCode {
		DUPLICATED_NAME,
		PLYAER_NOT_IN_TEAM,
		INVALID_ID
	}
	
}
