package com.dereekb.gae.model.extension.links.system.mutable.impl.link;

import java.util.Collections;
import java.util.Set;

import com.dereekb.gae.model.extension.links.system.components.LinkSize;
import com.dereekb.gae.model.extension.links.system.components.LinkType;
import com.dereekb.gae.model.extension.links.system.components.SimpleLinkInfo;
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

	public MultipleMutableLinkData(SimpleLinkInfo linkInfo,
	        MultipleMutableLinkDataDelegate<T> delegate) {
		super(linkInfo);
		this.delegate = delegate;
	}

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
			Set<ModelKey> newKeys = null;
			
			switch (change.getLinkChangeType()) {
				case ADD:
					Set<ModelKey> linkedKeysA = this.getLinkedModelKeys();
					linkedKeysA.addAll(change.getKeys());
					newKeys = linkedKeysA;
					break;
				case REMOVE:
					Set<ModelKey> linkedKeysR = this.getLinkedModelKeys();
					linkedKeysR.removeAll(change.getKeys());
					newKeys = linkedKeysR;
					break;
				case CLEAR:
					newKeys = Collections.emptySet();
					break;
				case SET:
					newKeys = change.getKeys();
					break;
				default:
					throw new UnsupportedOperationException();
			}
			
			// Set the keys.
			MultipleMutableLinkData.this.delegate.setLinkedModelKeys(this.model, newKeys);
		}
	
	}

	@Override
	public String toString() {
		return "MultipleMutableLinkData [delegate=" + this.delegate + "]";
	}

}
