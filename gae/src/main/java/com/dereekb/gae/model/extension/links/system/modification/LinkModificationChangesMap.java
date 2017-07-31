package com.dereekb.gae.model.extension.links.system.modification;

import java.util.List;

import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResultSet;

/**
 * Map that is used to filter redundant changes.
 * 
 * @author dereekb
 *
 */
@Deprecated
public interface LinkModificationChangesMap {

	/**
	 * Adds all results from a results set.
	 * 
	 * @param resultSet
	 *            {@link LinkModificationResultSet}.
	 */
	public void addResultSet(LinkModificationResultSet resultSet);

	/**
	 * Filters out redundant modifications.
	 * 
	 * @param modifications
	 *            {@link List}. Never {@code null}.
	 * @param resultSet
	 *            {@link LinkModificationResultSet}.
	 */
	public List<LinkModification> filterRedundantChanges(List<LinkModification> modifications);

}
