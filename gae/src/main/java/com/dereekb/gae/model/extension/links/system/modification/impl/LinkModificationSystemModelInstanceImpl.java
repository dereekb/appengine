package com.dereekb.gae.model.extension.links.system.modification.impl;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChange;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelInstance;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResultSet;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModel;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

public class LinkModificationSystemModelInstanceImpl<T extends UniqueModel>
        implements LinkModificationSystemModelInstance<T> {

	private T model;
	private MutableLinkModel mutableLinkModel;

	// MARK: LinkModificationSystemModelInstance
	@Override
	public T getModel() {
		return this.model;
	}

	@Override
	public MutableLinkModel getMutableLinkModel() {
		return this.mutableLinkModel;
	}

	@Override
	public ModelKey keyValue() {
		return this.model.getModelKey();
	}

	@Override
	public void commitChanges() {
		// Do nothing by default.
	}

	@Override
	public void revertChanges() {
		// TODO Auto-generated method stub

	}

	@Override
	public void queueChange(LinkModificationSystemModelChange change) {
		// TODO Auto-generated method stub

	}

	@Override
	public LinkModificationResultSet applyChanges() {
		// TODO Auto-generated method stub
		return null;
	}

}
