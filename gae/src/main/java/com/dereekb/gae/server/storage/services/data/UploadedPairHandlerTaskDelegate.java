package com.dereekb.gae.server.storage.services.data;

import com.dereekb.gae.server.storage.upload.reader.UploadedFileInfo;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;

/**
 * {@link UploadedPairHandlerTask} delegate.
 *
 * @author dereekb
 *
 */
public interface UploadedPairHandlerTaskDelegate {

	/**
	 * Use the uploaded data to create something. Returns a
	 * {@link ApiResponseData} describing what happened with the data.
	 *
	 * @param bytes
	 *            {@code byte[]} data
	 * @param info
	 *            {@link UploadedFileInfo} for the uploaded bytes.
	 * @return {@link ApiResponseData} that should be included in the response.
	 */
	public ApiResponseData useUploadedBytes(byte[] bytes,
	                                        UploadedFileInfo info);

}
