package com.dereekb.gae.model.extension.links.system.modification.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChange;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChangeInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChangeInstanceSet;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChangeSet;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResult;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResultSet;
import com.dereekb.gae.model.extension.links.system.modification.components.impl.LinkModificationResultSetImpl;
import com.dereekb.gae.model.extension.links.system.modification.exception.LinkModelMismatchException;
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
	public LinkModificationSystemModelChangeInstanceSet makeChangeSet(MutableLinkModel linkModel) {
		return new LinkModificationSystemModelChangeInstanceSetImpl(linkModel);
	}

	protected List<LinkModificationSystemModelChangeInstance> makeInstancesForModel(MutableLinkModel linkModel) {
		List<LinkModificationSystemModelChangeInstance> instances = new ArrayList<LinkModificationSystemModelChangeInstance>();

		for (LinkModificationSystemModelChange change : this.changes) {
			LinkModificationSystemModelChangeInstance instance = change.makeChange(linkModel);
			instances.add(instance);
		}

		return instances;
	}

	protected class LinkModificationSystemModelChangeInstanceSetImpl
	        implements LinkModificationSystemModelChangeInstanceSet {

		private final MutableLinkModel linkModel;
		private List<LinkModificationSystemModelChangeInstance> instances;

		private LinkModificationResultSet resultSet;

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
		public LinkModificationResultSet applyChanges() {
			if (this.resultSet == null) {
				LinkModificationResultSetImpl resultSet = new LinkModificationResultSetImpl();

				for (LinkModificationSystemModelChangeInstance instance : this.getInstances()) {
					LinkModificationResult result = instance.applyChange();
					resultSet.addResult(result);
				}

				this.resultSet = resultSet;
			}

			return this.resultSet;
		}

		protected List<LinkModificationSystemModelChangeInstance> getInstances() {
			if (this.instances == null) {
				this.instances = LinkModificationSystemModelChangeSetImpl.this.makeInstancesForModel(this.linkModel);
			}

			return this.instances;
		}

		@Override
		public boolean undoChanges(MutableLinkModel linkModel) throws LinkModelMismatchException {
			if (linkModel == null) {
				linkModel = this.linkModel;
			} else if (this.linkModel.getModelKey().equals(linkModel.getModelKey()) == false) {
				throw new LinkModelMismatchException();
			}

			if (this.instances != null) {
				boolean modified = false;

				for (LinkModificationSystemModelChangeInstance instance : this.getInstances()) {
					modified = modified || instance.undoChange(linkModel);
				}

				return modified;
			} else {
				return false;
			}
		}

	}

}
