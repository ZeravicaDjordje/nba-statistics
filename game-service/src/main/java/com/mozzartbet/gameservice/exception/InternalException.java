package com.mozzartbet.gameservice.exception;

public class InternalException extends RuntimeException {

	private static final long serialVersionUID = -4255058996348530964L;

	public InternalException(String pattern, Object...args) {
		super(String.format(pattern, args), extractCause(args));
	}
	
	static Throwable extractCause(Object...args) {
		if(args == null || args.length == 0) {
			return null;
		}
		if (args[args.length - 1] instanceof Throwable) {
			return (Throwable) args[args.length - 1];
		}
		return null;
	}
}
