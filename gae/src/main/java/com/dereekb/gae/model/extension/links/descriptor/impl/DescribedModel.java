package com.dereekb.gae.model.extension.links.descriptor.impl;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;

/**
 * Interface for models that are described by another model. Have a
 * {@link Descriptor} that contains description information.
 *
 * @author dereekb
 */
public interface DescribedModel {

	public static final String DEFAULT_LINK_TYPE = "INFO_LINK";

	/**
	 * {@link Descriptor} describing the information link for this type.
	 *
	 * @return {@link Descriptor} instance. Null if no relation is set.
	 */
	public Descriptor getDescriptor();

	/**
	 * Sets the new link/relation for this {@link DescribedModel}.
	 *
	 * @param descriptor
	 *            {@link Descriptor} describing the link information. Pass null
	 *            to clear the value.
	 */
	public void setDescriptor(Descriptor descriptor);

}
