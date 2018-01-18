package com.dereekb.gae.server.auth.security.model.query.task.impl.field;

import com.dereekb.gae.server.auth.security.model.query.task.impl.AbstractSecurityModelQueryTaskOverrideDelegate;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.datastore.models.keys.exception.NoModelKeyException;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * {@link AbstractSecurityModelQueryTaskOverrideDelegate} implementation that
 * wraps another delegate and catches any {@link InvalidAttributeException} that
 * is thrown.
 *
 * @author dereekb
 *
 * @param <D>
 *            details type
 * @param <Q>
 *            query type
 */
public class SecurityModelQueryTaskOverrideDelegateAttributeRecovery<D extends LoginTokenUserDetails<?>, Q>
        implements AbstractSecurityModelQueryTaskOverrideDelegate<D, Q> {

	private AbstractSecurityModelQueryTaskOverrideDelegate<D, Q> delegate;

	public SecurityModelQueryTaskOverrideDelegateAttributeRecovery(
	        AbstractSecurityModelQueryTaskOverrideDelegate<D, Q> delegate) {
		this.setDelegate(delegate);
	}

	public AbstractSecurityModelQueryTaskOverrideDelegate<D, Q> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(AbstractSecurityModelQueryTaskOverrideDelegate<D, Q> delegate) {
		if (delegate == null) {
			throw new IllegalArgumentException("delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	// MARK: AbstractSecurityModelQueryTaskOverrideDelegate
	@Override
	public void updateQueryForUser(Q query,
	                               D details)
	        throws InvalidAttributeException,
	            NoModelKeyException {
		try {
			this.delegate.updateQueryForUser(query, details);
		} catch (InvalidAttributeException e) {
			if (!this.recover(query, details, e)) {
				throw e;
			}
		}
	}

	// MARK: Recover
	protected boolean recover(Q query,
	                          D details,
	                          InvalidAttributeException e)
	        throws InvalidAttributeException {
		// Do nothing.
		return true;
	}

	@Override
	public String toString() {
		return "SecurityModelQueryTaskOverrideDelegateAttributeRecovery [delegate=" + this.delegate + "]";
	}

}
