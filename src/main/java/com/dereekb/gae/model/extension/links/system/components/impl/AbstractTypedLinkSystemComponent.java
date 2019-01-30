package com.dereekb.gae.model.extension.links.system.components.impl;

import com.dereekb.gae.model.extension.links.system.components.TypedLinkSystemComponent;

/**
 * Abstract {@link TypedLinkSystemComponent} implementation.
 * 
 * @author dereekb
 *
 */
public abstract class AbstractTypedLinkSystemComponent
        implements TypedLinkSystemComponent {

	private final String linkModelType;

	public AbstractTypedLinkSystemComponent(String linkModelType) {
		if (linkModelType == null) {
			throw new IllegalArgumentException("LinkModelType cannot be null.");
		}

		this.linkModelType = linkModelType;
	}

	@Override
	public String getLinkModelType() {
		return this.linkModelType;
	}

}
