package com.dereekb.gae.web.api.model.extension.search.impl.model;

import java.util.Map;

import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.search.document.search.service.model.impl.AbstractModelDocumentRequest;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.RawExpression;
import com.dereekb.gae.web.api.model.extension.search.ApiSearchReadRequest;


public abstract class AbstractSearchRequestBuilder<R extends AbstractModelDocumentRequest>
        implements SingleDirectionalConverter<ApiSearchReadRequest, R> {

	private Class<? extends R> requestType;

	protected AbstractSearchRequestBuilder(Class<? extends R> requestType) {
		if (requestType == null) {
			throw new IllegalArgumentException();
		}

		this.requestType = requestType;
	}

	@Override
    public R convertSingle(ApiSearchReadRequest input) throws ConversionFailureException {

		R request;

        try {
	        request = this.requestType.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
	        throw new RuntimeException(e);
        }

		Map<String, String> parameters = input.getParameters();
		String query = input.getQuery();

		if (query != null) {
			request.setOverride(new RawExpression(query));
		} else {
			this.applyParameters(request, parameters);
		}

		request.setLimit(input.getLimit());
		request.setCursor(input.getCursor());

		return request;
    }

	public abstract void applyParameters(R request,
	                                     Map<String, String> parameters);

}
