package com.dereekb.gae.model.extension.links.components.impl;

import com.dereekb.gae.model.extension.links.deprecated.components.LinkInfo;
import com.dereekb.gae.model.extension.links.deprecated.components.LinkTarget;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * {@link LinkInfo} implementation.
 *
 * @author dereekb
 *
 */
@Deprecated
public class LinkInfoImpl
        implements LinkInfo {

	private String linkName;
	private ModelKey linkModelKey;
	private LinkTarget linkTarget;

	public LinkInfoImpl(String linkName, ModelKey linkModelKey, String linkTargetType, ModelKeyType linkTargetKeyType) {
		this(linkName, linkModelKey, new LinkTargetImpl(linkTargetType, linkTargetKeyType));
	}

	public LinkInfoImpl(String linkName, ModelKey linkModelKey, LinkTarget target) {
		this.setLinkName(linkName);
		this.setLinkModelKey(linkModelKey);
		this.setLinkTarget(target);
	}

	@Override
	public String getLinkName() {
		return this.linkName;
	}

	public void setLinkName(String linkName) {
		if (linkName == null) {
			throw new IllegalArgumentException("Link name cannot be null.");
		}

		this.linkName = linkName;
	}

	@Override
	public ModelKey getLinkModelKey() {
		return this.linkModelKey;
	}

	public void setLinkModelKey(ModelKey linkModelKey) {
		if (linkModelKey == null) {
			throw new IllegalArgumentException("Link model key cannot be null.");
		}

		this.linkModelKey = linkModelKey;
	}

	@Override
	public LinkTarget getLinkTarget() {
		return this.linkTarget;
	}

	public void setLinkTarget(LinkTarget linkTarget) {
		if (linkTarget == null) {
			throw new IllegalArgumentException("Link target cannot be null.");
		}

		this.linkTarget = linkTarget;
	}

	@Override
	public String toString() {
		return "LinkInfoImpl [linkName=" + this.linkName + ", linkModelKey=" + this.linkModelKey + ", linkTarget="
		        + this.linkTarget + "]";
	}

}
