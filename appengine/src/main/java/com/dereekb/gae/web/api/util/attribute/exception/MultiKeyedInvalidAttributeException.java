package com.dereekb.gae.web.api.util.attribute.exception;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.web.api.exception.ApiSafeRuntimeException;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.util.attribute.InvalidAttribute;
import com.dereekb.gae.web.api.util.attribute.KeyedInvalidAttribute;
import com.dereekb.gae.web.api.util.attribute.MultiKeyedInvalidAttributes;
import com.dereekb.gae.web.api.util.attribute.builder.KeyedInvalidAttributeApiResponseBuilder;
import com.dereekb.gae.web.api.util.attribute.impl.MultiKeyedInvalidAttributesImpl;

/**
 * Exception that contains one or more {@link KeyedInvalidAttribute}
 * instances.
 * <p>
 * Wraps an {@link MultiKeyedInvalidAttributes}.
 *
 * @author dereekb
 *
 * @see KeyedInvalidAttributeException
 */
public class MultiKeyedInvalidAttributeException extends ApiSafeRuntimeException
        implements MultiKeyedInvalidAttributes {

	private static final long serialVersionUID = 1L;

	private MultiKeyedInvalidAttributes keyedInvalidAttributes;

	public MultiKeyedInvalidAttributeException(InvalidAttribute invalidAttribute) {
		this(new MultiKeyedInvalidAttributesImpl(invalidAttribute));
	}

	public MultiKeyedInvalidAttributeException(KeyedInvalidAttribute keyedInvalidAttribute) {
		this(new MultiKeyedInvalidAttributesImpl(keyedInvalidAttribute));
	}

	public MultiKeyedInvalidAttributeException(List<? extends KeyedInvalidAttribute> failures) {
		this(new MultiKeyedInvalidAttributesImpl(failures));
	}

	public MultiKeyedInvalidAttributeException(MultiKeyedInvalidAttributes keyedInvalidAttributes) {
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
		return "MultiKeyedInvalidAttributeException [keyedInvalidAttributes=" + this.keyedInvalidAttributes + "]";
	}

}
