package com.dereekb.gae.server.auth.security.model.query.task.impl.field;

import java.util.Set;

import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.dereekb.gae.utilities.query.ExpressionOperator;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;

/**
 * Typed {@link AbstractModelQueryFieldTaskOverrideDelegateImpl} for
 * {@link ModelKeyQueryFieldParameter}.
 * <p>
 * By default only allows an equality operator.
 *
 * @author dereekb
 *
 * @param <D>
 *            details type
 * @param <Q>
 *            query type
 */
public abstract class AbstractModelKeyFieldTaskOverrideDelegate<D extends LoginTokenUserDetails<?>, Q> extends AbstractModelQueryFieldTaskOverrideDelegateImpl<ModelKeyQueryFieldParameter, D, Q> {

	private static final Set<ExpressionOperator> DEFAULT_ALLOWED_OPERATORS = SetUtility
	        .makeSet(ExpressionOperator.EQUAL);

	public AbstractModelKeyFieldTaskOverrideDelegate(String field) {
		super(field);
		this.setAllowedOps(DEFAULT_ALLOWED_OPERATORS);
	}

	public AbstractModelKeyFieldTaskOverrideDelegate(String field, boolean required) {
		super(field, required);
		this.setAllowedOps(DEFAULT_ALLOWED_OPERATORS);
	}

}
