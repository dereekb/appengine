package com.dereekb.gae.server.storage.upload.function.observers;

import com.dereekb.gae.server.storage.upload.UploadedFile;
import com.dereekb.gae.server.storage.upload.function.UploadFunctionPair;

/**
 * Interface that is called to handle the uploaded data.
 * 
 * @author dereekb
 */
public interface UploadFunctionDataObserverDelegate<T, U extends UploadedFile> {

	public void handleUploadedData(Iterable<UploadFunctionPair<T, U>> pairs);

}
