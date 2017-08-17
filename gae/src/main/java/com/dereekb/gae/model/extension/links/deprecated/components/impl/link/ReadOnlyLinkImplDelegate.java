package com.dereekb.gae.model.extension.links.components.impl.link;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.links.deprecated.components.exception.IllegalRelationChangeException;
import com.dereekb.gae.model.extension.links.deprecated.components.exception.RelationChangeException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LinkImplDelegate} that wraps another and will throw exceptions when
 * {@link #clear()} and {@link #add(ModelKey)} are called.
 * 
 * @author dereekb
 *
 */
@Deprecated
public class ReadOnlyLinkImplDelegate
        implements LinkImplDelegate {

	private LinkImplDelegate delegate;

	public ReadOnlyLinkImplDelegate(LinkImplDelegate delegate) {
		this.setDelegate(delegate);
	}

	public static ReadOnlyLinkImplDelegate make(LinkImplDelegate delegate) {
		return new ReadOnlyLinkImplDelegate(delegate);
	}

	public LinkImplDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(LinkImplDelegate delegate) {
		if (delegate == null) {
			throw new IllegalArgumentException("delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	// MARK: LinkImplDelegate
	@Override
	public List<ModelKey> keys() {
		return this.delegate.keys();
	}

	@Override
	public boolean add(ModelKey key) throws RelationChangeException {
		throw new IllegalRelationChangeException(key);
	}

	@Override
	public boolean remove(ModelKey key) throws RelationChangeException {
		throw new IllegalRelationChangeException(key);
	}

	@Override
	public Set<ModelKey> clear() {
		throw new IllegalRelationChangeException();
	}

}
