package com.mozzartbet.gameservice.exception;

import com.mozzartbet.gameservice.exception.PlayerException.PlayerExceptionCode;

public class TeamException extends ApplicationException {

	private static final long serialVersionUID = -2931270195769637191L;

	public TeamException(TeamExceptionCode code, String pattern, Object...args) {
		super(code, pattern, args);
		// TODO Auto-generated constructor stub
	}

	public enum TeamExceptionCode implements ApplicationExceptionCode {
		DUPLICATED_NAME,
		TEAM_DOESNT_EXIST,
		INVALID_ID
	}

}
