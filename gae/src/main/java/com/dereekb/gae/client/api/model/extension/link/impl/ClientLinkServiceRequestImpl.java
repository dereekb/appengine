package com.dereekb.gae.client.api.model.extension.link.impl;

import java.util.List;

import com.dereekb.gae.client.api.model.extension.link.ClientLinkServiceRequest;
import com.dereekb.gae.web.api.model.extension.link.ApiLinkChange;

/**
 * {@link ClientLinkServiceRequest} implementation.
 * 
 * @author dereekb
 *
 */
public class ClientLinkServiceRequestImpl
        implements ClientLinkServiceRequest {

	private boolean atomic;

	private String type;
	private List<ApiLinkChange> changes;

	public ClientLinkServiceRequestImpl(String type, List<ApiLinkChange> changes) {
		this(type, changes, true);
	}

	public ClientLinkServiceRequestImpl(String type, List<ApiLinkChange> changes, boolean atomic) {
		this.setType(type);
		this.setChanges(changes);
		this.setAtomic(atomic);
	}

	@Override
	public boolean isAtomic() {
		return this.atomic;
	}

	public void setAtomic(boolean atomic) {
		this.atomic = atomic;
	}

	@Override
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		if (type == null || type.isEmpty()) {
			throw new IllegalArgumentException("Type cannot be null or empty.");
		}

		this.type = type;
	}

	@Override
	public List<ApiLinkChange> getChanges() {
		return this.changes;
	}

	public void setChanges(List<ApiLinkChange> changes) {
		if (changes == null || changes.isEmpty()) {
			throw new IllegalArgumentException("changes cannot be null or empty.");
		}

		this.changes = changes;
	}

	@Override
	public String toString() {
		return "ClientLinkServiceRequestImpl [atomic=" + this.atomic + ", type=" + this.type + ", changes="
		        + this.changes + "]";
	}

}
