package com.dereekb.gae.web.api.model.extension.search.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.extension.read.exception.UnavailableTypesException;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;
import com.dereekb.gae.web.api.model.extension.search.ApiSearchDelegate;
import com.dereekb.gae.web.api.model.extension.search.ApiSearchDelegateEntry;
import com.dereekb.gae.web.api.model.extension.search.ApiSearchReadRequest;
import com.dereekb.gae.web.api.model.extension.search.ApiSearchUpdateRequest;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * {@link ApiSearchDelegate} implementation.
 * <p>
 * The entries map is case-insensitive.
 *
 * @author dereekb
 *
 */
public class ApiSearchDelegateImpl
        implements ApiSearchDelegate {

	private Map<String, ApiSearchDelegateEntry> entries;

	public ApiSearchDelegateImpl(Map<String, ApiSearchDelegateEntry> entries) {
		this.setEntries(entries);
	}

	public final Map<String, ApiSearchDelegateEntry> getEntries() {
		return this.entries;
	}

	public final void setEntries(Map<String, ApiSearchDelegateEntry> entries) {
		this.entries = new CaseInsensitiveMap<ApiSearchDelegateEntry>(entries);
	}

	// MARK: Internal
	public Set<String> getUnavailable(Set<String> types) {
		Set<String> unavailable = new HashSet<String>();

		for (String type : types) {
			if (this.entries.containsKey(type) == false) {
				unavailable.add(type);
			}
		}

		return unavailable;
	}

	public ApiSearchDelegateEntry getEntry(String type) throws UnavailableTypesException {
		ApiSearchDelegateEntry entry = this.entries.get(type);

		if (entry == null) {
			throw new UnavailableTypesException(type);
		}

		return entry;
	}

	// MARK: ApiSearchDelegate
	@Override
	public ApiResponse search(String type,
	                          ApiSearchReadRequest request)
	        throws UnavailableTypesException {
		ApiSearchDelegateEntry entry = this.getEntry(type);
		ApiResponseData responseData = entry.search(request).asResponseData();
		return new ApiResponseImpl(responseData);
	}

	@Override
	public ApiResponse search(Set<String> types,
	                          ApiSearchReadRequest request)
	        throws UnavailableTypesException {
		if (types != null && types.isEmpty()) {
			throw new IllegalArgumentException("Search types cannot be empty. Set null if none specified.");
		}

		Set<String> unavailable = this.getUnavailable(types);

		if (unavailable.isEmpty() == false) {
			throw new UnavailableTypesException(unavailable);
		}

		ApiResponseImpl response = new ApiResponseImpl();

		List<ApiSearchResponseData> searchDataEntries = new ArrayList<ApiSearchResponseData>();

		for (String type : types) {
			ApiSearchDelegateEntry entry = this.entries.get(type);
			ApiSearchResponseData data = entry.search(request);
			searchDataEntries.add(data);
		}

		ApiMultiSearchResponseData responseData = new ApiMultiSearchResponseData(searchDataEntries);
		response.setData(responseData.asResponseData());

		return response;
	}

	@Override
	public ApiResponse query(String type,
	                         ApiSearchReadRequest request)
	        throws UnavailableTypesException {
		ApiSearchDelegateEntry entry = this.getEntry(type);
		ApiResponseData responseData = entry.query(request).asResponseData();
		return new ApiResponseImpl(responseData);
	}

	@Override
	public ApiResponse updateSearchIndex(ApiSearchUpdateRequest request) throws UnavailableTypesException {
		String type = request.getUpdateTargetType();
		ApiSearchDelegateEntry entry = this.entries.get(type);
		entry.updateSearchIndex(request);
		return new ApiResponseImpl();
	}

	@Override
	public String toString() {
		return "ApiSearchDelegateImpl [entries=" + this.entries + "]";
	}

}
