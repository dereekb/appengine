package com.dereekb.gae.model.extension.links.service.impl;

import java.util.List;

import com.dereekb.gae.model.crud.task.config.impl.AtomicTaskConfigImpl;
import com.dereekb.gae.model.extension.links.service.LinkServiceRequest;
import com.dereekb.gae.model.extension.links.service.LinkSystemChange;

/**
 * {@link LinkServiceRequest} implementation.
 *
 * @author dereekb
 */
public class LinkServiceRequestImpl extends AtomicTaskConfigImpl
        implements LinkServiceRequest {

	private List<LinkSystemChange> linkChanges;

	public LinkServiceRequestImpl(List<LinkSystemChange> linkChanges) throws IllegalArgumentException {
		this(linkChanges, false);
	}

	public LinkServiceRequestImpl(List<LinkSystemChange> linkChanges, boolean atomic) throws IllegalArgumentException {
		super(atomic);
		this.setLinkChanges(linkChanges);
	}

	@Override
	public List<LinkSystemChange> getLinkChanges() {
		return this.linkChanges;
	}

	public void setLinkChanges(List<LinkSystemChange> linkChanges) throws IllegalArgumentException {
		if (linkChanges == null || linkChanges.isEmpty()) {
			throw new IllegalArgumentException("Changes must be provided.");
		}

		this.linkChanges = linkChanges;
	}

	@Override
	public String toString() {
		return "LinkServiceRequestImpl [linkChanges=" + this.linkChanges + "]";
	}

}
