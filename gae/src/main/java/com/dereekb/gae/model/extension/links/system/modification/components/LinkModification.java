package com.dereekb.gae.model.extension.links.system.modification.components;

import com.dereekb.gae.model.extension.links.system.components.LinkInfo;
import com.dereekb.gae.model.extension.links.system.components.TypedLinkSystemComponent;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.misc.keyed.AlwaysKeyed;

/**
 * Modification for a specific model and link.
 * 
 * @author dereekb
 *
 */
public interface LinkModification
        extends AlwaysKeyed<ModelKey>, TypedLinkSystemComponent {

	public boolean isOptional();

	public ModelKey getKey();

	public LinkInfo getLink();

	public MutableLinkChange getChange();

}