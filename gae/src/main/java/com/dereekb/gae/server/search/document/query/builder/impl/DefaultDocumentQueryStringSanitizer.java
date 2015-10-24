package com.dereekb.gae.server.search.document.query.builder.impl;

import com.dereekb.gae.server.search.document.query.DocumentQueryStringSanitizer;
import com.dereekb.gae.utilities.regex.RegexHelper;

/**
 * Default sanitizer.
 * 
 * Completely removes all values after NOT or OR if allowNot/Or is set to false.
 * Removes functions/snippets and following whitespace
 * Removes expression objects only.
 * 
 * @author dereekb
 * 
 */
public class DefaultDocumentQueryStringSanitizer
        implements DocumentQueryStringSanitizer {

	// Sanitization
	private boolean santizeString = true;
	private boolean trim = true;
	private String allowedRegex = RegexHelper.EXCEPT_ALPHANUMERIC_AND_SYMBOLS;

	// Normal Operations
	private boolean allowAnd = true;

	// Complex Operations
	private boolean allowOr = false;
	private boolean allowNot = false;
	private boolean allowFunctions = false;
	private boolean allowSnippets = false;

	private String regexForFloatingConjunction(String conjunction) {
		return String.format("(%s)(\\s)*(?=([A-Z]{2,})|$)", conjunction);
	}

	@Override
	public String sanitizeQuery(String query) {
		String sanitized = query;

		if (this.santizeString && allowedRegex != null) {
			sanitized = sanitized.replaceAll(allowedRegex, "");
		}

		if (this.allowFunctions == false) {
			String regex = RegexHelper.WHITESPACE_BEFORE_AND_AFTER_ANY_FUNCTION;
			sanitized = sanitized.replaceAll(regex, "");
			sanitized = sanitized.replaceAll(",", ""); // Remove any leftover commas
		} else {
			if (this.allowSnippets == false) {
				String regex = RegexHelper.catchFunction("snippet");
				sanitized = sanitized.replaceAll(regex, "");
			}
		}

		if (this.allowAnd == false) {
			String regex = RegexHelper.catchWhitespaceBeforeAndAllAfter("AND");
			sanitized = sanitized.replaceAll(regex, "");
		} else {
			String regex = this.regexForFloatingConjunction("AND");
			sanitized = sanitized.replaceAll(regex, "");
		}

		if (this.allowOr == false) {
			String regex = RegexHelper.catchWhitespaceBeforeAndAllAfter("OR");
			sanitized = sanitized.replaceAll(regex, "");
		} else {
			String regex = this.regexForFloatingConjunction("OR");
			sanitized = sanitized.replaceAll(regex, "");
		}

		if (this.allowNot == false) {
			String regex = RegexHelper.catchWhitespaceBeforeAndAllAfter("NOT");
			sanitized = sanitized.replaceAll(regex, "");
		} else {
			String regex = this.regexForFloatingConjunction("NOT");
			sanitized = sanitized.replaceAll(regex, "");
		}

		if (trim) {
			sanitized = sanitized.trim();
		}

		return sanitized;
	}

	public boolean isAllowAnd() {
		return allowAnd;
	}

	public void setAllowAnd(boolean allowAnd) {
		this.allowAnd = allowAnd;
	}

	public boolean isAllowOr() {
		return allowOr;
	}

	public void setAllowOr(boolean allowOr) {
		this.allowOr = allowOr;
	}

	public boolean isAllowNot() {
		return allowNot;
	}

	public void setAllowNot(boolean allowNot) {
		this.allowNot = allowNot;
	}

	public boolean isAllowFunctions() {
		return allowFunctions;
	}

	public void setAllowFunctions(boolean allowFunctions) {
		this.allowFunctions = allowFunctions;
	}

	public void setAllowComplex(boolean allow) {
		this.setAllowOr(allow);
		this.setAllowNot(allow);
		this.setAllowFunctions(allow);
		this.setAllowSnippets(allow);
	}

	public boolean isAllowSnippets() {
		return allowSnippets;
	}

	public void setAllowSnippets(boolean allowSnippets) {
		this.allowSnippets = allowSnippets;
	}

	public boolean isSantizeString() {
		return santizeString;
	}

	public void setSantizeString(boolean santizeString) {
		this.santizeString = santizeString;
	}

	public String getAllowedRegex() {
		return allowedRegex;
	}

	public void setAllowedRegex(String allowedRegex) {
		this.allowedRegex = allowedRegex;
	}

}
