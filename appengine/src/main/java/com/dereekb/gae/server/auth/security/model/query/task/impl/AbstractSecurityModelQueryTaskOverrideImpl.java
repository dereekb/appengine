package com.dereekb.gae.server.auth.security.model.query.task.impl;

import java.util.List;

import com.dereekb.gae.server.auth.security.model.query.task.AbstractSecurityModelQueryTaskOverride;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.datastore.models.keys.exception.NoModelKeyException;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * {@link AbstractSecurityModelQueryTaskOverride} used for overriding and
 * modifying query requests using
 * {@link AbstractSecurityModelQueryTaskOverrideDelegate} values.
 *
 * @author dereekb
 *
 * @param <Q>
 *            query type
 */
public abstract class AbstractSecurityModelQueryTaskOverrideImpl<D extends LoginTokenUserDetails<?>, Q> extends AbstractSecurityModelQueryTaskOverride<D, Q> {

	private List<AbstractSecurityModelQueryTaskOverrideDelegate<D, Q>> delegates;

	public AbstractSecurityModelQueryTaskOverrideImpl(AbstractSecurityModelQueryTaskOverrideDelegate<D, Q> delegate) {
		this.setDelegates(ListUtility.wrap(delegate));
	}

	public AbstractSecurityModelQueryTaskOverrideImpl(
	        List<AbstractSecurityModelQueryTaskOverrideDelegate<D, Q>> delegates) {
		this.setDelegates(delegates);
	}

	public List<AbstractSecurityModelQueryTaskOverrideDelegate<D, Q>> getDelegates() {
		return this.delegates;
	}

	public void setDelegates(List<AbstractSecurityModelQueryTaskOverrideDelegate<D, Q>> delegates) {
		if (delegates == null) {
			throw new IllegalArgumentException("delegates cannot be null.");
		}

		this.delegates = delegates;
	}

	// MARK: AbstractSosSecurityModelQueryTaskOverride
	@Override
	protected void tryUpdateQueryForUser(Q input,
	                                     D details)
	        throws InvalidAttributeException,
	            NoModelKeyException {
		for (AbstractSecurityModelQueryTaskOverrideDelegate<D, Q> delegate : this.delegates) {
			delegate.updateQueryForUser(input, details);
		}
	}

	@Override
	public String toString() {
		return "AbstractSecurityModelQueryTaskOverrideImpl [delegates=" + this.delegates + "]";
	}

}
