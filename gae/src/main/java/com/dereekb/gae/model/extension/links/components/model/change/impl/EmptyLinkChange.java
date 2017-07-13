package com.dereekb.gae.model.extension.links.components.model.change.impl;

import java.util.Collections;
import java.util.Set;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.model.change.LinkChange;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LinkChange} implementation for links with no changes.
 *
 * @author dereekb
 *
 */
@Deprecated
public class EmptyLinkChange
        implements LinkChange {

	private final Link link;

	public EmptyLinkChange(Link link) {
		this.link = link;
	}

	@Override
	public Link getLink() {
		return this.link;
	}

	@Override
	public Set<ModelKey> getAddedKeys() {
		return Collections.emptySet();
	}

	@Override
	public Set<ModelKey> getRemovedKeys() {
		return Collections.emptySet();
	}

	@Override
	public boolean hasChanges() {
		return false;
	}

	@Override
	public String toString() {
		return "EmptyLinkChange [link=" + this.link + "]";
	}

}
