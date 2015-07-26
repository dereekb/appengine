package com.dereekb.gae.web.api.model.controller.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.ReadRequestOptions;
import com.dereekb.gae.model.crud.services.request.impl.KeyReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.model.controller.ReadModelControllerConversionDelegate;
import com.dereekb.gae.web.api.model.exception.MissingRequiredResourceException;
import com.dereekb.gae.web.api.shared.exception.RequestArgumentException;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;


/**
 * {@link ReadModelControllerConversionDelegate} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <I>
 *            api output type
 */
public final class ReadModelControllerConversionDelegateImpl<T extends UniqueModel, I>
        implements ReadModelControllerConversionDelegate<T> {

	private final String type;

	private final DirectionalConverter<String, ModelKey> keyReader;
	private final DirectionalConverter<T, I> converter;

	private boolean appendUnavailable = true;

	public ReadModelControllerConversionDelegateImpl(String type,
	        DirectionalConverter<String, ModelKey> keyReader,
	        DirectionalConverter<T, I> converter) {
		this.type = type;
		this.keyReader = keyReader;
		this.converter = converter;
	}

	public boolean isAppendUnavailable() {
		return this.appendUnavailable;
	}

	public void setAppendUnavailable(boolean appendUnavailable) {
		this.appendUnavailable = appendUnavailable;
	}

	public String getType() {
		return this.type;
	}

	public DirectionalConverter<String, ModelKey> getKeyReader() {
		return this.keyReader;
	}

	public DirectionalConverter<T, I> getConverter() {
		return this.converter;
	}

	@Override
	public ReadRequest convert(List<String> ids) {
		List<ModelKey> keys = null;
		ReadRequestOptions options = new ReadRequestOptions();

		try {
			keys = this.keyReader.convert(ids);
		} catch (ConversionFailureException e) {
			throw new RequestArgumentException("data", "Failed to convert identifiers.");
		}

		ReadRequest request = new KeyReadRequest(keys, options);
		return request;
	}

	@Override
	public ApiResponse convert(ReadResponse<T> response) {

		Collection<T> models = response.getModels();
		List<I> converted = this.converter.convert(models);

		ApiResponse apiResponse = new ApiResponse();
		ApiResponseData data = new ApiResponseData(this.type, converted);

		apiResponse.setData(data);

		if (this.appendUnavailable) {
			List<ModelKey> missing = new ArrayList<ModelKey>();
			missing.addAll(response.getFiltered());
			missing.addAll(response.getUnavailable());

			if (missing.size() != 0) {
				List<String> missingKeys = ModelKey.keysAsStrings(missing);
				String message = "Some requested models are unavailable.";
				ApiResponseError error = MissingRequiredResourceException.makeApiError(missingKeys, message);
				apiResponse.addError(error);
			}
		}

		return apiResponse;
	}

}
