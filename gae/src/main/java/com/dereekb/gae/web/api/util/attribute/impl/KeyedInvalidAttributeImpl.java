package com.dereekb.gae.web.api.util.attribute.impl;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.web.error.ErrorInfo;
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
		this(template.getModelKey(), failure.getAttribute(), failure.getValue(), failure.getDetail(), failure.getCode(), failure.getError());
	}

	public KeyedInvalidAttributeImpl(UniqueModel template, String attribute, String value, String detail) {
		this(template.getModelKey(), attribute, value, detail);
	}

	public KeyedInvalidAttributeImpl(ModelKey templateKey, String attribute, String value, String detail) {
		this(templateKey, attribute, value, detail, null);
	}
	
	public KeyedInvalidAttributeImpl(ModelKey templateKey, String attribute, String value, String detail, String code) {
		this(templateKey, attribute, value, detail, code, null);
	}

	public KeyedInvalidAttributeImpl(ModelKey templateKey, String attribute, String value, String detail, String code, ErrorInfo error) {
		super(attribute, value, detail, code, error);
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
