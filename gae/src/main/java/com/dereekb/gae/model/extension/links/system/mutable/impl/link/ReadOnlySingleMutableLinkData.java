package com.dereekb.gae.model.extension.links.system.mutable.impl.link;

import java.util.Set;

import com.dereekb.gae.model.extension.links.system.components.LinkSize;
import com.dereekb.gae.model.extension.links.system.components.LinkType;
import com.dereekb.gae.model.extension.links.system.components.SimpleLinkInfo;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkAccessor;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.exception.IllegalLinkChangeException;
import com.dereekb.gae.model.extension.links.system.mutable.exception.MutableLinkChangeException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.list.SetUtility;

/**
 * Read-only {@link MutableLinkData} implementation for a model with a single link.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ReadOnlySingleMutableLinkData<T> extends AbstractMutableLinkData<T> {

	private ReadOnlySingleMutableLinkDataDelegate<T> delegate;

	public ReadOnlySingleMutableLinkData(SimpleLinkInfo linkInfo, ReadOnlySingleMutableLinkDataDelegate<T> delegate) {
		super(linkInfo);
		this.delegate = delegate;
	}

	public ReadOnlySingleMutableLinkData(String linkName, String relationLinkType, ReadOnlySingleMutableLinkDataDelegate<T> delegate) {
		super(linkName, relationLinkType);
		this.delegate = delegate;
	}
	
	protected ReadOnlySingleMutableLinkDataDelegate<T> getDelegate() {
		return this.delegate;
	}

	protected void setDelegate(ReadOnlySingleMutableLinkDataDelegate<T> delegate) {
		this.delegate = delegate;
	}

	// MARK: MutableLinkData
	@Override
	public LinkType getLinkType() {
		return LinkType.STATIC;
	}

	@Override
	public LinkSize getLinkSize() {
		return LinkSize.ONE;
	}

	@Override
	public MutableLinkAccessor makeLinkAccessor(T model) {
		return new ReadOnlyAccessor(model);
	}

	protected class ReadOnlyAccessor extends AbstractLinkMutableLinkAccessor {

		public ReadOnlyAccessor(T model) {
			super(model);
		}
		
		// MARK: AbstractLinkMutableLinkAccessor
		@Override
		public Set<ModelKey> getLinkedModelKeys() {
			ModelKey key = ReadOnlySingleMutableLinkData.this.delegate.readLinkedModelKey(this.model);
			return SetUtility.wrap(key);
		}

		@Override
		protected void applyLinkChange(MutableLinkChange change) throws MutableLinkChangeException {
			throw new IllegalLinkChangeException(change, "This link is immutable and cannot be changed.");
		}
		
	}
	
}
