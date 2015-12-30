package com.dereekb.gae.model.stored.blob.search.document.query;

import java.util.Map;

import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.search.document.search.model.DateSearch;
import com.dereekb.gae.model.extension.search.document.search.model.DescriptorSearch;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.RawExpression;
import com.dereekb.gae.web.api.model.extension.search.ApiSearchReadRequest;


public class StoredBlobSearchRequestBuilder
        implements SingleDirectionalConverter<ApiSearchReadRequest, StoredBlobSearchRequest> {

	@Override
	public StoredBlobSearchRequest convertSingle(ApiSearchReadRequest input) throws ConversionFailureException {

		StoredBlobSearchRequest request = new StoredBlobSearchRequest();

		Map<String, String> parameters = input.getParameters();
		String query = input.getQuery();

		if (query != null) {
			request.setOverride(new RawExpression(query));
		} else {
			request.setName(parameters.get("name"));
			request.setEnding(parameters.get("ending"));
			request.setType(parameters.get("type"));

			request.setDate(DateSearch.fromString(parameters.get("date")));
			request.setDescriptor(DescriptorSearch.fromString(parameters.get("descriptor")));
		}

		request.setLimit(input.getLimit());
		request.setCursor(input.getCursor());

		return request;
	}

}
