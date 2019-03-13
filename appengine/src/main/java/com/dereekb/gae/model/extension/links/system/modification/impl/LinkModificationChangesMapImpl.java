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
 * @deprecated No longer necessary.
 */
@Deprecated
public class LinkModificationChangesMapImpl
        implements LinkModificationChangesMap {

	// MARK: LinkModificationChangesMap
	@Override
	public void addResultSet(LinkModificationResultSet resultSet) {

		// TODO Auto-generated method stub

	}

	@Override
	public List<LinkModification> filterRedundantChanges(List<LinkModification> modifications) {

		/*
		 * TODO: Going to have to figure out the best way to prevent
		 * redundant changes from happening and taking up CPU time.
		 * 
		 * For instance, if A has already been updated to reference A, then
		 * B is updated and returns that A needs to be updated, this needs
		 * to be marked as redundant and ignored.
		 * 
		 * Realistically though, this might not be too complex to be worth
		 * it. Would have to build a map of changes made then use
		 * it to filter out redundant requests, which isn't that
		 * difficult I suppose.
		 */

		return modifications; 	// TODO: Filter.
	}

}
