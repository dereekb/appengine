package com.dereekb.gae.model.extension.links.deprecated.service;

import java.util.Collection;

import com.dereekb.gae.model.extension.links.deprecated.functions.LinksAction;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.SingleItem;

/**
 * Request for a LinksServiceComponent.
 *
 * @author dereekb
 *
 * @see {@link LinksServiceModelRequest} to send models instead.
 */
public class LinksServiceKeyRequest
        implements LinksServiceRequest {

	private String type;
	private LinksAction action;
	private Collection<ModelKey> targetKeys;
	private Collection<ModelKey> linkKeys;

	public LinksServiceKeyRequest(ModelKey targetKey, ModelKey linkKey, String type, LinksAction action) {
		super();
		this.targetKeys = SingleItem.withValue(targetKey);
		this.linkKeys = SingleItem.withValue(linkKey);
		this.action = action;
		this.type = type;
	}

	public LinksServiceKeyRequest(Collection<ModelKey> targetKeys,
	        ModelKey linkKey,
	        String type,
	        LinksAction action) {
		super();
		this.targetKeys = targetKeys;
		this.linkKeys = SingleItem.withValue(linkKey);
		this.action = action;
		this.type = type;
	}

	public LinksServiceKeyRequest(ModelKey targetKey, Collection<ModelKey> linkKeys,
	        String type,
	        LinksAction action) {
		super();
		this.targetKeys = SingleItem.withValue(targetKey);
		this.linkKeys = linkKeys;
		this.action = action;
		this.type = type;
	}

	public LinksServiceKeyRequest(Collection<ModelKey> targetKeys,
	        Collection<ModelKey> linkKeys,
	        String type,
	        LinksAction action) {
		super();
		this.targetKeys = targetKeys;
		this.linkKeys = linkKeys;
		this.action = action;
		this.type = type;
	}

	@Override
    public LinksAction getAction() {
		return this.action;
	}

	public void setAction(LinksAction action) {
		this.action = action;
	}

	@Override
	public Collection<ModelKey> getLinkKeys() {
		return this.linkKeys;
	}

	public void setLinkKeys(Collection<ModelKey> keys) {
		if (keys == null || keys.isEmpty()) {
			throw new IllegalArgumentException("Keys collection cannot be null or empty.");
		}

		this.linkKeys = keys;
	}

	public Collection<ModelKey> getTargetKeys() {
		return this.targetKeys;
	}

	public void setTargetKeys(Collection<ModelKey> keys) throws IllegalArgumentException {
		if (keys == null || keys.isEmpty()) {
			throw new IllegalArgumentException("Keys collection cannot be null or empty.");
		}

		this.targetKeys = keys;
	}

	@Override
    public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
