package com.dereekb.gae.model.extension.links.system.mutable.impl.link;

import com.dereekb.gae.model.extension.links.system.components.DynamicLinkAccessorInfo;
import com.dereekb.gae.model.extension.links.system.components.Relation;
import com.dereekb.gae.model.extension.links.system.components.exceptions.DynamicLinkInfoException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.NoRelationException;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkAccessor;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkData;

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

		private final T model;

		public AbstractLinkMutableLinkAccessor(T model) {
			this.model = model;
		}

		// MARK: MutableLinkAccessor
		@Override
		public DynamicLinkAccessorInfo getDynamicLinkAccessorInfo() {
			return this;
		}

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
