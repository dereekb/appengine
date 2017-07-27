package com.dereekb.gae.model.extension.links.system.modification.impl;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemChangeInstance;
import com.dereekb.gae.model.extension.links.system.modification.exception.internal.ChangesAlreadyComittedException;
import com.dereekb.gae.model.extension.links.system.modification.exception.internal.UndoChangesAlreadyExecutedException;

/**
 * Abstract {@link LinkModificationSystemChangeInstance} implementation.
 * 
 * @author dereekb
 *
 */
public abstract class AbstractLinkModificationSystemChangeInstance
        implements LinkModificationSystemChangeInstance {
	
	private boolean undoneChanges = false;
	private boolean committedChanges = false;

	// MARK: LinkModificationSystemChangeInstance
	@Override
	public void commitChanges() {
		this.assertNoChanges();
		
		this.committedChanges = true;
		
		for (LinkModificationSystemChangeInstance instance : this.getInstances()) {
			instance.commitChanges();
		}
	}

	@Override
	public void undoChanges() {
		this.assertNoChanges();
		
		this.undoneChanges = true;
		
		for (LinkModificationSystemChangeInstance instance : this.getInstances()) {
			instance.undoChanges();
		}
	}

	protected void assertNoChanges() {
		if (this.undoneChanges) {
			 throw new UndoChangesAlreadyExecutedException();
		}
		
		if (this.committedChanges) {
			 throw new ChangesAlreadyComittedException();
		}
	}

	// MARK: Internal
	public abstract Iterable<? extends LinkModificationSystemChangeInstance> getInstances();

}
