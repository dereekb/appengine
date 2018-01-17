package com.dereekb.gae.server.auth.security.model.query.task.impl;

import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * Security query utility.
 *
 * @author dereekb
 *
 */
public class SecurityModelQueryUtility {

	public static final String CANNOT_SEARCH_ALL_CODE = "CANNOT_SEARCH_ALL";

	public static void throwCannotQueryAll() throws InvalidAttributeException {
		throw new InvalidAttributeException("query", null, "You cannot query everything. Narrow your query.",
		        CANNOT_SEARCH_ALL_CODE);
	}

}
