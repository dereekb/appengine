package com.thevisitcompany.gae.deprecated.web.response.upload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thevisitcompany.gae.deprecated.web.response.ApiResponse;

/**
 * Response that notes the amount of items created as a result of an upload.
 * 
 * @author dereekb
 */
public class UploadResponse extends ApiResponse {

	private static final String CREATED_ITEMS_COUNT_KEY = "createdCount";

	@JsonIgnore
	public Integer getCreatedItemsCount() {
		Object info = this.readInfo(CREATED_ITEMS_COUNT_KEY);
		Integer count = null;

		if (info != null) {
			count = (Integer) info;
		}

		return count;
	}

	@JsonIgnore
	public void setCreatedItemsCount(Integer createdItemsCount) {
		this.putInfo(CREATED_ITEMS_COUNT_KEY, createdItemsCount);
	}

}
