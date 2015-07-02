package com.thevisitcompany.gae.deprecated.web.api.models.support.upload;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.thevisitcompany.gae.deprecated.web.response.upload.UploadLinkResponse;
import com.thevisitcompany.gae.deprecated.web.response.upload.UploadResponse;
import com.thevisitcompany.gae.server.storage.upload.FileUploadHandler;
import com.thevisitcompany.gae.server.storage.upload.FileUploadUrlFactory;

public class UploadApiComponent<T> {

	protected FileUploadHandler<T> uploadHandler;
	protected FileUploadUrlFactory uploadUrlFactory;

	public UploadApiComponent() {};

	public UploadApiComponent(FileUploadUrlFactory uploadUrlFactory) {
		super();
		this.uploadUrlFactory = uploadUrlFactory;
	}

	public UploadApiComponent(FileUploadHandler<T> uploadHandler, FileUploadUrlFactory uploadUrlFactory) {
		super();
		this.uploadHandler = uploadHandler;
		this.uploadUrlFactory = uploadUrlFactory;
	}

	public final UploadResponse upload(HttpServletRequest request) {
		UploadResponse response = new UploadResponse();
		List<T> results = uploadHandler.upload(request);

		Integer resultsCount = (results != null) ? results.size() : 0;
		response.setCreatedItemsCount(resultsCount);
		return response;
	}

	public UploadLinkResponse generateUploadLink() throws RuntimeException {
		UploadLinkResponse response = new UploadLinkResponse();

		String uploadLink = uploadUrlFactory.newUploadUrl();
		response.setUploadUrl(uploadLink);

		if (uploadLink == null) {
			throw new RuntimeException("Could not generate a new upload link.");
		}

		return response;
	}

	public FileUploadHandler<T> getUploadHandler() {
		return uploadHandler;
	}

	public void setUploadHandler(FileUploadHandler<T> uploadHandler) throws IllegalArgumentException {
		if (uploadHandler == null) {
			throw new IllegalArgumentException();
		}

		this.uploadHandler = uploadHandler;
	}

	public FileUploadUrlFactory getUploadUrlFactory() {
		return uploadUrlFactory;
	}

	public void setUploadUrlFactory(FileUploadUrlFactory uploadUrlFactory) throws IllegalArgumentException {
		if (uploadUrlFactory == null) {
			throw new IllegalArgumentException();
		}

		this.uploadUrlFactory = uploadUrlFactory;
	}
}
