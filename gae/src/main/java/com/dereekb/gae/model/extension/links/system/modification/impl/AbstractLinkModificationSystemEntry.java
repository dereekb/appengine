package com.dereekb.gae.model.extension.links.system.modification.impl;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemEntry;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemEntryInstance;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Abstract {@link LinkModificationSystemEntry} implementation.
 * 
 * @author dereekb
 *
 */
public class AbstractLinkModificationSystemEntry<T extends UniqueModel>
        implements LinkModificationSystemEntry {

	// MARK: LinkModificationSystemEntry
	@Override
	public LinkModificationSystemEntryInstance makeInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
