package com.dereekb.gae.server.auth.model.key.search.query.security;

import com.dereekb.gae.server.auth.model.key.search.query.LoginKeyQuery;
import com.dereekb.gae.server.auth.security.model.query.task.impl.AbstractSecurityModelQueryTaskOverrideDelegate;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.datastore.models.keys.exception.NoModelKeyException;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * Delegate for {@link LoginKeyQuery}. Ensures that either Student, Tutor, or
 * Login fields are set.
 *
 * @author dereekb
 *
 */
public class LoginKeyQuerySecurityDelegate
        implements AbstractSecurityModelQueryTaskOverrideDelegate<LoginTokenUserDetails<?>, LoginKeyQuery> {

	// MARK: AbstractSecurityModelQueryTaskOverrideDelegate
	@Override
	public void updateQueryForUser(LoginKeyQuery query,
	                               LoginTokenUserDetails<?> details)
	        throws InvalidAttributeException,
	            NoModelKeyException {

		ModelKeyQueryFieldParameter login = query.getLogin();
		ModelKeyQueryFieldParameter pointer = query.getLoginPointer();

		if (pointer == null && login == null) {
			throw new InvalidAttributeException("query", null,
			        "This query requires either the login or pointer fields to be provided.");
		}
	}

}
