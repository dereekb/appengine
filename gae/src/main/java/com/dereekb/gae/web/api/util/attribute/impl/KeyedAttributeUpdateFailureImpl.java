package com.dereekb.gae.web.api.util.attribute.impl;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.util.attribute.AttributeUpdateFailure;
import com.dereekb.gae.web.api.util.attribute.KeyedAttributeUpdateFailure;

/**
 * {@link KeyedAttributeUpdateFailure} implementation.
 * 
 * @author dereekb
 *
 */
public class KeyedAttributeUpdateFailureImpl extends AttributeUpdateFailureImpl
        implements KeyedAttributeUpdateFailure {

	private ModelKey modelKey;

	public KeyedAttributeUpdateFailureImpl(KeyedAttributeUpdateFailure failure) {
		this(failure.keyValue(), failure);
	}

	public KeyedAttributeUpdateFailureImpl(UniqueModel template, AttributeUpdateFailure failure) {
		this(template.getModelKey(), failure.getAttribute(), failure.getValue(), failure.getDetail());
	}

	public KeyedAttributeUpdateFailureImpl(UniqueModel template, String attribute, String value, String detail) {
		this(template.getModelKey(), attribute, value, detail);
	}

	public KeyedAttributeUpdateFailureImpl(ModelKey templateKey, String attribute, String value, String detail) {
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
