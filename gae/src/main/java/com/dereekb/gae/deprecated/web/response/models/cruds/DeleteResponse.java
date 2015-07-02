package com.thevisitcompany.gae.deprecated.web.response.models.cruds;

import java.util.List;

import com.thevisitcompany.gae.deprecated.web.response.models.ApiIdentifierResponse;

public class DeleteResponse<K> extends ApiIdentifierResponse<K> {

	public DeleteResponse() {
		super();
	}

	public DeleteResponse(K identifier) {
		super(identifier);
	}

	public DeleteResponse(List<K> identifiers) {
		super(identifiers);
	}

}
