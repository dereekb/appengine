package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.extension.links.system.readonly.LinkSystem;

/**
 * Thread-safe system that safely performs changes on models.
 * <p>
 * Acts as a factory for {@link LinkModificationSystemInstance}.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystem extends LinkSystem {

	/**
	 * Generates a new {@link LinkModificationSystemInstance}.
	 * 
	 * @return {@link LinkModificationSystemInstance}. Never {@code null}.
	 */
	public LinkModificationSystemInstance makeInstance();
	
	/**
	 * Generates a new {@link LinkModificationSystemInstance} with the input options.
	 * 
	 * @param options {@link LinkModificationSystemInstanceOptions}. Can be {@code null}.
	 * @return {@link LinkModificationSystemInstance}. Never {@code null}.
	 */
	public LinkModificationSystemInstance makeInstance(LinkModificationSystemInstanceOptions options);

}
