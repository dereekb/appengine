package com.dereekb.gae.model.extension.links.service.impl;

import java.util.Set;

import com.dereekb.gae.model.extension.links.service.LinkSystemChange;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LinkSystemChange} implementation.
 *
 * @author dereekb
 * @deprecated Replaced by {@link LinkModificationSystemRequest}.
 */
@Deprecated
public class LinkSystemChangeImpl
        implements LinkSystemChange {

	private MutableLinkChangeType action;

	private String primaryType;
	private ModelKey primaryKey;

	private String linkName;

	private Set<String> targetStringKeys;

	public LinkSystemChangeImpl() {}

	public LinkSystemChangeImpl(MutableLinkChangeType action,
	        String primaryType,
	        ModelKey primaryKey,
	        String linkName,
	        Set<String> targetStringKeys) {
		this.setAction(action);
		this.setPrimaryType(primaryType);
		this.setPrimaryKey(primaryKey);
		this.setLinkName(linkName);
		this.setTargetStringKeys(targetStringKeys);
	}

	@Override
	public MutableLinkChangeType getAction() {
		return this.action;
	}

	public void setAction(MutableLinkChangeType action) {
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
		if (this.action != MutableLinkChangeType.CLEAR && (targetStringKeys == null || targetStringKeys.isEmpty())) {
			throw new IllegalArgumentException(
			        "Link Change Target Keys cannot be null or empty when the action is '" + this.action + "'.");
		}

		this.targetStringKeys = targetStringKeys;
	}

	@Override
	public String toString() {
		return "LinkSystemChangeImpl [action=" + this.action + ", primaryType=" + this.primaryType + ", primaryKey="
		        + this.primaryKey + ", linkName=" + this.linkName + ", targetStringKeys=" + this.targetStringKeys + "]";
	}

}
