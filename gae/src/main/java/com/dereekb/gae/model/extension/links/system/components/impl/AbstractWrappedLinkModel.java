package com.dereekb.gae.model.extension.links.system.components.impl;

import java.util.List;

import com.dereekb.gae.model.extension.links.system.components.Link;
import com.dereekb.gae.model.extension.links.system.components.LinkModel;
import com.dereekb.gae.model.extension.links.system.components.LinkModelInfo;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Abstract {@link LinkModel} that wraps another link model.
 * 
 * @author dereekb
 *
 */
public abstract class AbstractWrappedLinkModel
        implements LinkModel {

	protected final LinkModel linkModel;

	public AbstractWrappedLinkModel(LinkModel linkModel) {
		this.linkModel = linkModel;
	}

	// MARK: LinkModel
	@Override
	public ModelKey getModelKey() {
		return this.linkModel.getModelKey();
	}

	@Override
	public ModelKey keyValue() {
		return this.linkModel.keyValue();
	}

	@Override
	public String getLinkModelType() {
		return this.linkModel.getLinkModelType();
	}

	@Override
	public LinkModelInfo getLinkModelInfo() {
		return this.linkModel.getLinkModelInfo();
	}

	@Override
	public Link getLink(String linkName) throws UnavailableLinkException {
		return this.linkModel.getLink(linkName);
	}

	@Override
	public List<? extends Link> getLinks() {
		return this.linkModel.getLinks();
	}

}
