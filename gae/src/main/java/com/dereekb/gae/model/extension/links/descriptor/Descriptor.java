package com.dereekb.gae.model.extension.links.descriptor;

import com.dereekb.gae.model.extension.links.descriptor.impl.DescribedModel;

/**
 * Used by {@link DescribedModel} types to wrap the descriptive info model's
 * type and identifier.
 *
 * <p>
 * Descriptor types are generally named after the class and starts with a
 * capital letter.
 * </p>
 *
 * @author dereekb
 */
public interface Descriptor {

	/**
	 * Returns the type/class key of this relation.
	 *
	 * @return String type value. Never null.
	 */
	public String getDescriptorType();

	/**
	 * Returns the identifier of this relation.
	 *
	 * @return String representation of the identifier. Never null.
	 */
	public String getDescriptorId();

}
