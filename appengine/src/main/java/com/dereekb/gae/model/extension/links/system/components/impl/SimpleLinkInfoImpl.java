package com.dereekb.gae.model.extension.links.system.components.impl;

import com.dereekb.gae.model.extension.links.system.components.SimpleLinkInfo;

/**
 * {@link SimpleLinkInfo} implementation.
 * 
 * @author dereekb
 *
 */
public class SimpleLinkInfoImpl implements SimpleLinkInfo {
	
	private String linkName;
	private String relationLinkType;

	public SimpleLinkInfoImpl(String relationLinkTypeAndLinkName) {
		this(relationLinkTypeAndLinkName, relationLinkTypeAndLinkName);
	}

	public SimpleLinkInfoImpl(String linkName, String relationLinkType) {
		this.setLinkName(linkName);
		this.setRelationLinkType(relationLinkType);
	}

	@Override
	public String getLinkName() {
		return this.linkName;
	}
	
	public void setLinkName(String linkName) {
		if (linkName == null) {
			throw new IllegalArgumentException("linkName cannot be null.");
		}
	
		this.linkName = linkName;
	}
	
	@Override
	public String getRelationLinkType() {
		return this.relationLinkType;
	}
	
	public void setRelationLinkType(String relationLinkType) {
		if (relationLinkType == null) {
			throw new IllegalArgumentException("relationLinkType cannot be null.");
		}
	
		this.relationLinkType = relationLinkType;
	}

	@Override
	public String toString() {
		return "SimpleLinkInfoImpl [linkName=" + this.linkName + ", relationLinkType=" + this.relationLinkType + "]";
	}

}
