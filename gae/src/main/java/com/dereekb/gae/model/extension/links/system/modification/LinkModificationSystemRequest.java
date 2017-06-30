package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.components.TypedLinkSystemComponent;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LinkModificationSystemInstance} request to a model's links.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystemRequest
        extends MutableLinkChange, TypedLinkSystemComponent {

	/**
	 * Returns the link name.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getLinkName();

	/**
	 * Returns the key of the primary model being modified.
	 * 
	 * @return {@link ModelKey}. Never {@code null}.
	 */
	public ModelKey getPrimaryKey();

}
