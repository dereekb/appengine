package com.dereekb.gae.server.auth.security.model.query.task.impl.field;

import java.util.Set;

import com.dereekb.gae.server.auth.security.model.query.task.impl.AbstractSecurityModelQueryTaskOverrideDelegate;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.datastore.models.keys.exception.NoModelKeyException;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.query.builder.parameters.QueryFieldParameter;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * {@link AbstractSecurityModelQueryTaskOverrideDelegate} for a specific field
 * and field type.
 *
 * @author dereekb
 *
 * @param <F>
 *            field type
 * @param <D>
 *            details type
 * @param <Q>
 *            query type
 */
public abstract class AbstractModelQueryFieldTaskOverrideDelegateImpl<F extends QueryFieldParameter<?>, D extends LoginTokenUserDetails<?>, Q>
        implements AbstractSecurityModelQueryTaskOverrideDelegate<D, Q> {

	public static final String REQUIRED_FIELD_CODE = "REQUIRED_FIELD";
	public static final String ILLEGAL_OPERATOR_CODE = "ILLEGAL_OPERATOR";

	private static boolean DEFAULT_REQUIRED = false;
	private static boolean DEFAULT_TRY_DEFAULT = true;

	private final String field;
	private boolean required;
	private boolean tryDefault;
	private Set<ExpressionOperator> allowedOps;

	public AbstractModelQueryFieldTaskOverrideDelegateImpl(String field) {
		this(field, DEFAULT_REQUIRED);
	}

	public AbstractModelQueryFieldTaskOverrideDelegateImpl(String field, boolean required) {
		this(field, required, DEFAULT_TRY_DEFAULT);
	}

	public AbstractModelQueryFieldTaskOverrideDelegateImpl(String field, boolean required, boolean tryDefault) {
		this.field = field;
		this.setRequired(required);
		this.setTryDefault(tryDefault);
	}

	public String getField() {
		return this.field;
	}

	public boolean isRequired() {
		return this.required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isTryDefault() {
		return this.tryDefault;
	}

	public void setTryDefault(boolean tryDefault) {
		this.tryDefault = tryDefault;
	}

	public boolean getOptional() {
		return !this.required && !this.tryDefault;
	}

	public void setOptional(boolean optional) {
		this.setRequired(!optional);
		this.setTryDefault(!optional);
	}

	public Set<ExpressionOperator> getAllowedOps() {
		return this.allowedOps;
	}

	public void setAllowedOps(Set<ExpressionOperator> allowedOps) {
		this.allowedOps = allowedOps;
	}

	// MARK: AbstractSecurityModelQueryTaskOverrideDelegate
	@Override
	public void updateQueryForUser(Q query,
	                               D details)
	        throws InvalidAttributeException,
	            NoModelKeyException {

		F field = this.getFieldFromQuery(query);

		if (field == null && this.tryDefault) {
			this.setDefaultFieldValue(query, details);
			field = this.getFieldFromQuery(query);

			if (this.required) {
				this.assertRequiredCheck(query);
			}
		}

		if (field != null) {
			this.assertHasAllowedOperator(field, query, details);
			this.assertHasAccessToFieldValue(field, query, details);
		}
	}

	/**
	 * Reads the field from the query.
	 *
	 * @param query
	 *            Query. Never {@code null}.
	 * @return Field, or {@code null} if not set.
	 */
	protected abstract F getFieldFromQuery(Q query);

	/**
	 * Sets the default field value, or throws an exception.
	 *
	 * @param query
	 *            Query. Never {@code null}.
	 * @param details
	 *            Details. Never {@code null}.
	 * @throws InvalidAttributeException
	 */
	protected void setDefaultFieldValue(Q query,
	                                    D details)
	        throws InvalidAttributeException {
		// Do nothing by default.
	}

	protected void assertRequiredCheck(Q query) {
		F field = this.getFieldFromQuery(query);
		this.assertRequiredCheck(field, query);
	}

	protected void assertRequiredCheck(F field,
	                                   Q query) {
		if (field == null) {
			throw this.makeIsRequiredException();
		}
	}

	protected void throwIsRequiredException() throws InvalidAttributeException {
		throw this.makeIsRequiredException();
	}

	protected InvalidAttributeException makeIsRequiredException() throws InvalidAttributeException {
		return new InvalidAttributeException(this.field, null, "This field is required.", REQUIRED_FIELD_CODE);
	}

	private void assertHasAllowedOperator(F field,
	                                    Q query,
	                                    D details) {
		if (this.allowedOps != null) {
			ExpressionOperator operator = field.getOperator();

			if (!this.allowedOps.contains(operator)) {
				this.throwIllegalOperatorException();
			}
		}
	}

	protected void throwIllegalOperatorException() throws InvalidAttributeException {
		throw new InvalidAttributeException(this.field, null, "Illegal operator for this field.",
		        ILLEGAL_OPERATOR_CODE);
	}

	/**
	 * Asserts that the security context has access to the field.
	 *
	 * @param field
	 *            Field. Never {@code null}.
	 * @param query
	 *            Query. Never {@code null}.
	 * @param details
	 *            Details. Never {@code null}.
	 * @throws InvalidAttributeException
	 */
	protected abstract void assertHasAccessToFieldValue(F field,
	                                                    Q query,
	                                                    D details)
	        throws InvalidAttributeException;

	@Override
	public String toString() {
		return "AbstractModelQueryFieldTaskOverrideDelegateImpl [field=" + this.field + ", required=" + this.required
		        + ", tryDefault=" + this.tryDefault + "]";
	}

}
