package com.dereekb.gae.server.storage.gcs.deprecated.blobstore.upload;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.storage.gcs.deprecated.blobstore.BlobstoreUploadedBlobReader;
import com.dereekb.gae.server.storage.upload.FileUploadHandler;

/**
 * Simple upload handler that reads the all blobs that were uploaded, and converts them to the target model using a
 * BlobstoreUploadHandlerFunction.
 * 
 * @author dereekb
 */
public class BlobstoreUploadHandler<T>
        implements FileUploadHandler<T> {

	protected BlobstoreUploadedBlobReader blobReader;
	protected BlobstoreUploadHandlerFunction<T> uploadFunction;

	/**
	 * Handles reading uploaded objects by retrieving them from the blobstore.
	 * 
	 * Uploaded blobs are deleted when this function completes or encounters an exception.
	 */
	@Override
	public final List<T> handleUploadRequest(HttpServletRequest request) {
		Map<String, List<UploadedBlobFile>> filesMap = blobReader.uploadedBlobFilesMap(request);
		List<UploadedBlobFile> files = blobReader.uploadedBlobFilesFromMap(filesMap);
		List<T> result = null;

		try {
			result = this.handleUpload(filesMap, files);
			blobReader.deleteUploadedFiles(files);
		} catch (Exception e) {
			blobReader.deleteUploadedFiles(files);
			throw e;
		}

		return result;
	}

	/**
	 * Handles the upload of all files. Both the original filesMap and all files are provided
	 * 
	 * @param filesMap
	 * @param allFiles
	 * @return
	 */
	protected List<T> handleUpload(Map<String, List<UploadedBlobFile>> filesMap,
	                               List<UploadedBlobFile> files) {
		List<T> result = Collections.emptyList();
		result = this.uploadFunction.runWithUploadedFiles(files);
		return result;
	}

	public BlobstoreUploadedBlobReader getBlobReader() {
		return blobReader;
	}

	public void setBlobReader(BlobstoreUploadedBlobReader blobReader) {
		this.blobReader = blobReader;
	}

	public BlobstoreUploadHandlerFunction<T> getFunction() {
		return uploadFunction;
	}

	public void setFunction(BlobstoreUploadHandlerFunction<T> function) {
		this.uploadFunction = function;
	}

	public BlobstoreUploadHandlerFunction<T> getUploadFunction() {
		return uploadFunction;
	}

	public void setUploadFunction(BlobstoreUploadHandlerFunction<T> uploadFunction) {
		this.uploadFunction = uploadFunction;
	}

}
