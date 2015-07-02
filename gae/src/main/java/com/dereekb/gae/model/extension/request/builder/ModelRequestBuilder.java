package com.dereekb.gae.model.extension.request.builder;

import java.util.HashMap;

import com.dereekb.gae.model.extension.request.Request;
import com.dereekb.gae.model.extension.request.key.RequestKeyBuilder;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Default implementation of {@link RequestBuilder}. Can optionally used a
 * {@link RequestInfoBuilder} for attaching info to requests.
 *
 * @author dereekb
 */
public final class ModelRequestBuilder<T extends UniqueModel>
        implements RequestBuilder<T>, RequestInfoBuilder<T> {

	private final String type;
	private final RequestKeyBuilder<T> keyBuilder;
	private final RequestInfoBuilder<T> infoBuilder;

	public ModelRequestBuilder(String type, RequestKeyBuilder<T> keyBuilder) {
		this.type = type;
		this.keyBuilder = keyBuilder;
		this.infoBuilder = this;
	}

	public ModelRequestBuilder(String type, RequestKeyBuilder<T> keyBuilder, RequestInfoBuilder<T> infoBuilder) {
		this.type = type;
		this.keyBuilder = keyBuilder;
		this.infoBuilder = infoBuilder;
	}

	public RequestInfoBuilder<T> getInfoBuilder() {
		return this.infoBuilder;
	}

	@Override
	public Request buildRequest(T object) {
		Request request = new Request(this.type);

		// Key
		String requestKey = this.keyBuilder.requestKeyForObject(object);
		request.setIdentifier(requestKey);

		// Target
		ModelKey key = object.getModelKey();
		String target = key.keyAsString();
		request.setTarget(target);

		// Info
		HashMap<String, String> info = this.infoBuilder.makeRequestInfo(object);
		request.setInfo(info);

		return request;
	}

	// RequestInfoBuilder
	/**
	 * Function from {@link RequestInfoBuilder}. Will returns null by default.
	 */
	@Override
	public HashMap<String, String> makeRequestInfo(T input) {
		return null;
	}

}
