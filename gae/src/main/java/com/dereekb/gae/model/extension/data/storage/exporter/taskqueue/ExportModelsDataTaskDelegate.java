package com.dereekb.gae.model.extension.data.storage.exporter.taskqueue;

import com.dereekb.gae.model.extension.taskqueue.api.CustomTaskInfo;
import com.dereekb.gae.server.storage.file.StorageFile;


public interface ExportModelsDataTaskDelegate {

	/**
	 * @param request
	 * @return Returns the file to save to.
	 */
	public StorageFile storageFileForRequest(CustomTaskInfo request);

}
