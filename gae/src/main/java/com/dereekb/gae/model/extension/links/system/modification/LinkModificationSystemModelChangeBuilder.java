package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;

/**
 * Delegate for building changes.
 * 
 * @author dereekb
 * 
 */
public interface LinkModificationSystemModelChangeBuilder
        extends DirectionalConverter<LinkModification, LinkModificationSystemModelChange>,
        SingleDirectionalConverter<LinkModification, LinkModificationSystemModelChange> {

}
