package com.dereekb.gae.web.api.model.crud.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.dereekb.gae.utilities.collections.map.impl.CaseInsensitiveEntryContainerImpl;
import com.dereekb.gae.web.api.exception.ApiIllegalArgumentException;
import com.dereekb.gae.web.api.exception.resolver.RuntimeExceptionResolver;
import com.dereekb.gae.web.api.model.crud.impl.ReadControllerEntryRequestImpl;
import com.dereekb.gae.web.api.model.exception.MissingRequiredResourceException;
import com.dereekb.gae.web.api.model.exception.TooManyRequestKeysException;
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
public class ReadController extends CaseInsensitiveEntryContainerImpl<ReadControllerEntry> {

	public static final String ATOMIC_PARAM = "atomic";
	public static final String LOAD_RELATED_PARAM = "getRelated";
	public static final String RELATED_FILTER_PARAM = "relatedFilter";
	public static final String KEYS_PARAM = "keys";

	public static final int MAX_KEYS_PER_REQUEST = 40;

	private boolean appendUnavailable = true;

	private Integer maxKeysAllowed = MAX_KEYS_PER_REQUEST;

	private TypeModelKeyConverter keyTypeConverter;

	public ReadController() {
		super();
	}

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

	public Integer getMaxKeysAllowed() {
		return this.maxKeysAllowed;
	}

	public void setMaxKeysAllowed(Integer maxKeysAllowed) {
		this.maxKeysAllowed = maxKeysAllowed;
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
	                              @RequestParam(name = KEYS_PARAM, required = true) List<String> keys,
	                              @RequestParam(name = ATOMIC_PARAM, required = false, defaultValue = "false") boolean atomic,
	                              @RequestParam(name = LOAD_RELATED_PARAM, required = false, defaultValue = "false") boolean loadRelated,
	                              @RequestParam(name = RELATED_FILTER_PARAM, required = false) Set<String> relatedTypes)
	        throws TooManyRequestKeysException,
	            UnavailableTypesException {

		TooManyRequestKeysException.assertKeysCount(keys, this.maxKeysAllowed);

		ApiResponseImpl response = null;
		ReadControllerEntry entry = this.getEntryForType(modelType);

		if (keys.isEmpty()) {
			return this.buildNoKeysApiResponse(modelType);
		}

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
	private ApiResponseImpl buildNoKeysApiResponse(String modelType) {
		ApiResponseImpl response = new ApiResponseImpl();
		ApiResponseDataImpl data = new ApiResponseDataImpl(modelType, Collections.emptyList());
		response.setData(data);
		response.addError(new ApiResponseErrorImpl("NO_KEYS", "No Keys"));
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
			} else {
				filteredTypes = Collections.emptySet();
			}

			Set<String> missingTypes = new HashSet<String>();

			for (String type : types) {
				try {
					Set<ModelKey> keys = analysis.getKeysForType(type);

					if (keys.size() > 0) {
						ApiResponseDataImpl inclusionData = this.readRelated(type, keys);
						response.addIncluded(inclusionData);
					}
				} catch (InclusionTypeUnavailableException e) {
					missingTypes.addAll(e.getTypes());
				}
			}

			// Only return an error for items that were requested but aren't
			// available.
			missingTypes.retainAll(filteredTypes);

			if (missingTypes.size() > 0) {
				InclusionTypeUnavailableException exception = new InclusionTypeUnavailableException(missingTypes);
				ApiResponseError error = exception.asResponseError();
				response.addError(error);
			}
		}

		return response;
	}

	private ApiResponseDataImpl readRelated(String modelType,
	                                        Set<ModelKey> keys)
	        throws InclusionTypeUnavailableException {
		ReadControllerEntryResponse response = this.read(modelType, false, keys);
		Collection<Object> models = response.getResponseModels();
		return new ApiResponseDataImpl(modelType, models);
	}

	private ReadControllerEntryResponse read(String modelType,
	                                         boolean atomic,
	                                         Collection<ModelKey> keys)
	        throws InclusionTypeUnavailableException {
		ReadControllerEntry entry = this.getEntryForType(modelType);
		ReadControllerEntryRequestImpl request = new ReadControllerEntryRequestImpl(modelType, atomic, keys);
		request.setLoadRelatedTypes(false);
		return this.read(entry, request);
	}

	private ReadControllerEntryResponse read(ReadControllerEntry entry,
	                                         ReadControllerEntryRequest request) {
		return entry.read(request);
	}

	@Override
	protected void throwEntryDoesntExistException(String type) throws RuntimeException {
		throw new InclusionTypeUnavailableException(type);
	}

	@Override
	public String toString() {
		return "ReadController [appendUnavailable=" + this.appendUnavailable + ", keyTypeConverter="
		        + this.keyTypeConverter + ", entries=" + this.getEntries() + "]";
	}

}
