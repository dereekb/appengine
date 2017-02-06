package com.dereekb.gae.model.extension.links.components.model.change.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.exception.UnavailableLinkException;
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
	private final Map<String, LinkChange> changes;

	public LinkModelChangeImpl(LinkModel model, Map<String, LinkChange> changes) {
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
	 * Returns {@link #changes}.
	 *
	 * @see {@link #getChangesForLink(String)} or {@link #getLinkChanges()} for
	 *      filtered changes, per {@link LinkModelChange} interface design.
	 */
	public Map<String, LinkChange> getChanges() {
		return this.changes;
	}

	@Override
	public List<LinkChange> getLinkChanges() {
		List<LinkChange> modelChanges = new ArrayList<>();

		for (LinkChange change : this.changes.values()) {
			if (change.hasChanges()) {
				modelChanges.add(change);
			}
		}

		return modelChanges;
	}

	@Override
	public LinkChange getChangesForLink(String link) {
		LinkChange changes = this.changes.get(link);
		return changes;
	}

	@Override
	public boolean hasChanges() {
		boolean hasChanges = false;

		for (LinkChange change : this.changes.values()) {
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
		public List<LinkChange> getLinkChanges() {
			return Collections.emptyList();
		}

		@Override
		public boolean hasChanges() {
			return false;
		}

		@Override
		public LinkChange getChangesForLink(String name) throws UnavailableLinkException {
			Link link = this.model.getLink(name);
			return new EmptyLinkChange(link);
		}

	}

}
