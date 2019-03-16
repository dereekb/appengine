package com.dereekb.gae.model.extension.links.system.modification.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChange;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChangesInstance;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResultSet;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModel;

/**
 * {@link LinkModificationSystemModelChangesInstance} implementation.
 * 
 * @author dereekb
 * @deprecated LinkModificationSystemModelChangesInstance was deprecated.
 */
@Deprecated
public class LinkModificationSystemModelChangesInstanceImpl
        implements LinkModificationSystemModelChangesInstance {

	private List<LinkModificationSystemModelChange> changes = new ArrayList<LinkModificationSystemModelChange>();

	public LinkModificationSystemModelChangesInstanceImpl() {}

	public LinkModificationSystemModelChangesInstanceImpl(List<LinkModificationSystemModelChange> changes) {
		super();
		this.setChanges(changes);
	}

	public List<LinkModificationSystemModelChange> getChanges() {
		return this.changes;
	}

	public void setChanges(List<LinkModificationSystemModelChange> changes) {
		if (changes == null) {
			throw new IllegalArgumentException("changes cannot be null.");
		}

		this.changes = changes;
	}

	// MARK: LinkModificationSystemModelChangesInstance
	@Override
	public void queueChange(LinkModificationSystemModelChange change) {
		this.changes.add(change);
	}

	@Override
	public LinkModificationResultSet applyChanges(MutableLinkModel linkModel) {

		for (LinkModificationSystemModelChange change : this.changes) {
			change.makeChange(linkModel);
		}

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void undoChanges(MutableLinkModel linkModel) {
		// TODO Auto-generated method stub

	}

}
