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

	/**
	 * Whether or not this change can result in a failure.
	 * <p>
	 * This {@link LinkModification} should always be attempted to be run, but
	 * optional changes (generally unlinking, as the model may no longer exist)
	 * should not result in a failure.
	 * 
	 * @return {@code true} if the result is not required to succeed.
	 */
	public boolean isOptional();

	/**
	 * Returns the target model's {@link ModelKey}.
	 * 
	 * @return {@link ModelKey}. Never {@code null}.
	 */
	public ModelKey getKey();

	/**
	 * Returns the target link to change.
	 * 
	 * @return {@link LinkInfo}. Never {@code null}.
	 */
	public LinkInfo getLink();

	/**
	 * Returns the link change request for the modification.
	 * 
	 * @return {@link MutableLinkChange}. Never {@code null}.
	 */
	public MutableLinkChange getChange();

}