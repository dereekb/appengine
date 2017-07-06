package com.dereekb.gae.model.extension.links.system.mutable.impl.link;

import java.util.Set;

import com.dereekb.gae.model.extension.links.system.components.DynamicLinkInfo;
import com.dereekb.gae.model.extension.links.system.components.LinkSize;
import com.dereekb.gae.model.extension.links.system.components.LinkType;
import com.dereekb.gae.model.extension.links.system.components.Relation;
import com.dereekb.gae.model.extension.links.system.components.exceptions.DynamicLinkInfoException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.NoRelationException;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkAccessor;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeResult;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.exception.MutableLinkChangeException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

public class MultipleMutableLinkData<T>
        implements MutableLinkData<T> {

	private String linkName;
	private String relationLinkType;

	// MARK: MutableLinkData
	@Override
	public String getLinkName() {
		return this.linkName;
	}

	@Override
	public LinkType getLinkType() {
		return LinkType.STATIC;
	}

	@Override
	public String getRelationLinkType() throws DynamicLinkInfoException {
		return this.relationLinkType;
	}

	@Override
	public LinkSize getLinkSize() {
		return LinkSize.ONE;
	}

	@Override
	public MutableLinkAccessor makeLinkAccessor(T model) {
		return new SingleLinkMutableLinkAccessor(model);
	}

	private class SingleLinkMutableLinkAccessor
	        implements MutableLinkAccessor, DynamicLinkInfo {

		private final T model;

		public SingleLinkMutableLinkAccessor(T model) {
			this.model = model;
		}

		// MARK: MutableLinkAccessor
		@Override
		public DynamicLinkInfo getDynamicLinkInfo() {
			return this;
		}

		@Override
		public Set<ModelKey> getLinkedModelKeys() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MutableLinkChangeResult modifyKeys(MutableLinkChange change) throws MutableLinkChangeException {
			// TODO Auto-generated method stub
			return null;
		}

		// MARK: DynamicLinkInfo
		@Override
		public String getRelationLinkType() {
			return SingleMutableLinkData.this.getRelationLinkType();
		}

		@Override
		public Relation getRelationInfo() throws NoRelationException {
			throw new UnsupportedOperationException();
		}

	}

}
