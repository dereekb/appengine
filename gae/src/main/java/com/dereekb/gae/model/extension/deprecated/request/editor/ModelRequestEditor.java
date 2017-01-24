package com.dereekb.gae.model.extension.request.editor;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.deprecated.request.Request;
import com.dereekb.gae.model.extension.deprecated.request.builder.RequestBuilder;
import com.dereekb.gae.model.extension.deprecated.request.key.RequestKeyBuilder;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.utility.ConfiguredDeleter;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;

/**
 * Default implementation for {@link RequestEditor}.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model Type.
 */
public final class ModelRequestEditor<T>
        implements RequestEditor<T> {

	private final RequestBuilder<T> requestBuilder;
	private final RequestKeyBuilder<T> keyBuilder;
	private final ConfiguredSetter<Request> requestSetter;
	private final ConfiguredDeleter requestDeleter;

	public ModelRequestEditor(RequestBuilder<T> requestBuilder,
	        RequestKeyBuilder<T> keyBuilder,
	        ConfiguredSetter<Request> requestSetter,
	        ConfiguredDeleter requestDeleter) {
		this.requestBuilder = requestBuilder;
		this.keyBuilder = keyBuilder;
		this.requestSetter = requestSetter;
		this.requestDeleter = requestDeleter;
	}

	@Override
	public void makeRequests(Iterable<T> objects) throws RequestChangeException {
		List<Request> requests = new ArrayList<Request>();

		for (T object : objects) {
			Request request = this.requestBuilder.buildRequest(object);
			requests.add(request);
		}

		this.requestSetter.save(requests);
	}

	@Override
	public void deleteRequests(Iterable<T> objects) throws RequestChangeException {
		List<ModelKey> requestKeys = new ArrayList<ModelKey>();

		for (T object : objects) {
			String requestKey = this.keyBuilder.requestKeyForObject(object);
			ModelKey requestModelKey = new ModelKey(requestKey);
			requestKeys.add(requestModelKey);
		}

		this.requestDeleter.deleteWithKeys(requestKeys);
	}

}
