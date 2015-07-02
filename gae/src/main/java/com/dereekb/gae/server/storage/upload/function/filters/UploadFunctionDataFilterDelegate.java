package com.dereekb.gae.server.storage.upload.function.filters;

import com.dereekb.gae.server.storage.upload.UploadedFile;

/**
 * Interface that is called to handle the uploaded data.
 * 
 * @author dereekb
 */
public interface UploadFunctionDataFilterDelegate<U extends UploadedFile> {

	public boolean isValidData(U file);

}
