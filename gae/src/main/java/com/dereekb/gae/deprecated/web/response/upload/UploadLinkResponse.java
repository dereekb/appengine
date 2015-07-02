package com.thevisitcompany.gae.deprecated.web.response.upload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thevisitcompany.gae.deprecated.web.response.ApiResponse;

/**
 * Response for retrieving the URLs for uploading.
 * 
 * @author dereekb
 */
public class UploadLinkResponse extends ApiResponse {

	private static final String UPLOAD_URL_KEY = "upload";

	@JsonIgnore
	public String getUploadUrl() {
		String url = null;
		Object info = this.readInfo(UPLOAD_URL_KEY);

		if (info != null) {
			url = (String) info;
		}

		return url;
	}

	@JsonIgnore
	public void setUploadUrl(String uploadUrl) {
		this.putInfo(UPLOAD_URL_KEY, uploadUrl);
	}

}
