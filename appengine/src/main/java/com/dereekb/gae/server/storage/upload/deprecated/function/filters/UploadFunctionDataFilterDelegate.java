package com.dereekb.gae.server.storage.upload.deprecated.function.filters;

import com.dereekb.gae.server.storage.upload.deprecated.UploadedFile;

/**
 * Interface that is called to handle the uploaded data.
 *
 * @author dereekb
 */
@Deprecated
public interface UploadFunctionDataFilterDelegate<U extends UploadedFile> {

	public boolean isValidData(U file);

}
