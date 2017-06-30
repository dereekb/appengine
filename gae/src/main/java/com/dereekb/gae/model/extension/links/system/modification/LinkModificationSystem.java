package com.dereekb.gae.model.extension.links.system.modification;

/**
 * Thread-safe system that safely performs changes on models.
 * <p>
 * Acts as a factory for {@link LinkModificationSystemInstance}.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystem {

	/**
	 * Generates a new {@link LinkModificationSystemInstance}.
	 * 
	 * @return {@link LinkModificationSystemInstance}. Never {@code null}.
	 */
	public LinkModificationSystemInstance makeInstance();

}
