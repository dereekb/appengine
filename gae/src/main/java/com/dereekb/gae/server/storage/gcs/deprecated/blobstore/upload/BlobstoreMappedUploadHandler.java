package com.dereekb.gae.server.storage.gcs.deprecated.blobstore.upload;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Reads the blobs that were uploaded, and converts them to the target model.
 * 
 * Uses a map to
 * 
 * All uploaded blobs should be modified or deleted.
 * 
 * @author dereekb
 *
 */
public class BlobstoreMappedUploadHandler<T> extends BlobstoreUploadHandler<T> {

	private Map<String, BlobstoreUploadHandlerFunction<T>> functionsMap = Collections.emptyMap();

	private boolean hasFunctionForKey(String key) {
		return functionsMap.containsKey(key);
	}

	private List<T> handleUpload(String key,
	                             List<UploadedBlobFile> files) {
		List<T> result = Collections.emptyList();
		BlobstoreUploadHandlerFunction<T> function = this.functionsMap.get(key);
		result = function.runWithUploadedFiles(files);
		return result;
	}

	protected List<T> handleUpload(Map<String, List<UploadedBlobFile>> filesMap,
	                               List<UploadedBlobFile> allFiles) {

		List<T> results = new ArrayList<T>();
		List<UploadedBlobFile> unusedBlobs = new ArrayList<UploadedBlobFile>();

		for (String key : filesMap.keySet()) {
			List<UploadedBlobFile> blobsForKey = filesMap.get(key);
			boolean hasKey = this.hasFunctionForKey(key);

			if (hasKey) {
				List<T> result = this.handleUpload(key, blobsForKey);
				results.addAll(result);
			} else {
				unusedBlobs.addAll(blobsForKey);
			}
		}

		if (unusedBlobs.isEmpty() == false && this.uploadFunction != null) {
			List<T> result = super.handleUpload(filesMap, unusedBlobs);
			results.addAll(result);
		}

		return results;
	}

	public Map<String, BlobstoreUploadHandlerFunction<T>> getFunctionsMap() {
		return functionsMap;
	}

	public void setFunctionsMap(Map<String, BlobstoreUploadHandlerFunction<T>> functionsMap) {
		this.functionsMap = functionsMap;
	}

}
