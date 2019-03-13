package com.dereekb.gae.model.stored.blob.storage.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.storage.accessor.StorageSystem;
import com.dereekb.gae.server.storage.object.file.StorableFile;
import com.dereekb.gae.server.storage.task.DeleteStorableFilesTask;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * Deletes files associated with each blob.
 *
 * @author dereekb
 *
 */
public class StoredBlobDeleteDataTask
        implements Task<ModelKeyListAccessor<StoredBlob>> {

	private DeleteStorableFilesTask deleteTask;

	public StoredBlobDeleteDataTask(StorageSystem storageSystem) throws IllegalArgumentException {
		this(new DeleteStorableFilesTask(storageSystem));
	}

	public StoredBlobDeleteDataTask(DeleteStorableFilesTask deleteTask) throws IllegalArgumentException {
		this.setDeleteTask(deleteTask);
	}

	public DeleteStorableFilesTask getDeleteTask() {
		return this.deleteTask;
	}

	public void setDeleteTask(DeleteStorableFilesTask deleteTask) throws IllegalArgumentException {
		if (deleteTask == null) {
			throw new IllegalArgumentException("DeleteTask cannot be null.");
		}

		this.deleteTask = deleteTask;
	}

	@Override
	public void doTask(ModelKeyListAccessor<StoredBlob> input) throws FailedTaskException {
		List<StoredBlob> storedBlobs = input.getModels();
		List<StorableFile> storableFiles = new ArrayList<StorableFile>(storedBlobs);
		this.deleteTask.doTask(storableFiles);
	}

	@Override
	public String toString() {
		return "StoredBlobDeleteDataTask [deleteTask=" + this.deleteTask + "]";
	}

}
