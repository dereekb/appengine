package com.dereekb.gae.client.api.model.crud.builder.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.error.ClientResponseError;
import com.dereekb.gae.client.api.service.response.error.ClientResponseErrorInfo;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.web.api.util.attribute.KeyedInvalidAttribute;
import com.dereekb.gae.web.api.util.attribute.builder.KeyedInvalidAttributeApiResponseBuilder;
import com.dereekb.gae.web.api.util.attribute.impl.KeyedInvalidAttributeData;
import com.dereekb.gae.web.api.util.attribute.impl.KeyedInvalidAttributeImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
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
public abstract class AbstractClientModelEditRequestSender<T extends UniqueModel, O, R, S> extends AbstractClientModelCrudRequestSender<T, O, R, S> {

	public AbstractClientModelEditRequestSender(String type,
	        Class<O> dtoType,
	        BidirectionalConverter<T, O> dtoReader,
	        TypeModelKeyConverter keyTypeConverter,
	        SecuredClientApiRequestSender requestSender) throws IllegalArgumentException {
		super(type, dtoType, dtoReader, keyTypeConverter, requestSender);
	}

	public AbstractClientModelEditRequestSender(String type,
	        String pathFormat,
	        Class<O> dtoType,
	        BidirectionalConverter<T, O> dtoReader,
	        TypeModelKeyConverter keyTypeConverter,
	        SecuredClientApiRequestSender requestSender) throws IllegalArgumentException {
		super(type, pathFormat, dtoType, dtoReader, keyTypeConverter, requestSender);
	}

	// MARK: Utility
	public List<KeyedInvalidAttribute> serializeFailures(ClientApiResponse response) {
		List<KeyedInvalidAttribute> failures = null;

		ClientResponseError error = response.getError();
		Map<String, ClientResponseErrorInfo> errorInfoMap = error.getErrorInfoMap();
		ClientResponseErrorInfo failedPairsInfo = errorInfoMap.get(KeyedInvalidAttributeApiResponseBuilder.ERROR_CODE);

		if (failedPairsInfo != null) {
			failures = this.serializeFailuresFromErrorInfoData(failedPairsInfo);
		} else {
			failures = Collections.emptyList();
		}

		return failures;
	}

	public List<KeyedInvalidAttribute> serializeFailuresFromErrorInfoData(ClientResponseErrorInfo keysErrorInfo) {
		JsonNode pairsData = keysErrorInfo.getErrorData();
		return this.serializeFailures(pairsData);
	}

	public List<KeyedInvalidAttribute> serializeFailures(JsonNode keysArrayNode) {
		List<KeyedInvalidAttribute> failures = new ArrayList<KeyedInvalidAttribute>();

		TypeModelKeyConverter typeKeyConverter = this.getKeyTypeConverter();
		StringModelKeyConverter keyConverter = typeKeyConverter.getConverterForType(this.getType());
		ObjectMapper mapper = this.getObjectMapper();

		try {
			KeyedInvalidAttributeData[] data = mapper.treeToValue(keysArrayNode, KeyedInvalidAttributeData[].class);

			for (KeyedInvalidAttributeData entry : data) {
				String stringKey = entry.getKey();
				ModelKey key = keyConverter.convertSingle(stringKey);

				KeyedInvalidAttributeImpl failure = new KeyedInvalidAttributeImpl(key, entry);
				failures.add(failure);
			}
		} catch (JsonProcessingException e) {
			throw new ClientResponseSerializationException(e);
		}

		return failures;
	}

}
