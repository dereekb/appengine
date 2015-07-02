package com.dereekb.gae.server.storage.upload.function;

import com.dereekb.gae.server.storage.upload.UploadedFile;
import com.dereekb.gae.utilities.function.staged.filter.FilteredStagedFunction;

/**
 * Function used during uploading an object. Uses a delegate to create an object from the uploaded function.
 * 
 * @author dereekb
 *
 * @param <T>
 *            Created Item Type
 * @param <U>
 *            UploadedFile Type
 */
public class UploadFunction<T, U extends UploadedFile> extends FilteredStagedFunction<T, UploadFunctionPair<T, U>> {

	private UploadFunctionDelegate<T> uploadDelegate;

	@Override
	protected void doFunction() {
		Iterable<UploadFunctionPair<T, U>> pairs = this.getWorkingObjects();
		this.generateObjects(pairs);
	}

	private void generateObjects(Iterable<UploadFunctionPair<T, U>> pairs) {
		for (UploadFunctionPair<T, U> pair : pairs) {
			U source = pair.getSource();
			T result = this.uploadDelegate.createForUpload(source);
			pair.setResult(result);
		}
	}

	public UploadFunctionDelegate<T> getUploadDelegate() {
		return uploadDelegate;
	}

	public void setUploadDelegate(UploadFunctionDelegate<T> uploadDelegate) {
		if (uploadDelegate == null) {
			throw new NullPointerException();
		}

		this.uploadDelegate = uploadDelegate;
	}

}
