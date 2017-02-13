package com.dereekb.gae.web.api.util.attribute.exception;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.util.attribute.AttributeUpdateFailure;
import com.dereekb.gae.web.api.util.attribute.KeyedAttributeUpdateFailure;
import com.dereekb.gae.web.api.util.attribute.impl.KeyedAttributeUpdateFailureImpl;

/**
 * {@link KeyedAttributeUpdateFailure} exception.
 * 
 * @author dereekb
 *
 */
public class KeyedAttributeUpdateFailureException extends AttributeUpdateFailureException
        implements KeyedAttributeUpdateFailure {

	private static final long serialVersionUID = 1L;

	private final KeyedAttributeUpdateFailure failure;

	public KeyedAttributeUpdateFailureException(UniqueModel template, AttributeUpdateFailure failure) {
		this(template.getModelKey(), failure.getAttribute(), failure.getValue(), failure.getDetail());
	}

	public KeyedAttributeUpdateFailureException(UniqueModel template, String attribute, String value, String detail) {
		this(template.getModelKey(), attribute, value, detail);
	}

	public KeyedAttributeUpdateFailureException(ModelKey templateKey, String attribute, String value, String detail) {
		this(new KeyedAttributeUpdateFailureImpl(templateKey, attribute, value, detail));
	}

	protected KeyedAttributeUpdateFailureException(KeyedAttributeUpdateFailure failure) {
		super(failure);
		this.failure = failure;
	}

	// MARK: KeyedAttributeUpdateFailure
	@Override
	public UniqueModel keyValue() {
		return this.failure.keyValue();
	}

}
