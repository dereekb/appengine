package com.dereekb.gae.model.extension.links.components.model.change.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dereekb.gae.model.extension.links.components.model.LinkModel;
import com.dereekb.gae.model.extension.links.components.model.change.LinkChange;
import com.dereekb.gae.model.extension.links.components.model.change.LinkModelChange;

/**
 * Default {@link LinkModelChange} implementation.
 *
 * @author dereekb
 *
 */
public class LinkModelChangeImpl
        implements LinkModelChange {

	private final LinkModel model;
	private final List<LinkChange> changes;

	public LinkModelChangeImpl(LinkModel model, List<LinkChange> changes) {
		this.model = model;
		this.changes = changes;
	}

	public static LinkModelChange empty(LinkModel model) {
		return new EmptyLinkModelChangeImpl(model);
	}

	@Override
	public LinkModel getModel() {
		return this.model;
	}

	/**
	 * Returns all {@link LinkChange} values.
	 *
	 * @see {@link #getModelChanges()} for filtered changes, per
	 *      {@link LinkModelChange} interface design.
	 */
	public List<LinkChange> getChanges() {
		return this.changes;
	}

	@Override
	public List<LinkChange> getModelChanges() {
		List<LinkChange> modelChanges = new ArrayList<LinkChange>();

		for (LinkChange change : this.changes) {
			if (change.hasChanges()) {
				modelChanges.add(change);
			}
		}

		return modelChanges;
	}

	@Override
	public boolean hasChanges() {
		boolean hasChanges = false;

		for (LinkChange change : this.changes) {
			if (change.hasChanges()) {
				hasChanges = true;
				break;
			}
		}

		return hasChanges;
	}

	@Override
	public String toString() {
		return "LinkModelChangeImpl [model=" + this.model + ", changes=" + this.changes + "]";
	}

	private static class EmptyLinkModelChangeImpl
	        implements LinkModelChange {

		private final LinkModel model;

		public EmptyLinkModelChangeImpl(LinkModel model) {
			this.model = model;
		}

		@Override
		public LinkModel getModel() {
			return this.model;
		}

		@Override
		public List<LinkChange> getModelChanges() {
			return Collections.emptyList();
		}

		@Override
		public boolean hasChanges() {
			return false;
		}

	}

}
