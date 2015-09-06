package com.dereekb.gae.server.storage.functions.observers;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.storage.accessor.StorageSystem;
import com.dereekb.gae.server.storage.file.impl.StorableFileImpl;
import com.dereekb.gae.utilities.function.staged.StagedFunction;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.observer.StagedFunctionObserver;

@Deprecated
public class DeleteFileObserver<T>
        implements StagedFunctionObserver<T> {

	private boolean ignoreMissingFiles = true;
	private DeleteFileObserverDelegate<T> delegate;
	private StorageSystem accessor;

	@Override
	public void functionHandlerCallback(StagedFunctionStage stage,
	                                    StagedFunction<T, ?> handler) {

		List<T> objects = handler.getFunctionObjects();
		for (T object : objects) {
			Collection<StorableFileImpl> files = this.delegate.retrieveFilesToDelete(object);

			for (StorableFileImpl file : files) {
				boolean success = this.accessor.deleteFile(file);

				if (success == false && this.ignoreMissingFiles == false) {
					this.delegate.handleMissingFile(file, object);
				}
			}
		}
	}

	public boolean isIgnoreMissingFiles() {
		return this.ignoreMissingFiles;
	}

	public void setIgnoreMissingFiles(boolean ignoreMissingFiles) {
		this.ignoreMissingFiles = ignoreMissingFiles;
	}

	public StorageSystem getAccessor() {
		return this.accessor;
	}

	public void setAccessor(StorageSystem accessor) {
		this.accessor = accessor;
	}

	public DeleteFileObserverDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(DeleteFileObserverDelegate<T> delegate) {
		this.delegate = delegate;
	}

}
