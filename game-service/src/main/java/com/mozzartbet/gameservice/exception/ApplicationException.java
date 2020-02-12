package com.mozzartbet.gameservice.exception;

import static com.mozzartbet.gameservice.exception.InternalException.*;

import lombok.Data;

@Data

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 4932022866845981303L;

	final ApplicationExceptionCode code;
	
	public ApplicationException(ApplicationExceptionCode code, String pattern, Object...args) {
		super(String.format("[%s]", code) + String.format(pattern, args), extractCause(args));
		this.code = code;
	}
}
