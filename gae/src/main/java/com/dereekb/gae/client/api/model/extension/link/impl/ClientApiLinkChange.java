package com.dereekb.gae.client.api.model.extension.link.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.dereekb.gae.web.api.model.extension.link.ApiLinkChange;
import com.dereekb.gae.web.api.model.extension.link.impl.ApiLinkChangeImpl;

/**
 * {@link ApiLinkChange} implementation used by clients.
 * 
 * @author dereekb
 */
public class ClientApiLinkChange
        implements ApiLinkChange {

	private String id;

	private MutableLinkChangeType changeAction;

	private ModelKey primary;

	private String linkName;

	private Set<ModelKey> targetKeys = Collections.emptySet();

	public ClientApiLinkChange() {}

	public ClientApiLinkChange(MutableLinkChangeType changeAction,
	        ModelKey primary,
	        String linkName,
	        Set<ModelKey> targetKeys) {
		super();
		this.setChangeAction(changeAction);
		this.setPrimary(primary);
		this.setLinkName(linkName);
		this.setTargetKeys(targetKeys);
	}

	public static ClientApiLinkChange clear(ModelKey primaryKey,
	                                        String linkName) {
		return new ClientApiLinkChange(MutableLinkChangeType.CLEAR, primaryKey, linkName, null);
	}

	// MARK: Accessors
	@Override
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public MutableLinkChangeType getChangeAction() {
		return this.changeAction;
	}

	public void setChangeAction(MutableLinkChangeType changeAction) {
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

	public void setTargetKey(ModelKey modelKey) {
		this.setTargetKeys(SetUtility.makeSet(modelKey));
	}

	public void setTargetKeys(Collection<ModelKey> targetKeys) {
		Set<ModelKey> keysSet = null;

		if (targetKeys != null) {
			keysSet = new HashSet<ModelKey>(targetKeys);
		}

		this.setTargetKeys(keysSet);
	}

	public void setTargetKeys(Set<ModelKey> targetKeys) {
		if (targetKeys == null) {
			targetKeys = Collections.emptySet();
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
