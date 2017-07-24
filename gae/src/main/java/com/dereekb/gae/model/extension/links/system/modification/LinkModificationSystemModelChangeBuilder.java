package com.dereekb.gae.model.extension.links.system.modification;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;

/**
 * Delegate for converting {@link LinkModification} to a
 * {@link LinkModificationSystemModelChange}.
 * 
 * @author dereekb
 * 
 */
public interface LinkModificationSystemModelChangeBuilder
        extends DirectionalConverter<LinkModificationPair, LinkModificationSystemModelChange>,
        SingleDirectionalConverter<LinkModificationPair, LinkModificationSystemModelChange> {

	/**
	 * Makes a change set for the converted values of the input {@link LinkModificationPair} list.
	 * 
	 * @param modifications
	 *            {@link Collection}. Never {@code null}.
	 * @return {@link LinkModificationSystemModelChangeSet}. Never {@code null}.
	 */
	public LinkModificationSystemModelChangeSet makeChangeSet(List<LinkModificationPair> modifications);

}
