package com.dereekb.gae.utilities.regex;

import java.util.regex.Pattern;

/**
 * Light helper class that contains regex values;
 *
 * @author dereekb
 *
 */
public class RegexHelper {

	/**
	 * Eliminates all white space before the %s value, and anything after it.
	 */
	public static final String WHITE_SPACE = "\\s";
	public static final String ANY_DIGIT_OR_LETTER = "\\w|\\d";
	public static final String ANY_DIGIT_OR_SPACE_OR_LETTER = "(\\w|\\d|\\s)";
	public static final String ANYTHING = "(\\s|\\S)";
	public static final String WHITESPACE_BEFORE_ANY_AFTER_FORMAT = "(((\\s)*(%s)+" + ANYTHING + "*))";
	public static final String WHITESPACE_BEFORE_AND_AFTER_FUNCTION = "(\\s)*(%s)+(\\()+(\\d|\\w|\\s|,)*(\\))*(\\s)*";
	public static final String WHITESPACE_BEFORE_AND_AFTER_ANY_FUNCTION = String
	        .format(WHITESPACE_BEFORE_AND_AFTER_FUNCTION, ANY_DIGIT_OR_LETTER);
	public static final String OR_REGEX = "(%s)|(%s)";
	public static final String BACKSLASH = "\\";
	public static final String TWITTER_TEXT_USERNAME = "^(\\w{1,15})\\b$";	// 15char
	                                                                      	// Username
	                                                                      	// w/o
	                                                                      	// the
	                                                                      	// @
	public static final String EXCEPT_ALPHANUMERIC_AND_SYMBOLS = "[^(a-zA-Z0-9)(,\\.\"\')(\\s)(:!@#$%^&*)(\\-<>+=)$]";
	public static final String PUNCTUATION = "\\p{Punct}";

	public static final String catchWhitespaceBeforeAndAllAfter(String value) {
		String string = String.format(WHITESPACE_BEFORE_ANY_AFTER_FORMAT, value);
		return string;
	}

	public static final String catchFunction(String value) {
		String string = String.format(WHITESPACE_BEFORE_AND_AFTER_FUNCTION, value);
		return string;
	}

	public static final String orRegex(String a,
	                                   String b) {
		String string = String.format(OR_REGEX, a, b);
		return string;
	}

	public static final String removePunctuation(String input) {
		return replacePunctuation(input, "");
	}

	public static final String replacePunctuation(String input,
	                                              String replacement) {
		return input.replaceAll(PUNCTUATION, replacement);
	}

	public static final boolean containsPunctuation(String input) {
		return Pattern.matches(PUNCTUATION, input);
	}

}
