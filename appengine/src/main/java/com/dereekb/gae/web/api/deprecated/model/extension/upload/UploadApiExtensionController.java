package com.dereekb.gae.web.api.model.extension.upload;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.deprecated.storage.upload.FileUploadUrlFactory;
import com.dereekb.gae.server.deprecated.storage.upload.exception.FileUploadFailedException;
import com.dereekb.gae.server.deprecated.storage.upload.exception.FileUploadUrlCreationException;
import com.dereekb.gae.server.deprecated.storage.upload.handler.FileUploadHandler;
import com.dereekb.gae.web.api.deprecated.model.extension.upload.exception.InvalidUploadTypeException;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseDataImpl;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * Controller for the file upload API components.
 *
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/upload")
public class UploadApiExtensionController {

	private FileUploadUrlFactory urlFactory;
	private FileUploadHandler handler;

	public UploadApiExtensionController() {}

	public UploadApiExtensionController(FileUploadUrlFactory urlFactory, FileUploadHandler handler) {
		this.urlFactory = urlFactory;
		this.handler = handler;
	}

	public FileUploadUrlFactory getUrlFactory() {
		return this.urlFactory;
	}

	public void setUrlFactory(FileUploadUrlFactory urlFactory) {
		this.urlFactory = urlFactory;
	}

	public FileUploadHandler getHandler() {
		return this.handler;
	}

	public void setHandler(FileUploadHandler handler) {
		this.handler = handler;
	}

	// MARK: API
	/**
	 * Creates a new upload url.
	 *
	 * @param type
	 *            Uploaded file type. Corresponds to the handler to use.
	 * @return {@link ApiResponse} containing upload url.
	 * @throws FileUploadUrlCreationException
	 * @throws InvalidUploadTypeException
	 */
	@ResponseBody
	@RequestMapping(value = "/new", method = RequestMethod.POST, produces = "application/json")
	public final ApiResponse makeFileUploadUrl(@NotNull @RequestParam("type") String type)
	        throws FileUploadUrlCreationException,
	            FileUploadUrlCreationException,
	            InvalidUploadTypeException {
		ApiResponseImpl response = new ApiResponseImpl();

		String uploadUrl = this.urlFactory.makeUploadUrl(type);

		ApiResponseDataImpl urlData = new ApiResponseDataImpl("url");
		urlData.setData(uploadUrl);
		response.setData(urlData);

		return response;
	}

	/**
	 * Performs an upload.
	 *
	 * @param request
	 *            {@link HttpServletRequest} containing data.
	 * @return {@link ApiResponse} containing result data.
	 * @throws FileUploadFailedException
	 *             Thrown if the upload fails. All uploaded files are deleted,
	 *             and no new files have been created.
	 */
	@ResponseBody
	@RequestMapping(value = "/{type}", method = RequestMethod.POST, produces = "application/json")
	public final ApiResponse uploadFiles(@PathVariable("type") String type,
	                                     HttpServletRequest request) throws FileUploadFailedException {
		ApiResponse response = this.handler.handleUploadRequest(type, request);
		return response;
	}

	@Override
	public String toString() {
		return "UploadApiExtensionController [urlFactory=" + this.urlFactory + ", handler=" + this.handler + "]";
	}

}
