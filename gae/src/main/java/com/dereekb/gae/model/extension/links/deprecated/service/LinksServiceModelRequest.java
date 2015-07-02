package com.dereekb.gae.model.extension.links.deprecated.service;

import java.util.Collection;

import com.dereekb.gae.model.extension.links.deprecated.functions.LinksAction;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.SingleItem;

/**
 * Request for a LinksServiceComponent that uses {@link ModelKey} and lets the
 * component read the models.
 *
 * @author dereekb
 *
 * @see {@link LinksServiceKeyRequest} for using keys instead.
 */
public class LinksServiceModelRequest<T>
        implements LinksServiceRequest {

	private String type;
	private LinksAction action;
	private Collection<T> targets;
	private Collection<ModelKey> linkKeys;

	public LinksServiceModelRequest(T target, ModelKey linkKey, String type, LinksAction action) {
		super();
		this.targets = SingleItem.withValue(target);
		this.linkKeys = SingleItem.withValue(linkKey);
		this.action = action;
		this.type = type;
	}

	public LinksServiceModelRequest(Collection<T> targets, ModelKey linkKey, String type, LinksAction action) {
		super();
		this.targets = targets;
		this.linkKeys = SingleItem.withValue(linkKey);
		this.action = action;
		this.type = type;
	}

	public LinksServiceModelRequest(T target, Collection<ModelKey> linkKeys, String type, LinksAction action) {
		super();
		this.targets = SingleItem.withValue(target);
		this.linkKeys = linkKeys;
		this.action = action;
		this.type = type;
	}

	public LinksServiceModelRequest(Collection<T> targets,
	        Collection<ModelKey> linkKeys,
	        String type,
	        LinksAction action) {
		super();
		this.targets = targets;
		this.linkKeys = linkKeys;
		this.action = action;
		this.type = type;
	}

	@Override
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
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
		if (keys == null || this.targets.isEmpty()) {
			throw new IllegalArgumentException("Keys collection cannot be null or empty.");
		}

		this.linkKeys = keys;
	}

	public Collection<T> getTargets() {
		return this.targets;
	}

	public void setTargets(Collection<T> targets) throws IllegalArgumentException {
		if (targets == null || targets.isEmpty()) {
			throw new IllegalArgumentException("Targets collection cannot be null or empty.");
		}

		this.targets = targets;
	}

	@Override
	public String toString() {
		return "LinksServiceModelRequest [type=" + this.type + ", action=" + this.action + ", targets=" + this.targets
		        + ", linkKeys=" + this.linkKeys + "]";
	}

}
