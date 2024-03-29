package com.dereekb.gae.server.auth.security.model.roles.parent.impl;

import com.dereekb.gae.model.exception.NoParentException;
import com.dereekb.gae.server.auth.security.model.roles.loader.AnonymousModelRoleSetContext;
import com.dereekb.gae.server.auth.security.model.roles.loader.AnonymousModelRoleSetContextService;
import com.dereekb.gae.server.auth.security.model.roles.parent.ParentModelRoleSetContextReader;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link ParentModelRoleSetContextReader} implementation.
 *
 * @author dereekb
 *
 * @deprecated Forms a circular dependency in most cases.
 *
 * @param <T>
 *            model type
 */
@Deprecated
public class ParentModelRoleSetContextReaderImpl<T extends UniqueModel>
        implements ParentModelRoleSetContextReader<T> {

	private ParentModelRoleSetContextReaderDelegate<T> delegate;
	private AnonymousModelRoleSetContextService contextService;

	public ParentModelRoleSetContextReaderImpl(ParentModelRoleSetContextReaderDelegate<T> delegate,
	        AnonymousModelRoleSetContextService contextService) {
		super();
		this.setDelegate(delegate);
		this.setContextService(contextService);
	}

	public ParentModelRoleSetContextReaderDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(ParentModelRoleSetContextReaderDelegate<T> delegate) {
		if (delegate == null) {
			throw new IllegalArgumentException("delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	public AnonymousModelRoleSetContextService getContextService() {
		return this.contextService;
	}

	public void setContextService(AnonymousModelRoleSetContextService contextService) {
		if (contextService == null) {
			throw new IllegalArgumentException("contextService cannot be null.");
		}

		this.contextService = contextService;
	}

	// MARK: ParentModelRoleSetContextReader
	@Override
	public AnonymousModelRoleSetContext getParentRoleSetContext(T child) throws NoParentException {
		String parentType = this.getDelegate().getParentType();
		ModelKey parentKey = this.delegate.getParentModelKey(child);

		if (parentKey == null) {
			throw new NoParentException(child.getModelKey());
		}

		return this.contextService.getterForType(parentType).getAnonymous(parentKey);
	}

	@Override
	public String toString() {
		return "ParentModelRoleSetContextReaderImpl [delegate=" + this.delegate + ", contextService="
		        + this.contextService + "]";
	}

}
