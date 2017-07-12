package com.dereekb.gae.model.extension.links.system.mutable.impl.link;

import java.util.Set;

import com.dereekb.gae.model.extension.links.system.components.LinkSize;
import com.dereekb.gae.model.extension.links.system.components.LinkType;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkAccessor;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.exception.MutableLinkChangeException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.list.SetUtility;

/**
 * {@link MutableLinkData} implementation for a model with a set representing a
 * link.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class MultipleMutableLinkData<T> extends AbstractMutableLinkData<T> {

	private MultipleMutableLinkDataDelegate<T> delegate;

	public MultipleMutableLinkData(String linkName,
	        String relationLinkType,
	        MultipleMutableLinkDataDelegate<T> delegate) {
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
		return LinkSize.MANY;
	}

	@Override
	public MutableLinkAccessor makeLinkAccessor(T model) {
		return new Accessor(model);
	}

	private class Accessor extends AbstractLinkMutableLinkAccessor {

		public Accessor(T model) {
			super(model);
		}

		@Override
		public Set<ModelKey> getLinkedModelKeys() {
			Set<ModelKey> modelKeys = MultipleMutableLinkData.this.delegate.readLinkedModelKeys(this.model);
			return SetUtility.copy(modelKeys);
		}

		@Override
		protected void applyLinkChange(MutableLinkChange change) throws MutableLinkChangeException {

			switch (change.getLinkChangeType()) {
				case ADD:
					
					break;
				case CLEAR:
					
					break;
				case REMOVE:
					
					break;
				case SET:
					
					break;
				default:
					throw new UnsupportedOperationException();
			}
		}
	
	}

}
