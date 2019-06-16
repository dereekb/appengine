package com.dereekb.gae.server.storage.upload.handler.impl.delegate.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.deprecated.storage.upload.handler.impl.delegate.FileUploadHandlerDelegate;
import com.dereekb.gae.server.deprecated.storage.upload.handler.impl.delegate.FileUploadHandlerDelegateResult;
import com.dereekb.gae.server.deprecated.storage.upload.handler.impl.delegate.UploadedFileResult;
import com.dereekb.gae.server.deprecated.storage.upload.reader.UploadedFile;
import com.dereekb.gae.server.deprecated.storage.upload.reader.UploadedFileSet;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link FileUploadHandlerDelegate} implementation. Uses a single {@link Task}
 * to run against the input items.
 *
 * @author dereekb
 *
 */
public class FileUploadHandlerDelegateImpl
        implements FileUploadHandlerDelegate {

	private Task<UploadPair> uploadTask;

	public FileUploadHandlerDelegateImpl() {}

	public FileUploadHandlerDelegateImpl(Task<UploadPair> uploadTask) {
		this.setUploadTask(uploadTask);
	}

	public Task<UploadPair> getUploadTask() {
		return this.uploadTask;
	}

	public void setUploadTask(Task<UploadPair> uploadTask) {
		this.uploadTask = uploadTask;
	}

	// MARK: FileUploadHandlerDelegate
	@Override
	public FileUploadHandlerDelegateResult handleUploadedFiles(UploadedFileSet set) throws RuntimeException {
		Instance instance = new Instance(set);

		instance.runTask();

		FileUploadHandlerDelegateResult results = instance.getResults();
		return results;
	}

	private class Instance {

		private final UploadedFileSet set;

		private List<UploadedFileResult> usedFiles = new ArrayList<UploadedFileResult>();
		private List<UploadedFileResult> failedFiles = new ArrayList<UploadedFileResult>();

		public Instance(UploadedFileSet set) {
			this.set = set;
		}

		public void runTask() {
			List<UploadedFile> files = this.set.getAllUploadedFiles();
			List<UploadPair> pairs = UploadPair.createPairsForFiles(files);

			for (UploadPair pair : pairs) {
				UploadedFileResult result = null;

				try {
					FileUploadHandlerDelegateImpl.this.uploadTask.doTask(pair);
					result = pair.getResult();
				} catch (FailedTaskException e) {
					// TODO: Append result.
				} catch (Exception e) {
					// TODO: Append result.
				}

				if (result.fileWasUsed()) {
					this.usedFiles.add(result);
				} else {
					this.failedFiles.add(result);
				}
			}

		}

		public FileUploadHandlerDelegateResultImpl getResults() {
			return new FileUploadHandlerDelegateResultImpl(this.usedFiles, this.failedFiles);
		}

	}

	@Override
	public String toString() {
		return "FileUploadHandlerDelegateImpl [uploadTask=" + this.uploadTask + "]";
	}

}
