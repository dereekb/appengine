package com.dereekb.gae.model.extension.links.components.impl.link;

import com.dereekb.gae.model.extension.links.components.LinkTarget;
import com.dereekb.gae.model.extension.links.components.impl.LinkInfoImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Used by {@link DescriptiveModelLinkInfo} to create reverse info types.
 *
 * @author dereekb
 */
public class DescriptiveModelLinkInfo {

	private String linkName;
	private LinkTarget linkTarget;

	public DescriptiveModelLinkInfo(String linkName, LinkTarget linkTarget) {
		this.setLinkName(linkName);
		this.setLinkTarget(linkTarget);
	}

	public String getLinkName() {
		return this.linkName;
	}

	public void setLinkName(String linkName) {
		if (linkName == null) {
			throw new IllegalArgumentException("Link name cannot be null.");
		}

		this.linkName = linkName;
	}

	public LinkTarget getLinkTarget() {
		return this.linkTarget;
	}

	public void setLinkTarget(LinkTarget linkTarget) {
		if (linkTarget == null) {
			throw new IllegalArgumentException("Link target cannot be null.");
		}

		this.linkTarget = linkTarget;
	}

	public LinkInfoImpl toLinkInfo(ModelKey key) {
		return new LinkInfoImpl(this.linkName, key, this.linkTarget);
	}

}
