package com.dereekb.gae.server.auth.security.model.query.task.impl.field;

import java.util.Set;

import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeySetQueryFieldParameterBuilder.ModelKeySetQueryFieldParameter;

/**
 * Typed {@link AbstractModelQueryFieldTaskOverrideDelegateImpl} for
 * {@link ModelKeySetQueryFieldParameter}.
 * <p>
 * By default only allows a few operators.
 *
 * @author dereekb
 *
 * @param <D>
 *            details type
 * @param <Q>
 *            query type
 */
public abstract class AbstractModelKeySetFieldTaskOverrideDelegate<D extends LoginTokenUserDetails<?>, Q> extends AbstractModelQueryFieldTaskOverrideDelegateImpl<ModelKeySetQueryFieldParameter, D, Q> {

	private static final Set<ExpressionOperator> DEFAULT_ALLOWED_OPERATORS = SetUtility.makeSet(ExpressionOperator.IN,
	        ExpressionOperator.EQUAL);

	public AbstractModelKeySetFieldTaskOverrideDelegate(String field) {
		super(field);
		this.setAllowedOps(DEFAULT_ALLOWED_OPERATORS);
	}

	public AbstractModelKeySetFieldTaskOverrideDelegate(String field, boolean required) {
		super(field, required);
		this.setAllowedOps(DEFAULT_ALLOWED_OPERATORS);
	}

}
