package com.dereekb.gae.server.storage.upload.function.observers;

import com.dereekb.gae.server.storage.upload.UploadedFile;
import com.dereekb.gae.server.storage.upload.function.UploadFunctionPair;

/**
 * Observer that loads the uploaded data into the UploadFile of type <U>.
 * 
 * If the file has issues being read, the pair is set to failed to be filtered out.
 * 
 * @author dereekb
 *
 * @param <U>
 */
public class UploadFunctionDataLoader<U extends UploadedFile>
        implements UploadFunctionDataObserverDelegate<Object, U> {

	private UploadFunctionDataLoaderDelegate<U> delegate;

	@Override
	public void handleUploadedData(Iterable<UploadFunctionPair<Object, U>> pairs) {
		for (UploadFunctionPair<Object, U> pair : pairs) {
			U file = pair.getSource();
			byte[] bytes = this.delegate.readBytes(file);

			if (bytes == null) {
				pair.setSuccessful(false);
			} else {
				file.setBytes(bytes);
			}
		}
	}

	public UploadFunctionDataLoaderDelegate<U> getDelegate() {
		return delegate;
	}

	public void setDelegate(UploadFunctionDataLoaderDelegate<U> delegate) {
		this.delegate = delegate;
	}

}
