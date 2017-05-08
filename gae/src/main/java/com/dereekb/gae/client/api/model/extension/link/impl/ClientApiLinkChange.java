package com.dereekb.gae.client.api.model.extension.link.impl;

import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.model.extension.links.service.LinkChangeAction;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.model.extension.link.ApiLinkChange;
import com.dereekb.gae.web.api.model.extension.link.impl.ApiLinkChangeImpl;

/**
 * {@link ApiLinkChange} implementation used by clients.
 * 
 * @author dereekb
 */
public class ClientApiLinkChange
        implements ApiLinkChange {

	private LinkChangeAction changeAction;

	private ModelKey primary;

	private String linkName;

	private Set<ModelKey> targetKeys;

	public ClientApiLinkChange() {}

	public ClientApiLinkChange(LinkChangeAction changeAction,
	        ModelKey primary,
	        String linkName,
	        Set<ModelKey> targetKeys) {
		super();
		this.setChangeAction(changeAction);
		this.setPrimary(primary);
		this.setLinkName(linkName);
		this.setTargetKeys(targetKeys);
	}

	// MARK: Accessors
	public LinkChangeAction getChangeAction() {
		return this.changeAction;
	}

	public void setChangeAction(LinkChangeAction changeAction) {
		if (changeAction == null) {
			throw new IllegalArgumentException("changeAction cannot be null.");
		}

		this.changeAction = changeAction;
	}

	public ModelKey getPrimary() {
		return this.primary;
	}

	public void setPrimary(ModelKey primary) {
		if (primary == null) {
			throw new IllegalArgumentException("primary cannot be null.");
		}

		this.primary = primary;
	}

	public void setLinkName(String linkName) {
		if (linkName == null) {
			throw new IllegalArgumentException("linkName cannot be null.");
		}

		this.linkName = linkName;
	}

	public void setTargetKeys(Set<ModelKey> targetKeys) {
		if (targetKeys == null) {
			throw new IllegalArgumentException("targetKeys cannot be null.");
		}

		this.targetKeys = targetKeys;
	}

	// MARK: ApiLinkChange
	@Override
	public String getAction() {
		return this.changeAction.getActionName();
	}

	@Override
	public String getPrimaryKey() {
		return this.primary.toString();
	}

	@Override
	public String getLinkName() {
		return this.linkName;
	}

	@Override
	public Set<String> getTargetKeys() {
		return new HashSet<String>(ModelKey.readStringKeys(this.targetKeys));
	}

	public ApiLinkChange asLinkChange() {
		return new ApiLinkChangeImpl(this.getAction(), this.getPrimaryKey(), this.getLinkName(), this.getTargetKeys());
	}

	@Override
	public String toString() {
		return "ClientApiLinkChange [changeAction=" + this.changeAction + ", primary=" + this.primary + ", linkName="
		        + this.linkName + ", targetKeys=" + this.targetKeys + "]";
	}

}
