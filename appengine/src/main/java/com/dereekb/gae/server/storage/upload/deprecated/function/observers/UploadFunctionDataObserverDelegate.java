package com.dereekb.gae.server.storage.upload.deprecated.function.observers;

import com.dereekb.gae.server.storage.upload.deprecated.UploadedFile;
import com.dereekb.gae.server.storage.upload.deprecated.function.UploadFunctionPair;

/**
 * Interface that is called to handle the uploaded data.
 *
 * @author dereekb
 */
@Deprecated
public interface UploadFunctionDataObserverDelegate<T, U extends UploadedFile> {

	public void handleUploadedData(Iterable<UploadFunctionPair<T, U>> pairs);

}
