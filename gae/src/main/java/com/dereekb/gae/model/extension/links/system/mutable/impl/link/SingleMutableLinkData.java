package com.dereekb.gae.model.extension.links.system.mutable.impl.link;

import java.util.Set;

import com.dereekb.gae.model.extension.links.system.components.LinkSize;
import com.dereekb.gae.model.extension.links.system.components.LinkType;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkAccessor;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeResult;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkData;
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
public class SingleMutableLinkData<T> extends AbstractMutableLinkData<T> {

	private SingleMutableLinkDataDelegate<T> delegate;

	public SingleMutableLinkData(String linkName, String relationLinkType, SingleMutableLinkDataDelegate<T> delegate) {
		super(linkName, relationLinkType);
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
		return new SingleLinkMutableLinkAccessor(model);
	}

	protected class SingleLinkMutableLinkAccessor extends AbstractLinkMutableLinkAccessor {

		public SingleLinkMutableLinkAccessor(T model) {
			super(model);
		}
		
		// MARK: AbstractLinkMutableLinkAccessor
		@Override
		public MutableLinkChangeResult modifyKeys(MutableLinkChange change) throws MutableLinkChangeException {
			Set<ModelKey> keys = change.getKeys();
			MutableLinkChangeType linkChangeType = change.getLinkChangeType();
			
			return null;
		}

		@Override
		public Set<ModelKey> getLinkedModelKeys() {
			// TODO Auto-generated method stub
			return null;
		}

	}

}
