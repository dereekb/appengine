package com.dereekb.gae.server.auth.security.model.query.task.impl.field;

import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeySetQueryFieldParameterBuilder.ModelKeySetQueryFieldParameter;

/**
 * Typed {@link AbstractModelQueryFieldTaskOverrideDelegateImpl} for
 * {@link ModelKeySetQueryFieldParameter}.
 *
 * @author dereekb
 *
 * @param <D>
 *            details type
 * @param <Q>
 *            query type
 */
public abstract class AbstractModelKeySetFieldTaskOverrideDelegate<D extends LoginTokenUserDetails<?>, Q> extends AbstractModelQueryFieldTaskOverrideDelegateImpl<ModelKeySetQueryFieldParameter, D, Q> {

	public AbstractModelKeySetFieldTaskOverrideDelegate(String field) {
		super(field);
	}

}
