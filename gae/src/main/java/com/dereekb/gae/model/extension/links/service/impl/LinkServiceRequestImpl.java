package com.dereekb.gae.model.extension.links.service.impl;

import java.util.List;

import com.dereekb.gae.model.extension.links.service.LinkChange;
import com.dereekb.gae.model.extension.links.service.LinkServiceRequest;

/**
 * {@link LinkServiceRequest} implementation.
 *
 * @author dereekb
 */
public class LinkServiceRequestImpl
        implements LinkServiceRequest {

	private List<LinkChange> linkChanges;

	public LinkServiceRequestImpl(List<LinkChange> linkChanges) {
		this.linkChanges = linkChanges;
	}

	@Override
	public List<LinkChange> getLinkChanges() {
		return this.linkChanges;
	}

	public void setLinkChanges(List<LinkChange> linkChanges) {
		this.linkChanges = linkChanges;
	}

	@Override
	public String toString() {
		return "LinkServiceRequestImpl [linkChanges=" + this.linkChanges + "]";
	}

}
