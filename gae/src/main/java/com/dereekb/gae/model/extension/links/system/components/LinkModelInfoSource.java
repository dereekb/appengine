package com.dereekb.gae.model.extension.links.system.components;

/**
 * Source for a {@link LinkModelInfo} for a specific type.
 * 
 * @author dereekb
 */
public interface LinkModelInfoSource
        extends TypedLinkSystemComponent {

	/**
	 * Loads the {@link LinkModelInfo} for a type.
	 * 
	 * @return {@link LinkModelInfo}. Never {@code null}.
	 */
	public LinkModelInfo loadLinkModelInfo();

}
