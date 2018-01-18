package com.dereekb.gae.client.api.model.crud.builder.impl;

import java.util.List;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.exception.ClientKeyedInvalidAttributeException;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.error.ClientApiResponseErrorType;
import com.dereekb.gae.client.api.service.response.error.ClientResponseError;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.web.api.util.attribute.KeyedInvalidAttribute;

/**
 * {@link AbstractClientModelCrudRequestSender} extension used for template
 * requests, such as Create and Update.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <O>
 *            dto type
 * @param <R>
 *            request type
 * @param <S>
 *            response type
 */
public abstract class AbstractClientModelTemplateRequestSender<T extends UniqueModel, O, R, S> extends AbstractClientModelCrudRequestSender<T, O, R, S> {

	public AbstractClientModelTemplateRequestSender(String type,
	        Class<O> dtoType,
	        BidirectionalConverter<T, O> dtoReader,
	        TypeModelKeyConverter keyTypeConverter,
	        SecuredClientApiRequestSender requestSender) throws IllegalArgumentException {
		super(type, dtoType, dtoReader, keyTypeConverter, requestSender);
	}

	public AbstractClientModelTemplateRequestSender(String type,
	        String pathFormat,
	        Class<O> dtoType,
	        BidirectionalConverter<T, O> dtoReader,
	        TypeModelKeyConverter keyTypeConverter,
	        SecuredClientApiRequestSender requestSender) throws IllegalArgumentException {
		super(type, pathFormat, dtoType, dtoReader, keyTypeConverter, requestSender);
	}

	// MARK: Override
	@Override
	public void assertSuccessfulResponse(ClientApiResponse clientResponse) throws ClientRequestFailureException {
		if (clientResponse.isSuccessful() == false) {
			this.assertNoInvalidAttributes(clientResponse);
			super.assertSuccessfulResponse(clientResponse);
		}
	}

	public void assertNoInvalidAttributes(ClientApiResponse clientResponse)
	        throws ClientKeyedInvalidAttributeException {
		ClientResponseError error = clientResponse.getError();

		if (error.getErrorType() == ClientApiResponseErrorType.OTHER_BAD_RESPONSE_ERROR) {
			List<KeyedInvalidAttribute> invalidAttributes = this.serializeInvalidAttributes(clientResponse);

			if (invalidAttributes.isEmpty() == false) {
				throw new ClientKeyedInvalidAttributeException(invalidAttributes, clientResponse);
			}
		}
	}

	// MARK: Utility
	public List<KeyedInvalidAttribute> serializeInvalidAttributes(ClientApiResponse response) {
		return ClientKeyedInvalidAttributeException
		        .utility(this.getKeyTypeConverter(), this.getObjectMapper(), this.getType())
		        .serializeInvalidAttributes(response);
	}

}
