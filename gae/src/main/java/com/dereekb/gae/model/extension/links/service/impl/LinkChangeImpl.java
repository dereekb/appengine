package com.dereekb.gae.model.extension.links.service.impl;

import java.util.Collection;

import com.dereekb.gae.model.extension.links.service.LinkChange;
import com.dereekb.gae.model.extension.links.service.LinkChangeAction;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LinkChange} implementation.
 *
 * @author dereekb
 *
 */
public class LinkChangeImpl
        implements LinkChange {

	private LinkChangeAction action;

	private String primaryType;
	private ModelKey primaryKey;

	private String linkName;

	private Collection<ModelKey> targetKeys;

	public LinkChangeImpl() {}

	public LinkChangeImpl(LinkChangeAction action,
	        String primaryType,
	        ModelKey primaryKey,
	        String linkName,
	        Collection<ModelKey> targetKeys) {
		this.action = action;
		this.primaryType = primaryType;
		this.primaryKey = primaryKey;
		this.linkName = linkName;
		this.targetKeys = targetKeys;
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
	public Collection<ModelKey> getTargetKeys() {
		return this.targetKeys;
	}

	public void setTargetKeys(Collection<ModelKey> targetKeys) {
		this.targetKeys = targetKeys;
	}

}
