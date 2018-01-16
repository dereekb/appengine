package com.dereekb.gae.server.auth.security.model.query.task.impl.field;

import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;

/**
 * Typed {@link AbstractModelQueryFieldTaskOverrideDelegateImpl} for
 * {@link ModelKeyQueryFieldParameter}.
 *
 * @author dereekb
 *
 * @param <D>
 *            details type
 * @param <Q>
 *            query type
 */
public abstract class AbstractModelKeyFieldTaskOverrideDelegate<D extends LoginTokenUserDetails<?>, Q> extends AbstractModelQueryFieldTaskOverrideDelegateImpl<ModelKeyQueryFieldParameter, D, Q> {

	public AbstractModelKeyFieldTaskOverrideDelegate(String field) {
		super(field);
	}

}
