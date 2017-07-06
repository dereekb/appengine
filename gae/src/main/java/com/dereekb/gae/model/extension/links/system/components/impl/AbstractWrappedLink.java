package com.dereekb.gae.model.extension.links.system.components.impl;

import java.util.Set;

import com.dereekb.gae.model.extension.links.system.components.DynamicLinkAccessorInfo;
import com.dereekb.gae.model.extension.links.system.components.Link;
import com.dereekb.gae.model.extension.links.system.components.LinkInfo;
import com.dereekb.gae.model.extension.links.system.components.LinkModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Abstract {@link Link} implementation that wraps another {@link Link}.
 * @author dereekb
 *
 */
public class AbstractWrappedLink
        implements Link {

	protected final Link link;

	public AbstractWrappedLink(Link link) {
		this.link = link;
	}

	// MARK: Link
	@Override
	public Set<ModelKey> getLinkedModelKeys() {
		return this.link.getLinkedModelKeys();
	}

	@Override
	public LinkInfo getLinkInfo() {
		return this.link.getLinkInfo();
	}

	@Override
	public LinkModel getLinkModel() {
		return this.link.getLinkModel();
	}

	@Override
	public DynamicLinkAccessorInfo getDynamicLinkAccessorInfo() {
		return this.link.getDynamicLinkAccessorInfo();
	}

}
