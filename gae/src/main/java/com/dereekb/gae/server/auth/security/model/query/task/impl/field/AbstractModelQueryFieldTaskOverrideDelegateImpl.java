package com.dereekb.gae.server.auth.security.model.query.task.impl.field;

import com.dereekb.gae.server.auth.security.model.query.task.impl.AbstractSecurityModelQueryTaskOverrideDelegate;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.datastore.models.keys.exception.NoModelKeyException;
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
public abstract class AbstractModelQueryFieldTaskOverrideDelegateImpl<F, D extends LoginTokenUserDetails<?>, Q>
        implements AbstractSecurityModelQueryTaskOverrideDelegate<D, Q> {

	public static final String REQUIRED_FIELD_CODE = "REQUIRED_FIELD";

	private final String field;

	public AbstractModelQueryFieldTaskOverrideDelegateImpl(String field) {
		this.field = field;
	}

	public String getField() {
		return this.field;
	}

	// MARK: AbstractSecurityModelQueryTaskOverrideDelegate
	@Override
	public void updateQueryForUser(Q query,
	                               D details)
	        throws InvalidAttributeException,
	            NoModelKeyException {

		F field = this.getFieldFromQuery(query);

		if (field != null) {
			this.assertHasAccessToFieldValue(field, query, details);
		} else {
			this.setDefaultFieldValue(query, details);
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
		throw new InvalidAttributeException(this.field, null, "This field is required.", REQUIRED_FIELD_CODE);
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

}
