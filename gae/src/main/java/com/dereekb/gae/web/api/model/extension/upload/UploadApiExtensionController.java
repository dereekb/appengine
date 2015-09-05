package com.dereekb.gae.web.api.model.extension.upload;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.storage.upload.FileUploadUrlFactory;
import com.dereekb.gae.server.storage.upload.exception.FileUploadFailedException;
import com.dereekb.gae.server.storage.upload.exception.FileUploadUrlCreationException;
import com.dereekb.gae.server.storage.upload.handler.FileUploadHandler;
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
public class UploadApiExtensionController {

	private FileUploadUrlFactory urlFactory;
	private FileUploadHandler handler;

	/**
	 * Creates a new upload url.
	 *
	 * @param type
	 *            Uploaded file type. Corresponds to the handler to use.
	 * @return {@link ApiResponse} containing upload url.
	 * @throws FileUploadUrlCreationException
	 */
	@ResponseBody
	@RequestMapping(value = "/upload/{type}", method = RequestMethod.GET, produces = "application/json")
	public final ApiResponse makeFileUploadUrl(@PathVariable("type") String type)
	        throws FileUploadUrlCreationException {
		ApiResponseImpl response = new ApiResponseImpl();

		String uploadUrl = this.urlFactory.makeUploadUrl(type);

		ApiResponseDataImpl urlData = new ApiResponseDataImpl("url");
		urlData.setData(uploadUrl);

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
	@RequestMapping(value = "/upload/{type}", method = RequestMethod.POST, produces = "application/json")
	public final ApiResponse uploadFiles(@PathVariable("type") String type,
	                                     HttpServletRequest request) throws FileUploadFailedException {
		ApiResponse response = this.handler.handleUploadRequest(type, request);
		return response;
	}

}
