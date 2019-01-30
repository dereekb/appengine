package com.dereekb.gae.utilities.query.exception;

import java.util.Collection;

import com.dereekb.gae.web.api.exception.ApiResponseErrorConvertable;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.util.attribute.InvalidAttribute;
import com.dereekb.gae.web.api.util.attribute.KeyedInvalidAttribute;
import com.dereekb.gae.web.api.util.attribute.MultiKeyedInvalidAttributes;
import com.dereekb.gae.web.api.util.attribute.builder.KeyedInvalidAttributeApiResponseBuilder;
import com.dereekb.gae.web.api.util.attribute.impl.MultiKeyedInvalidAttributesImpl;

/**
 * Thrown when one or more attributes are invalid.
 * <p>
 * Wraps an {@link MultiKeyedInvalidAttributes}.
 *
 * @author dereekb
 *
 */
public class IllegalQueryAttributesException extends IllegalQueryArgumentException
        implements MultiKeyedInvalidAttributes, ApiResponseErrorConvertable {

	private static final long serialVersionUID = 1L;

	private MultiKeyedInvalidAttributes keyedInvalidAttributes;

	public IllegalQueryAttributesException(InvalidAttribute invalidAttribute) {
		this(new MultiKeyedInvalidAttributesImpl(invalidAttribute));
	}

	public IllegalQueryAttributesException(KeyedInvalidAttribute keyedInvalidAttribute) {
		this(new MultiKeyedInvalidAttributesImpl(keyedInvalidAttribute));
	}

	public IllegalQueryAttributesException(MultiKeyedInvalidAttributes keyedInvalidAttributes) {
		this.setKeyedInvalidAttributes(keyedInvalidAttributes);
	}

	public MultiKeyedInvalidAttributes getKeyedInvalidAttributes() {
		return this.keyedInvalidAttributes;
	}

	public void setKeyedInvalidAttributes(MultiKeyedInvalidAttributes keyedInvalidAttributes) {
		if (keyedInvalidAttributes == null) {
			throw new IllegalArgumentException("keyedInvalidAttributes cannot be null.");
		}

		this.keyedInvalidAttributes = keyedInvalidAttributes;
	}

	// MARK: MultiKeyedInvalidAttributes
	@Override
	public Collection<? extends KeyedInvalidAttribute> getInvalidAttributes() {
		return this.keyedInvalidAttributes.getInvalidAttributes();
	}

	// MARK: ApiSafeRuntimeException
	@Override
	public ApiResponseError asResponseError() {
		return KeyedInvalidAttributeApiResponseBuilder.make(this);
	}

	@Override
	public String toString() {
		return "IllegalQueryAttributesException [keyedInvalidAttributes=" + this.keyedInvalidAttributes + "]";
	}

}
