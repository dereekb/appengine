package com.dereekb.gae.model.extension.links.components.model.impl;

import java.util.Map;

import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.components.model.change.LinkModelChange;
import com.dereekb.gae.model.extension.links.components.model.change.LinkModelSetChange;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LinkModelSetChange} implemenation.
 *
 * @author dereekb
 *
 */
public class LinkModelSetChangeImpl
        implements LinkModelSetChange {

	private final LinkModelSet modelSet;
	private final Map<ModelKey, LinkModelChange> modelChanges;

	public LinkModelSetChangeImpl(LinkModelSet modelSet, Map<ModelKey, LinkModelChange> modelChanges) {
		this.modelSet = modelSet;
		this.modelChanges = modelChanges;
	}

	@Override
	public LinkModelSet getModelSet() {
		return this.modelSet;
	}

	@Override
	public Map<ModelKey, LinkModelChange> getModelChanges() {
		return this.modelChanges;
	}

	@Override
	public String toString() {
		return "LinkModelSetChangeImpl [modelSet=" + this.modelSet + ", modelChanges=" + this.modelChanges + "]";
	}

}
