package com.dereekb.gae.web.api.model.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.Max;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.inclusion.reader.InclusionReaderSetAnalysis;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.web.api.model.controller.impl.ReadControllerEntryRequestImpl;
import com.dereekb.gae.web.api.model.exception.ApiRuntimeException;
import com.dereekb.gae.web.api.model.exception.MissingRequiredResourceException;
import com.dereekb.gae.web.api.model.exception.UnregisteredTypeException;
import com.dereekb.gae.web.api.model.exception.resolver.AtomicOperationFailureResolver;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseDataImpl;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * API used for reading models.
 *
 * @author dereekb
 *
 */
@RestController
public class ReadController {

	private boolean appendUnavailable = true;

	private TypeModelKeyConverter keyTypeConverter;
	private Map<String, ReadControllerEntry> entries;

	public TypeModelKeyConverter getKeyTypeConverter() {
		return this.keyTypeConverter;
	}

	public void setKeyTypeConverter(TypeModelKeyConverter keyTypeConverter) {
		this.keyTypeConverter = keyTypeConverter;
	}

	public Map<String, ReadControllerEntry> getEntries() {
		return this.entries;
	}

	public void setEntries(Map<String, ReadControllerEntry> entries) {
		this.entries = entries;
	}

	// MARK: Controller
	/**
	 * API Entry point for reading a value.
	 *
	 * @param ids
	 *            Identifiers to read. Max of 40. Never {@code null}.
	 * @param atomic
	 *            Whether or not to perform an atomic read request. False by
	 *            default.
	 * @param getRelated
	 *            Whether or not to retrieve related values. False by default.
	 *            If {@code related} is not empty or {@code null}, this value is
	 *            set to {@code true}.
	 * @param related
	 *            Inclusive filter of related elements to load.
	 * @return {@link ApiResponse}
	 */
	@ResponseBody
	@PreAuthorize("hasPermission(this, 'read')")
	@RequestMapping(value = "/{type}", method = RequestMethod.GET, produces = "application/json")
	public ApiResponse readModels(@PathVariable("type") String modelType,
	                       @Max(40) @RequestParam(required = true) List<String> ids,
	                       @RequestParam(required = false, defaultValue = "false") boolean atomic,
	                              @RequestParam(required = false, defaultValue = "false") boolean loadRelated,
	                              @RequestParam(required = false) Set<String> relatedTypes) {

		ApiResponseImpl response = null;
		ReadControllerEntry entry = this.getEntryForType(modelType);

		try {
			List<ModelKey> modelKeys = this.keyTypeConverter.convertKeys(modelType, ids);

			ReadControllerEntryRequestImpl request = new ReadControllerEntryRequestImpl(modelType, atomic, modelKeys);
			request.setLoadRelatedTypes(loadRelated);
			request.setRelatedTypesFilter(relatedTypes);

			ReadControllerEntryResponse readResponse = entry.read(request);
			response = this.buildApiResponse(request, readResponse);
		} catch (AtomicOperationException e) {
			AtomicOperationFailureResolver.resolve(e);
		} catch (RuntimeException e) {
			throw new ApiRuntimeException(e);
		}

		return response;
	}

	private ApiResponseImpl buildApiResponse(ReadControllerEntryRequest entryRequest,
	                                         ReadControllerEntryResponse entryResponse) {
		String modelType = entryRequest.getModelType();
		ApiResponseImpl response = new ApiResponseImpl();

		Collection<Object> objects = entryResponse.getResponseModels();
		ApiResponseDataImpl data = new ApiResponseDataImpl(modelType, objects);
		response.setData(data);

		// Append Missing
		if (this.appendUnavailable) {
			Collection<ModelKey> missing = entryResponse.getUnavailableModelKeys();

			if (missing.size() != 0) {
				List<String> missingKeys = ModelKey.keysAsStrings(missing);
				String message = "Some requested models are unavailable.";
				ApiResponseErrorImpl error = MissingRequiredResourceException.makeApiError(missingKeys, message);
				response.addError(error);
			}
		}

		// Append Related
		InclusionReaderSetAnalysis analysis = entryResponse.getAnalysis();

		if (analysis != null) {
			// TODO: ...
		}

		return response;
	}

	private ReadControllerEntry getEntryForType(String modelType) throws UnregisteredTypeException {
		ReadControllerEntry entry = this.entries.get(modelType);

		if (entry == null) {
			throw new UnregisteredTypeException();
		}

		return entry;
	}

}
