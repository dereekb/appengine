package com.dereekb.gae.client.api.model.crud.builder.impl;

import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;

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

}
