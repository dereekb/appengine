package com.dereekb.gae.server.auth.security.model.query.task.impl;

import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.datastore.models.keys.exception.NoModelKeyException;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * {@link AbstractSecurityModelQueryTaskOverrideImpl} delegate.
 *
 * @author dereekb
 *
 */
public interface AbstractSecurityModelQueryTaskOverrideDelegate<D extends LoginTokenUserDetails<?>, Q> {

	/**
	 * Updates the input query.
	 *
	 * @param input
	 *            query. Never {@code null}.
	 * @param details
	 *            details. Never {@code null}.
	 * @throws InvalidAttributeException
	 *             thrown if a query has an illegal configuration.
	 * @throws NoModelKeyException
	 *             thrown if a required security key is unavailable.
	 */
	public void updateQueryForUser(Q input,
	                               D details)
	        throws InvalidAttributeException,
	            NoModelKeyException;

}
