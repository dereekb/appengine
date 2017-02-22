package com.dereekb.gae.web.api.util.attribute.exception;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.util.attribute.InvalidAttribute;
import com.dereekb.gae.web.api.util.attribute.KeyedInvalidAttribute;
import com.dereekb.gae.web.api.util.attribute.builder.KeyedInvalidAttributeApiResponseBuilder;
import com.dereekb.gae.web.api.util.attribute.impl.KeyedInvalidAttributeImpl;

/**
 * {@link KeyedInvalidAttribute} exception.
 * 
 * @author dereekb
 *
 */
public class KeyedInvalidAttributeException extends InvalidAttributeException
        implements KeyedInvalidAttribute {

	private static final long serialVersionUID = 1L;

	private final KeyedInvalidAttribute failure;

	public KeyedInvalidAttributeException(UniqueModel template, InvalidAttribute failure) {
		this(template.getModelKey(), failure.getAttribute(), failure.getValue(), failure.getDetail());
	}

	public KeyedInvalidAttributeException(UniqueModel template, String attribute, String value, String detail) {
		this(template.getModelKey(), attribute, value, detail);
	}

	public KeyedInvalidAttributeException(ModelKey templateKey, String attribute, String value, String detail) {
		this(new KeyedInvalidAttributeImpl(templateKey, attribute, value, detail));
	}

	protected KeyedInvalidAttributeException(KeyedInvalidAttribute failure) {
		super(failure);
		this.failure = failure;
	}

	// MARK: KeyedInvalidAttribute
	@Override
	public UniqueModel keyValue() {
		return this.failure.keyValue();
	}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		return KeyedInvalidAttributeApiResponseBuilder.make(this);
	}

}
