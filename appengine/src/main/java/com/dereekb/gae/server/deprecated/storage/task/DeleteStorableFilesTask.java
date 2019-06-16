package com.dereekb.gae.server.storage.task;

import java.io.IOException;

import com.dereekb.gae.server.deprecated.storage.accessor.StorageSystem;
import com.dereekb.gae.server.deprecated.storage.object.file.StorableFile;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * Task that deletes all input files from the configured {@link StorageSystem}.
 *
 * @author dereekb
 *
 */
public class DeleteStorableFilesTask
        implements IterableTask<StorableFile> {

	private boolean failOnException = false;
	private StorageSystem storageSystem;

	public DeleteStorableFilesTask(StorageSystem storageSystem) throws IllegalArgumentException {
		this.setStorageSystem(storageSystem);
	}

	public boolean isFailOnException() {
		return this.failOnException;
	}

	public void setFailOnException(boolean failOnException) {
		this.failOnException = failOnException;
	}

	public StorageSystem getStorageSystem() {
		return this.storageSystem;
	}

	public void setStorageSystem(StorageSystem storageSystem) throws IllegalArgumentException {
		if (storageSystem == null) {
			throw new IllegalArgumentException("StorageSystem cannot be null.");
		}

		this.storageSystem = storageSystem;
	}

	@Override
	public void doTask(Iterable<StorableFile> input) throws FailedTaskException {
		for (StorableFile file : input) {
			try {
				this.storageSystem.deleteFile(file);
			} catch (IOException e) {
				if (this.failOnException) {
					throw new FailedTaskException(e);
				} else {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public String toString() {
		return "DeleteStorableFilesTask [failOnException=" + this.failOnException + ", storageSystem="
		        + this.storageSystem + "]";
	}

}
