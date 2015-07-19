package com.dereekb.gae.model.extension.links.service.impl;

import java.util.List;

import com.dereekb.gae.model.extension.links.service.LinkSystemChange;
import com.dereekb.gae.model.extension.links.service.LinkServiceRequest;

/**
 * {@link LinkServiceRequest} implementation.
 *
 * @author dereekb
 */
public class LinkServiceRequestImpl
        implements LinkServiceRequest {

	private List<LinkSystemChange> linkChanges;

	public LinkServiceRequestImpl(List<LinkSystemChange> linkChanges) {
		this.linkChanges = linkChanges;
	}

	@Override
	public List<LinkSystemChange> getLinkChanges() {
		return this.linkChanges;
	}

	public void setLinkChanges(List<LinkSystemChange> linkChanges) {
		this.linkChanges = linkChanges;
	}

	@Override
	public String toString() {
		return "LinkServiceRequestImpl [linkChanges=" + this.linkChanges + "]";
	}

}
