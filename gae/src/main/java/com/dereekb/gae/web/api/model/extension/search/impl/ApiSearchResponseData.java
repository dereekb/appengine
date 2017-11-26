package com.dereekb.gae.web.api.model.extension.search.impl;

import java.util.List;

import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseDataImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Response data for searches that includes query-related results.
 *
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_NULL)
public class ApiSearchResponseData
        implements TypedModel {

	public static final String API_DATA_TYPE = "SEARCH_RESULTS";

	private String modelType = null;
	private List<? extends Object> data = null;

	private String cursor = null;

	public ApiSearchResponseData(String modelType, List<? extends Object> data) {
		this(modelType, data, null);
	}

	public ApiSearchResponseData(String modelType, List<? extends Object> data, String cursor) {
		super();
		this.setModelType(modelType);
		this.setData(data);
		this.setCursor(cursor);
	}

	// MARK:
	@Override
	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		if (modelType == null) {
			throw new IllegalArgumentException("modelType cannot be null.");
		}

		this.modelType = modelType;
	}

	public List<? extends Object> getData() {
		return this.data;
	}

	public void setData(List<? extends Object> data) {
		if (data == null) {
			throw new IllegalArgumentException("data cannot be null.");
		}

		this.data = data;
	}

	public String getCursor() {
		return this.cursor;
	}

	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	// MARK: Utility
	public ApiResponseData asResponseData() {
		return new ApiResponseDataImpl(API_DATA_TYPE, this);
	}

	@Override
	public String toString() {
		return "ApiSearchResponseData [modelType=" + this.modelType + ", data=" + this.data + ", cursor=" + this.cursor
		        + "]";
	}

}
