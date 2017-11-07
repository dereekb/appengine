package com.dereekb.gae.client.api.model.exception;

import java.util.List;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.web.api.util.attribute.KeyedInvalidAttribute;

/**
 * {@link ClientRequestFailureException} that lists all
 * {@link KeyedInvalidAttribute} values.
 * 
 * @author dereekb
 *
 */
public class ClientKeyedInvalidAttributeException extends ClientRequestFailureException {

	private static final long serialVersionUID = 1L;

	private List<KeyedInvalidAttribute> invalidAttributes;

	public ClientKeyedInvalidAttributeException(List<KeyedInvalidAttribute> invalidAttributes,
	        ClientApiResponse clientResponse) {
		super(clientResponse);
		this.invalidAttributes = invalidAttributes;
	}

	public ClientKeyedInvalidAttributeException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ClientKeyedInvalidAttributeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientKeyedInvalidAttributeException(String message) {
		super(message);
	}

	public ClientKeyedInvalidAttributeException(Throwable cause) {
		super(cause);
	}

	public List<KeyedInvalidAttribute> getInvalidAttributes() {
		return this.invalidAttributes;
	}

	public void setInvalidAttributes(List<KeyedInvalidAttribute> invalidAttributes) {
		this.invalidAttributes = invalidAttributes;
	}

	@Override
	public String toString() {
		return "ClientKeyedInvalidAttributeException [invalidAttributes=" + this.invalidAttributes + "]";
	}

}
