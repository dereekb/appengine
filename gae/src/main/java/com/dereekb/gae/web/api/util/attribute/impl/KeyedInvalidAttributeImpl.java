package com.dereekb.gae.web.api.util.attribute.impl;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.util.attribute.InvalidAttribute;
import com.dereekb.gae.web.api.util.attribute.KeyedInvalidAttribute;

/**
 * {@link KeyedInvalidAttribute} implementation.
 * 
 * @author dereekb
 *
 */
public class KeyedInvalidAttributeImpl extends InvalidAttributeImpl
        implements KeyedInvalidAttribute {

	private ModelKey modelKey;

	public KeyedInvalidAttributeImpl(KeyedInvalidAttribute failure) {
		this(failure.keyValue(), failure);
	}

	public KeyedInvalidAttributeImpl(UniqueModel template, InvalidAttribute failure) {
		this(template.getModelKey(), failure.getAttribute(), failure.getValue(), failure.getDetail());
	}

	public KeyedInvalidAttributeImpl(UniqueModel template, String attribute, String value, String detail) {
		this(template.getModelKey(), attribute, value, detail);
	}

	public KeyedInvalidAttributeImpl(ModelKey templateKey, String attribute, String value, String detail) {
		super(attribute, value, detail);
		this.setModelKey(templateKey);
	}

	public ModelKey getModelKey() {
		return this.modelKey;
	}

	public void setModelKey(ModelKey modelKey) {
		if (modelKey == null) {
			throw new IllegalArgumentException("ModelKey cannot be null.");
		}

		this.modelKey = modelKey;
	}

	// MARK: AlwaysKeyed
	@Override
	public ModelKey keyValue() {
		return this.modelKey;
	}

}
