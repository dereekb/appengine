package com.dereekb.gae.model.extension.links.system.modification.impl;

import java.util.Set;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Abstract {@link LinkModificationSystemRequest} implementation that wraps another request.
 * 
 * @author dereekb
 *
 */
public abstract class AbstractWrappedLinkModificationSystemRequest implements LinkModificationSystemRequest {
	
	private LinkModificationSystemRequest request;

	public AbstractWrappedLinkModificationSystemRequest(LinkModificationSystemRequest request) {
		super();
		this.request = request;
	}

	// MARK: LinkModificationSystemRequest
	@Override
	public String getLinkModelType() {
		return this.request.getLinkModelType();
	}

	@Override
	public ModelKey keyValue() {
		return this.request.keyValue();
	}

	@Override
	public String getLinkName() {
		return this.request.getLinkName();
	}

	@Override
	public String getPrimaryKey() {
		return this.request.getPrimaryKey();
	}

	@Override
	public MutableLinkChangeType getLinkChangeType() {
		return this.request.getLinkChangeType();
	}

	@Override
	public String getDynamicLinkModelType() {
		return this.request.getDynamicLinkModelType();
	}

	@Override
	public Set<String> getKeys() {
		return this.request.getKeys();
	}

	@Override
	public String toString() {
		return "AbstractWrappedLinkModificationSystemRequest [request=" + this.request + "]";
	}

}
