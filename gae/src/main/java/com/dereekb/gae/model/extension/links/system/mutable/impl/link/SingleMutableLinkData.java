package com.dereekb.gae.model.extension.links.system.mutable.impl.link;

import java.util.Set;

import com.dereekb.gae.model.extension.links.system.components.SimpleLinkInfo;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkAccessor;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.exception.IllegalLinkChangeException;
import com.dereekb.gae.model.extension.links.system.mutable.exception.MutableLinkChangeException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link MutableLinkData} implementation for a model with a single link.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class SingleMutableLinkData<T> extends ReadOnlySingleMutableLinkData<T> {

	private SingleMutableLinkDataDelegate<T> delegate;

	public SingleMutableLinkData(SimpleLinkInfo linkInfo, SingleMutableLinkDataDelegate<T> delegate) {
		super(linkInfo, delegate);
		this.delegate = delegate;
	}

	public SingleMutableLinkData(String linkName, String relationLinkType, SingleMutableLinkDataDelegate<T> delegate) {
		super(linkName, relationLinkType, delegate);
		this.delegate = delegate;
	}
	
	@Override
	protected SingleMutableLinkDataDelegate<T> getDelegate() {
		return this.delegate;
	}

	protected void setDelegate(SingleMutableLinkDataDelegate<T> delegate) {
		this.delegate = delegate;
	}

	// MARK: MutableLinkData
	@Override
	public MutableLinkAccessor makeLinkAccessor(T model) {
		return new Accessor(model);
	}

	protected class Accessor extends ReadOnlyAccessor {

		public Accessor(T model) {
			super(model);
		}
		
		// MARK: AbstractLinkMutableLinkAccessor
		@Override
		protected void applyLinkChange(MutableLinkChange change) throws MutableLinkChangeException {
			
			switch (change.getLinkChangeType()) {
				case ADD:
				case REMOVE:
					// Cannot perform Add or Remove on a single link..?
					// Issue is with the reverse, this becomes bad...
					throw new IllegalLinkChangeException(change);
				case SET:
					Set<ModelKey> modelKeys = change.getKeys();
					
					if (modelKeys.isEmpty() == false) {
						ModelKey key = modelKeys.iterator().next();
						this.setLinkedModelKeyForChange(change, key);
						break;
					}
				case CLEAR:
					this.setLinkedModelKeyForChange(change, null);
					break;
				case NONE: 
					return;	// Do nothing.
				default:
					throw new UnsupportedOperationException();
			}
		}
		
		protected void setLinkedModelKeyForChange(MutableLinkChange change, ModelKey key) {
			SingleMutableLinkData.this.delegate.setLinkedModelKey(this.model, key);
		}

	}

	@Override
	public String toString() {
		return "SingleMutableLinkData [delegate=" + this.delegate + "]";
	}

}
