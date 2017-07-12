package com.dereekb.gae.model.extension.links.system.mutable.impl.link;

import java.util.Set;

import com.dereekb.gae.model.extension.links.system.components.DynamicLinkAccessorInfo;
import com.dereekb.gae.model.extension.links.system.components.Relation;
import com.dereekb.gae.model.extension.links.system.components.exceptions.DynamicLinkInfoException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.NoRelationException;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkAccessor;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeResult;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.exception.MutableLinkChangeException;
import com.dereekb.gae.model.extension.links.system.mutable.impl.MutableLinkChangeResultImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Abstract {@link MutableLinkData}.
 * 
 * @author dereekb
 *
 * @param <T> model type
 */
public abstract class AbstractMutableLinkData<T>
        implements MutableLinkData<T> {

	private String linkName;
	private String relationLinkType;

	public AbstractMutableLinkData(String linkName, String relationLinkType) {
		super();
		this.linkName = linkName;
		this.relationLinkType = relationLinkType;
	}

	// MARK: MutableLinkData
	@Override
	public String getLinkName() {
		return this.linkName;
	}

	@Override
	public String getRelationLinkType() throws DynamicLinkInfoException {
		return this.relationLinkType;
	}

	protected abstract class AbstractLinkMutableLinkAccessor
	        implements MutableLinkAccessor, DynamicLinkAccessorInfo {

		protected final T model;

		public AbstractLinkMutableLinkAccessor(T model) {
			this.model = model;
		}

		// MARK: MutableLinkAccessor
		@Override
		public DynamicLinkAccessorInfo getDynamicLinkAccessorInfo() {
			return this;
		}
		
		/**
		 * {@inheritDoc}
		 * 
		 * The default implementation retrieves the keys set before and after, then returns an {@link MutableLinkChangeResultImpl}.
		 */
		@Override
		public MutableLinkChangeResult modifyKeys(MutableLinkChange change) throws MutableLinkChangeException {
			Set<ModelKey> beforeKeysSet = this.getLinkedModelKeys();
			
			this.applyLinkChange(change);
			
			Set<ModelKey> afterKeysSet = this.getLinkedModelKeys();
			
			return new MutableLinkChangeResultImpl(change, beforeKeysSet, afterKeysSet);
		}

		/**
		 * Applies a link change.
		 * 
		 * @param change {@link MutableLinkChange}. Never {@code null}.
		 * @throws MutableLinkChangeException thrown if the change cannot be applied.
		 */
		protected abstract void applyLinkChange(MutableLinkChange change) throws MutableLinkChangeException;

		// MARK: DynamicLinkAccessorInfo
		@Override
		public String getRelationLinkType() {
			return AbstractMutableLinkData.this.getRelationLinkType();
		}

		@Override
		public Relation getRelationInfo() throws UnsupportedOperationException, NoRelationException {
			throw new UnsupportedOperationException();
		}

	}

}
