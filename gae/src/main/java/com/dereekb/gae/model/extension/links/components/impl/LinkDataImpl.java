package com.dereekb.gae.model.extension.links.components.impl;

import java.util.Collection;

import com.dereekb.gae.model.extension.links.components.LinkData;
import com.dereekb.gae.model.extension.links.components.LinkInfo;
import com.dereekb.gae.model.extension.links.components.LinkTarget;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LinkData} implementation.
 *
 * @author dereekb
 *
 */
public class LinkDataImpl extends RelationImpl
        implements LinkData {

	private LinkInfo info;

	public LinkDataImpl(LinkInfo info) {
		this.info = info;
	}

	public LinkDataImpl(LinkInfo info, ModelKey currentKey) {
		super(currentKey);
		this.info = info;
	}

	public LinkDataImpl(LinkInfo info, Collection<ModelKey> currentKeys) {
		super(currentKeys);
		this.info = info;
	}

	@Override
	public String getLinkName() {
		return this.info.getLinkName();
	}

	@Override
	public ModelKey getLinkModelKey() {
		return this.info.getLinkModelKey();
	}

	@Override
	public LinkTarget getLinkTarget() {
		return this.info.getLinkTarget();
	}

	@Override
	public String toString() {
		return "LinkDataImpl [info=" + this.info + "]";
	}

}
