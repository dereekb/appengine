package com.dereekb.gae.model.extension.links.system.modification.impl;

import java.util.List;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationChangesMap;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResultSet;

/**
 * {@link LinkModificationChangesMap} implementations.
 * 
 * @author dereekb
 *
 */
public class LinkModificationChangesMapImpl
        implements LinkModificationChangesMap {

	// MARK: LinkModificationChangesMap
	@Override
	public void addResultSet(LinkModificationResultSet resultSet) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<LinkModification> filterRedundantChanges(List<LinkModification> modifications) {
		return modifications; 	// TODO: Filter.
	}

}
