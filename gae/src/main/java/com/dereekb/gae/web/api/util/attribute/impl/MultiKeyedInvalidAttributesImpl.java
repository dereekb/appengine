package com.dereekb.gae.web.api.util.attribute.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.web.api.util.attribute.InvalidAttribute;
import com.dereekb.gae.web.api.util.attribute.KeyedInvalidAttribute;
import com.dereekb.gae.web.api.util.attribute.MultiKeyedInvalidAttributes;

/**
 * {@link MultiKeyedInvalidAttributes} implementation.
 *
 * @author dereekb
 *
 */
public class MultiKeyedInvalidAttributesImpl
        implements MultiKeyedInvalidAttributes {

	private List<? extends KeyedInvalidAttribute> attributes;

	public MultiKeyedInvalidAttributesImpl(InvalidAttribute invalidAttribute) {
		this(new KeyedInvalidAttributeImpl(invalidAttribute));
	}

	public MultiKeyedInvalidAttributesImpl(KeyedInvalidAttribute keyedInvalidAttribute) {
		this(ListUtility.wrap(keyedInvalidAttribute));
	}

	public MultiKeyedInvalidAttributesImpl(List<? extends KeyedInvalidAttribute> attributes) {
		this.setAttributes(attributes);
	}

	public List<? extends KeyedInvalidAttribute> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(Collection<? extends KeyedInvalidAttribute> attributes) {
		if (attributes == null) {
			throw new IllegalArgumentException("attributes cannot be null.");
		}

		this.attributes = ListUtility.copy(attributes);
	}

	// MARK: MultiKeyedInvalidAttributes
	@Override
	public Collection<? extends KeyedInvalidAttribute> getInvalidAttributes() {
		return this.attributes;
	}

	@Override
	public String toString() {
		return "MultiKeyedInvalidAttributesImpl [attributes=" + this.attributes + "]";
	}

}
