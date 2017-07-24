package com.dereekb.gae.model.extension.links.system.modification.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChange;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChangeInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChangeInstanceSet;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChangeSet;
import com.dereekb.gae.model.extension.links.system.modification.exception.NoUndoChangesException;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModel;

/**
 * {@link LinkModificationSystemModelChangeSet} implementation.
 * 
 * @author dereekb
 *
 */
public class LinkModificationSystemModelChangeSetImpl
        implements LinkModificationSystemModelChangeSet {

	private Boolean isOptional;
	private List<LinkModificationSystemModelChange> changes;

	public LinkModificationSystemModelChangeSetImpl(List<LinkModificationSystemModelChange> changes) {
		this.setChanges(changes);
	}

	protected void setChanges(List<LinkModificationSystemModelChange> changes) {
		if (changes == null) {
			throw new IllegalArgumentException("changes cannot be null.");
		}

		this.changes = changes;
	}

	// MARK: LinkModificationSystemModelChangeSet
	@Override
	public boolean isOptional() {
		if (this.isOptional == null) {
			this.isOptional = true;

			for (LinkModificationSystemModelChange change : this.changes) {
				if (change.isOptional() == false) {
					this.isOptional = false;
					break;
				}
			}
		}

		return this.isOptional;
	}

	@Override
	public LinkModificationSystemModelChangeInstanceSet makeInstanceWithModel(MutableLinkModel linkModel) {
		return new LinkModificationSystemModelChangeInstanceSetImpl(linkModel);
	}

	protected List<LinkModificationSystemModelChangeInstance> makeInstancesForModel(MutableLinkModel linkModel) {
		List<LinkModificationSystemModelChangeInstance> instances = new ArrayList<LinkModificationSystemModelChangeInstance>();

		for (LinkModificationSystemModelChange change : this.changes) {
			LinkModificationSystemModelChangeInstance instance = change.makeChangeInstance(linkModel);
			instances.add(instance);
		}

		return instances;
	}

	protected class LinkModificationSystemModelChangeInstanceSetImpl
	        implements LinkModificationSystemModelChangeInstanceSet {

		private final MutableLinkModel linkModel;
		private List<LinkModificationSystemModelChangeInstance> instances;

		public LinkModificationSystemModelChangeInstanceSetImpl(MutableLinkModel linkModel) {
			super();
			this.linkModel = linkModel;
		}

		// MARK: LinkModificationSystemModelChangeInstanceSet
		@Override
		public MutableLinkModel getLinkModel() {
			return this.linkModel;
		}

		@Override
		public boolean applyChanges() {
			List<LinkModificationSystemModelChangeInstance> instances = this.getInstances();

			boolean modified = false;

			for (LinkModificationSystemModelChangeInstance instance : instances) {
				modified = modified || instance.applyChange().isModelModified();
			}

			return modified;
		}

		@Override
		public boolean undoChanges() {
			List<LinkModificationSystemModelChangeInstance> instances = this.getInstances();

			boolean modified = false;

			for (LinkModificationSystemModelChangeInstance instance : instances) {
				try {
					modified = modified || instance.undoChange().isModelModified();
				} catch (NoUndoChangesException e) {
					// Continue loop.
				}
			}

			return modified;
		}

		protected List<LinkModificationSystemModelChangeInstance> getInstances() {
			if (this.instances == null) {
				this.instances = LinkModificationSystemModelChangeSetImpl.this.makeInstancesForModel(this.linkModel);
			}

			return this.instances;
		}

	}

}
