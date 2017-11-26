package com.dereekb.gae.web.api.model.extension.search.impl;

import java.util.List;
import java.util.Map;

import com.dereekb.gae.server.datastore.models.ModelUtility;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseDataImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Mutli-search response.
 *
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_NULL)
public class ApiMultiSearchResponseData {

	public static final String API_DATA_TYPE = "MULTI_SEARCH_RESULTS";

	private Map<String, ApiSearchResponseData> results;

	public ApiMultiSearchResponseData(List<ApiSearchResponseData> results) {
		this.setResultsList(results);
	}

	public ApiMultiSearchResponseData(Map<String, ApiSearchResponseData> results) {
		this.setResults(results);
	}

	public Map<String, ApiSearchResponseData> getResults() {
		return this.results;
	}

	@JsonIgnore
	public void setResultsList(List<ApiSearchResponseData> results) {
		this.setResults(ModelUtility.makeTypedModelMap(results));
	}

	public void setResults(Map<String, ApiSearchResponseData> results) {
		if (results == null) {
			throw new IllegalArgumentException("results cannot be null.");
		}

		this.results = results;
	}

	// MARK: Utility
	public ApiResponseData asResponseData() {
		return new ApiResponseDataImpl(API_DATA_TYPE, this);
	}

	@Override
	public String toString() {
		return "ApiMultiSearchResponseData [results=" + this.results + "]";
	}

}
