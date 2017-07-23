package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
import com.dereekb.gae.utilities.collections.pairs.MutableSuccessPair;

/**
 * Mutable {@link LinkModificationPair}.
 * 
 * @author dereekb
 *
 */
public interface MutableLinkModificationPair extends LinkModificationPair, MutableSuccessPair<LinkModification> { }
