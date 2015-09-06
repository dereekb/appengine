package com.dereekb.gae.server.storage.services.data;

import java.io.IOException;

import com.dereekb.gae.server.storage.exception.InvalidFileDataException;
import com.dereekb.gae.server.storage.exception.NoFileDataException;
import com.dereekb.gae.server.storage.upload.handler.impl.delegate.UploadedFileResult;
import com.dereekb.gae.server.storage.upload.handler.impl.delegate.impl.UploadPair;
import com.dereekb.gae.server.storage.upload.handler.impl.delegate.impl.UploadedFileResultImpl;
import com.dereekb.gae.server.storage.upload.reader.UploadedFile;
import com.dereekb.gae.server.storage.upload.reader.UploadedFileInfo;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.utilities.validation.ContentValidationException;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;

/**
 * {@link Task} for handling an {@link UploadPair} using optional components.
 *
 * @author dereekb
 *
 */
public class UploadedPairHandlerTask
        implements Task<UploadPair> {

	private BytesContentValidator validator;
	private BytesContentModifier modifier;
	private UploadedPairHandlerTaskDelegate delegate;

	@Override
	public void doTask(UploadPair input) throws FailedTaskException {
		UploadedFile file = input.getSource();

		Instance instance = new Instance(file);

		UploadedFileResult result = instance.buildResult();
		input.setResult(result);
	}

	private class Instance {

		private final UploadedFile file;

		private Instance(UploadedFile file) {
			this.file = file;
		}

		public UploadedFileResult buildResult() {
			UploadedFileResult result;

			try {
				result = this.useFile();
			} catch (NoFileDataException | IOException e) {
				result = UploadedFileResultImpl.withMessage(this.file, "File was unavailable.");
			} catch (ContentValidationException e) {
				result = UploadedFileResultImpl.withException(this.file, e);
			} catch (InvalidFileDataException e) {
				result = UploadedFileResultImpl.withException(this.file, e);
			} catch (Exception e) {
				result = UploadedFileResultImpl.withException(this.file, e);
			}

			return result;
		}

		private UploadedFileResult useFile()
		        throws NoFileDataException,
		            IOException,
		            ContentValidationException,
		            InvalidFileDataException {

			byte[] bytes = this.getBytes();

			if (UploadedPairHandlerTask.this.validator != null) {
				UploadedPairHandlerTask.this.validator.validateContent(bytes);
			}

			if (UploadedPairHandlerTask.this.modifier != null) {
				bytes = UploadedPairHandlerTask.this.modifier.modifyBytesContent(bytes);
			}

			UploadedFileInfo info = this.file.getUploadedFileInfo();
			ApiResponseData responseData = UploadedPairHandlerTask.this.delegate.useUploadedBytes(bytes, info);
			UploadedFileResult result = new UploadedFileResultImpl(this.file, responseData, true);
			return result;
		}

		private byte[] getBytes() throws NoFileDataException, IOException {
			return this.file.getUploadedFileBytes();
		}

	}

	@Override
	public String toString() {
		return "UploadedPairHandlerTask [validator=" + this.validator
		        + ", modifier=" + this.modifier + ", delegate=" + this.delegate + "]";
	}

}
