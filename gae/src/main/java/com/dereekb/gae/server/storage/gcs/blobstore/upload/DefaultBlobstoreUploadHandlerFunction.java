package com.dereekb.gae.server.storage.gcs.blobstore.upload;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.storage.upload.function.UploadFunction;
import com.dereekb.gae.server.storage.upload.function.UploadFunctionPair;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * Helper class that implements BlobstoreUploadHandlerFunction and runs an UploadFunction using the input files.
 * 
 * @author dereekb
 *
 */
public class DefaultBlobstoreUploadHandlerFunction<T>
        implements BlobstoreUploadHandlerFunction<T> {

	private Factory<UploadFunction<T, UploadedBlobFile>> uploadFunctionFactory;

	@Override
	public List<T> runWithUploadedFiles(List<UploadedBlobFile> files) {
		List<UploadFunctionPair<T, UploadedBlobFile>> pairs = new ArrayList<UploadFunctionPair<T, UploadedBlobFile>>();

		for (UploadedBlobFile file : files) {
			UploadFunctionPair<T, UploadedBlobFile> pair = new UploadFunctionPair<T, UploadedBlobFile>(file);
			pairs.add(pair);
		}

		UploadFunction<T, UploadedBlobFile> uploadFunction = uploadFunctionFactory.make();
		uploadFunction.addObjects(pairs);
		uploadFunction.run();

		List<T> results = UploadFunctionPair.getResults(pairs);
		return results;
	}

	public Factory<UploadFunction<T, UploadedBlobFile>> getUploadFunctionFactory() {
		return uploadFunctionFactory;
	}

	public void setUploadFunctionFactory(Factory<UploadFunction<T, UploadedBlobFile>> uploadFunctionFactory) {
		this.uploadFunctionFactory = uploadFunctionFactory;
	}

}
