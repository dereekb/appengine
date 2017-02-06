package com.thevisitcompany.gae.deprecated.web.api.models.base.cruds.delete;

import java.util.List;

import com.thevisitcompany.gae.deprecated.web.api.ApiRequest;

/**
 * Request for creating elements.
 *
 * @author dereekb
 *
 * @param <T>
 */
public class DeleteRequest<K> extends ApiRequest<K>> {

	public DeleteRequest() {
		super();
	}

	public DeleteRequest(List<K> data, String type) {
		super(data, type);
	}

	public DeleteRequest(List<K> data) {
		super(data);
	}

	public DeleteRequest(String type) {
		super(type);
	}

	public List<K> getIdentifiers() {
		return this.getData();
	}

}
