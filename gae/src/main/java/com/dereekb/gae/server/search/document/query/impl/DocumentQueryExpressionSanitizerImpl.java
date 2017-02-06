package com.dereekb.gae.server.search.document.query.impl;

import com.dereekb.gae.server.search.document.query.DocumentQueryExpressionSanitizer;
import com.dereekb.gae.utilities.regex.RegexHelper;

/**
 * {@link DocumentQueryExpressionSanitizer} implementation.
 *
 * @author dereekb
 *
 */
public class DocumentQueryExpressionSanitizerImpl
        implements DocumentQueryExpressionSanitizer {

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
	public String sanitizeExpression(String query) {
		String sanitized = query;

		if (this.santizeString && this.allowedRegex != null) {
			sanitized = sanitized.replaceAll(this.allowedRegex, "");
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

		if (this.trim) {
			sanitized = sanitized.trim();
		}

		return sanitized;
	}

	public boolean isAllowAnd() {
		return this.allowAnd;
	}

	public void setAllowAnd(boolean allowAnd) {
		this.allowAnd = allowAnd;
	}

	public boolean isAllowOr() {
		return this.allowOr;
	}

	public void setAllowOr(boolean allowOr) {
		this.allowOr = allowOr;
	}

	public boolean isAllowNot() {
		return this.allowNot;
	}

	public void setAllowNot(boolean allowNot) {
		this.allowNot = allowNot;
	}

	public boolean isAllowFunctions() {
		return this.allowFunctions;
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
		return this.allowSnippets;
	}

	public void setAllowSnippets(boolean allowSnippets) {
		this.allowSnippets = allowSnippets;
	}

	public boolean isSantizeString() {
		return this.santizeString;
	}

	public void setSantizeString(boolean santizeString) {
		this.santizeString = santizeString;
	}

	public String getAllowedRegex() {
		return this.allowedRegex;
	}

	public void setAllowedRegex(String allowedRegex) {
		this.allowedRegex = allowedRegex;
	}

}
