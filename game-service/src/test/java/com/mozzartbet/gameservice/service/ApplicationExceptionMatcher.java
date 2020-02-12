package com.mozzartbet.gameservice.service;

import org.hamcrest.DiagnosingMatcher;
import static com.google.common.base.Objects.*;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;

import com.mozzartbet.gameservice.exception.ApplicationException;
import com.mozzartbet.gameservice.exception.ApplicationExceptionCode;

import lombok.RequiredArgsConstructor;

import com.mozzartbet.gameservice.exception.ApplicationException;

public class ApplicationExceptionMatcher<E extends ApplicationException> extends DiagnosingMatcher<E>  {
	
	final Class<E> exceptionClass;
	final ApplicationExceptionCode exceptionCode;

	final String expectedMessage;

	@Override
	public void describeTo(Description desc) {
		desc.appendText("exception: ").appendValue(exceptionClass).appendText(", code: ").appendValue(exceptionCode)
				.appendText(", message:").appendValue(expectedMessage);
	}

	@Override
	protected boolean matches(Object item, Description mismatch) {
		if (!exceptionClass.isInstance(item)) {
			mismatch.appendText("was: ").appendText(item.getClass().getName());
			return false;
		}

		ApplicationException actual = (ApplicationException) item;
		if (!equal(exceptionCode, actual.getCode())) {
			mismatch.appendText("code was: ").appendText(actual.getCode().name());
			return false;
		}

		if (expectedMessage != null && !equal(expectedMessage, actual.getMessage())) {
			mismatch.appendText("message was: ").appendText(actual.getMessage());
			return false;
		}

		return true;
	}

}


