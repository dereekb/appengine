package com.dereekb.gae.model.extension.links.system.modification.impl;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemChangeInstance;

/**
 * Abstract {@link LinkModificationSystemChangeInstance} implementation.
 * 
 * @author dereekb
 *
 */
public abstract class AbstractLinkModificationSystemChangeInstance
        implements LinkModificationSystemChangeInstance {

	// MARK: LinkModificationSystemChangeInstance
	@Override
	public void commitChanges() {
		for (LinkModificationSystemChangeInstance instance : this.getInstances()) {
			instance.commitChanges();
		}
	}

	@Override
	public void undoChanges() {
		for (LinkModificationSystemChangeInstance instance : this.getInstances()) {
			instance.undoChanges();
		}
	}

	// MARK: Internal
	public abstract Iterable<? extends LinkModificationSystemChangeInstance> getInstances();

}
