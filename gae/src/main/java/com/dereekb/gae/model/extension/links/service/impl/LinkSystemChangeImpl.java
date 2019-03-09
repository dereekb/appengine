package com.dereekb.gae.model.extension.links.service.impl;

import java.util.Set;

import com.dereekb.gae.model.extension.links.service.LinkChangeAction;
import com.dereekb.gae.model.extension.links.service.LinkSystemChange;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LinkSystemChange} implementation.
 *
 * @author dereekb
 *
 */
public class LinkSystemChangeImpl
        implements LinkSystemChange {

	private LinkChangeAction action;

	private String primaryType;
	private ModelKey primaryKey;

	private String linkName;

	private Set<String> targetStringKeys;

	public LinkSystemChangeImpl() {}

	public LinkSystemChangeImpl(LinkChangeAction action,
	        String primaryType,
	        ModelKey primaryKey,
	        String linkName,
	        Set<String> targetStringKeys) {
		this.action = action;
		this.primaryType = primaryType;
		this.primaryKey = primaryKey;
		this.linkName = linkName;
		this.targetStringKeys = targetStringKeys;
	}

	@Override
	public LinkChangeAction getAction() {
		return this.action;
	}

	public void setAction(LinkChangeAction action) {
		this.action = action;
	}

	@Override
	public String getPrimaryType() {
		return this.primaryType;
	}

	public void setPrimaryType(String primaryType) {
		this.primaryType = primaryType;
	}

	@Override
	public ModelKey getPrimaryKey() {
		return this.primaryKey;
	}

	public void setPrimaryKey(ModelKey primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Override
	public String getLinkName() {
		return this.linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	@Override
	public Set<String> getTargetStringKeys() {
		return this.targetStringKeys;
	}

	public void setTargetStringKeys(Set<String> targetStringKeys) {
		if (targetStringKeys == null || targetStringKeys.isEmpty()) {
			throw new IllegalArgumentException();
		}

		this.targetStringKeys = targetStringKeys;
	}

	@Override
	public String toString() {
		return "LinkSystemChangeImpl [action=" + this.action + ", primaryType=" + this.primaryType + ", primaryKey="
		        + this.primaryKey + ", linkName=" + this.linkName + ", targetStringKeys=" + this.targetStringKeys + "]";
	}

}
