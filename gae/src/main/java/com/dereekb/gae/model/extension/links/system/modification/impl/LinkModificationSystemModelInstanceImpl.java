package com.dereekb.gae.model.extension.links.system.modification.impl;

import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChange;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChangeInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelInstance;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResult;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResultSet;
import com.dereekb.gae.model.extension.links.system.modification.components.impl.LinkModificationResultSetImpl;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModel;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LinkModificationSystemModelInstance} implementation.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class LinkModificationSystemModelInstanceImpl<T extends UniqueModel>
        implements LinkModificationSystemModelInstance<T> {

	private T model;
	private MutableLinkModel mutableLinkModel;

	private Set<LinkModificationSystemModelChangeInstance> changes = new HashSet<LinkModificationSystemModelChangeInstance>();

	public LinkModificationSystemModelInstanceImpl(T model, MutableLinkModel mutableLinkModel) {
		super();
		this.setModel(model);
		this.setMutableLinkModel(mutableLinkModel);
	}

	public void setModel(T model) {
		if (model == null) {
			throw new IllegalArgumentException("model cannot be null.");
		}

		this.model = model;
	}

	public void setMutableLinkModel(MutableLinkModel mutableLinkModel) {
		if (mutableLinkModel == null) {
			throw new IllegalArgumentException("mutableLinkModel cannot be null.");
		}

		this.mutableLinkModel = mutableLinkModel;
	}

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
		// Does nothing by default.
	}

	@Override
	public void queueChange(LinkModificationSystemModelChange change) {
		LinkModificationSystemModelChangeInstance instance = change.makeChange(this.mutableLinkModel);
		this.changes.add(instance);
	}

	@Override
	public LinkModificationResultSet applyChanges() {
		LinkModificationResultSetImpl set = new LinkModificationResultSetImpl();

		for (LinkModificationSystemModelChangeInstance change : this.changes) {
			LinkModificationResult result = change.applyChange();
			set.addResult(result);
		}

		return set;
	}

	@Override
	public void undoChanges() {
		for (LinkModificationSystemModelChangeInstance change : this.changes) {
			change.undoChange();
		}
	}

}
