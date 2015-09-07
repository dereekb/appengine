package com.dereekb.gae.model.extension.data.storage.exporter.taskqueue;

import com.dereekb.gae.model.extension.taskqueue.api.CustomTaskInfo;
import com.dereekb.gae.server.storage.object.file.impl.StorableFileImpl;


@Deprecated
public interface ExportModelsDataTaskDelegate {

	/**
	 * @param request
	 * @return Returns the file to save to.
	 */
	public StorableFileImpl storageFileForRequest(CustomTaskInfo request);

}
