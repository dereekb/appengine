package com.dereekb.gae.web.api.model.crud.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.Max;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.inclusion.exception.InclusionTypeUnavailableException;
import com.dereekb.gae.model.extension.inclusion.reader.InclusionReaderSetAnalysis;
import com.dereekb.gae.model.extension.read.exception.UnavailableTypesException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;
import com.dereekb.gae.web.api.exception.ApiIllegalArgumentException;
import com.dereekb.gae.web.api.exception.resolver.RuntimeExceptionResolver;
import com.dereekb.gae.web.api.model.crud.impl.ReadControllerEntryRequestImpl;
import com.dereekb.gae.web.api.model.exception.MissingRequiredResourceException;
import com.dereekb.gae.web.api.model.exception.resolver.AtomicOperationFailureResolver;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
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

	public static final String ATOMIC_PARAM = "atomic";
	public static final String LOAD_RELATED_PARAM = "getRelated";
	public static final String RELATED_FILTER_PARAM = "relatedFilter";
	public static final String KEYS_PARAM = "keys";

	public static final long MAX_KEYS_PER_REQUEST = 40;

	private boolean appendUnavailable = true;

	private TypeModelKeyConverter keyTypeConverter;
	private Map<String, ReadControllerEntry> entries;

	public ReadController(TypeModelKeyConverter keyTypeConverter, Map<String, ReadControllerEntry> entries)
	        throws IllegalArgumentException {
		this.setKeyTypeConverter(keyTypeConverter);
		this.setEntries(entries);
	}

	public boolean isAppendUnavailable() {
		return this.appendUnavailable;
	}

	public void setAppendUnavailable(boolean appendUnavailable) {
		this.appendUnavailable = appendUnavailable;
	}

	public TypeModelKeyConverter getKeyTypeConverter() {
		return this.keyTypeConverter;
	}

	public void setKeyTypeConverter(TypeModelKeyConverter keyTypeConverter) throws IllegalArgumentException {
		if (keyTypeConverter == null) {
			throw new IllegalArgumentException();
		}

		this.keyTypeConverter = keyTypeConverter;
	}

	public Map<String, ReadControllerEntry> getEntries() {
		return this.entries;
	}

	public void setEntries(Map<String, ReadControllerEntry> entries) throws IllegalArgumentException {
		if (entries == null) {
			throw new IllegalArgumentException();
		}

		this.entries = new CaseInsensitiveMap<>(entries);
	}

	// MARK: Controller
	/**
	 * API Entry point for reading a value.
	 *
	 * @param keys
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
	@RequestMapping(value = "/{type}", method = RequestMethod.GET, produces = "application/json")
	public ApiResponse readModels(@PathVariable("type") String modelType,
	                              @Max(MAX_KEYS_PER_REQUEST) @RequestParam(name = KEYS_PARAM, required = true) List<String> keys,
	                              @RequestParam(name = ATOMIC_PARAM, required = false, defaultValue = "false") boolean atomic,
	                              @RequestParam(name = LOAD_RELATED_PARAM, required = false, defaultValue = "false") boolean loadRelated,
	                              @RequestParam(name = RELATED_FILTER_PARAM, required = false) Set<String> relatedTypes)
	        throws UnavailableTypesException {

		ApiResponseImpl response = null;
		ReadControllerEntry entry = this.getEntryForType(modelType);

		List<ModelKey> modelKeys = null;

		try {
			modelKeys = this.keyTypeConverter.convertKeys(modelType, keys);
		} catch (ConversionFailureException e) {
			throw new ApiIllegalArgumentException(e);
		}

		try {
			ReadControllerEntryRequestImpl request = new ReadControllerEntryRequestImpl(modelType, atomic, modelKeys);
			request.setLoadRelatedTypes(loadRelated);
			request.setRelatedTypesFilter(relatedTypes);

			ReadControllerEntryResponse readResponse = this.read(entry, request);
			response = this.buildApiResponse(request, readResponse);
		} catch (AtomicOperationException e) {
			AtomicOperationFailureResolver.resolve(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	// MARK: Internal
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
				ApiResponseErrorImpl error = MissingRequiredResourceException.makeApiError(missingKeys,
				        "Some requested models were unavailable.");
				response.addError(error);
			}
		}

		// Append Related
		InclusionReaderSetAnalysis analysis = entryResponse.getAnalysis();

		if (analysis != null) {
			Set<String> filteredTypes = entryRequest.getRelatedTypesFilter();
			Set<String> types = analysis.getRelatedTypes();

			if (filteredTypes != null && filteredTypes.isEmpty() == false) {
				types = filteredTypes;
			}

			try {
				for (String type : types) {
					Set<ModelKey> keys = analysis.getKeysForType(type);

					if (keys.size() > 0) {
						ApiResponseDataImpl inclusionData = this.readRelated(type, keys);
						response.addIncluded(inclusionData);
					}
				}
			} catch (InclusionTypeUnavailableException e) {
				ApiResponseError error = e.asResponseError();
				response.addError(error);
			}
		}

		return response;
	}

	private ApiResponseDataImpl readRelated(String modelType,
	                                        Set<ModelKey> keys)
	        throws UnavailableTypesException {
		ReadControllerEntryResponse response = this.read(modelType, false, keys);
		Collection<Object> models = response.getResponseModels();
		return new ApiResponseDataImpl(modelType, models);
	}

	private ReadControllerEntryResponse read(String modelType,
	                                         boolean atomic,
	                                         Collection<ModelKey> keys)
	        throws UnavailableTypesException {
		ReadControllerEntry entry = this.getEntryForType(modelType);
		ReadControllerEntryRequestImpl request = new ReadControllerEntryRequestImpl(modelType, atomic, keys);
		request.setLoadRelatedTypes(false);
		return this.read(entry, request);
	}

	private ReadControllerEntryResponse read(ReadControllerEntry entry,
	                                         ReadControllerEntryRequest request) {
		return entry.read(request);
	}

	private ReadControllerEntry getEntryForType(String modelType) throws UnavailableTypesException {
		ReadControllerEntry entry = this.entries.get(modelType);

		if (entry == null) {
			throw new UnavailableTypesException(modelType);
		}

		return entry;
	}

	@Override
	public String toString() {
		return "ReadController [appendUnavailable=" + this.appendUnavailable + ", keyTypeConverter="
		        + this.keyTypeConverter + ", entries=" + this.entries + "]";
	}

}
