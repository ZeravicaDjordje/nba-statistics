package com.mozzartbet.gameservice.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mozzartbet.gameservice.exception.NotValidHtmlException;

public class ValidateHtml {

	private static final Pattern RE_PATTERN = Pattern.compile("<[^>]*>");
	
	public static void validateHtml(String html) throws NotValidHtmlException {
		Matcher matcher = RE_PATTERN.matcher(html);
		if(!matcher.find()) {
			throw new NotValidHtmlException(html);
		}
	}
}
