package com.dereekb.gae.model.stored.blob.storage;

import java.io.IOException;
import java.util.Arrays;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.StoredBlobType;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;
import com.dereekb.gae.server.storage.accessor.StorageSystem;
import com.dereekb.gae.server.storage.download.DownloadKeyService;
import com.dereekb.gae.server.storage.object.file.StorableContent;
import com.dereekb.gae.server.storage.upload.data.task.UploadedPairHandlerTaskDelegate;
import com.dereekb.gae.server.storage.upload.reader.UploadedFileInfo;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseDataImpl;

/**
 * {@link UploadedPairHandlerTaskDelegate} implementation that creates a
 * {@link StoredBlob} that represents the uploaded file.
 *
 * @author dereekb
 *
 */
public class StoredBlobUploadHandler<T extends Descriptor>
        implements UploadedPairHandlerTaskDelegate {

	private StoredBlobType blobType;

	private StorageSystem storageSystem;

	private ConfiguredSetter<StoredBlob> storedBlobSetter;
	private StoredBlobUploadHandlerDelegate<T> delegate;

	private DownloadKeyService downloadKeyService;

	public StoredBlobUploadHandler() {}

	public StoredBlobUploadHandler(StoredBlobType blobType,
	        StorageSystem storageSystem,
	        ConfiguredSetter<StoredBlob> storedBlobSetter,
	        StoredBlobUploadHandlerDelegate<T> delegate,
	        DownloadKeyService downloadKeyService) {
		this.blobType = blobType;
		this.storageSystem = storageSystem;
		this.storedBlobSetter = storedBlobSetter;
		this.delegate = delegate;
		this.downloadKeyService = downloadKeyService;
	}

	public StoredBlobType getBlobType() {
		return this.blobType;
	}

	public void setBlobType(StoredBlobType blobType) {
		this.blobType = blobType;
	}

	public StorageSystem getStorageSystem() {
		return this.storageSystem;
	}

	public void setStorageSystem(StorageSystem storageSystem) {
		this.storageSystem = storageSystem;
	}

	public ConfiguredSetter<StoredBlob> getStoredBlobSetter() {
		return this.storedBlobSetter;
	}

	public void setStoredBlobSetter(ConfiguredSetter<StoredBlob> storedBlobSetter) {
		this.storedBlobSetter = storedBlobSetter;
	}

	public StoredBlobUploadHandlerDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(StoredBlobUploadHandlerDelegate<T> delegate) {
		this.delegate = delegate;
	}

	public DownloadKeyService getDownloadKeyService() {
		return this.downloadKeyService;
	}

	public void setDownloadKeyService(DownloadKeyService downloadKeyService) {
		this.downloadKeyService = downloadKeyService;
	}

	// MARK: UploadedPairHandlerTaskDelegate
	@Override
	public ApiResponseData useUploadedBytes(byte[] bytes,
	                                        UploadedFileInfo info) {
		Instance instance = new Instance(bytes, info);

		return instance.useUploadedBytes();
	}

	public class Instance {

		private final byte[] bytes;
		private final UploadedFileInfo info;

		private StoredBlob storedBlob;
		private StorableContent storedContent;

		private T descriptor;

		private Instance(byte[] bytes, UploadedFileInfo info) {
			this.bytes = bytes;
			this.info = info;
		}

		public StoredBlob getStoredBlob() {
			return this.storedBlob;
		}

		public StorableContent getStoredContent() {
			return this.storedContent;
		}

		public byte[] getBytes() {
			return this.bytes;
		}

		public UploadedFileInfo getInfo() {
			return this.info;
		}

		public T getDescriptor() {
			return this.descriptor;
		}

		public ApiResponseData useUploadedBytes() {
			this.storedBlob = this.createStoredBlob();

			// Create Stored Content
			try {
				this.storedContent = StoredBlobUploadHandler.this.delegate.buildContent(this.bytes, this.storedBlob);
				StoredBlobUploadHandler.this.storageSystem.saveFile(this.storedContent);
			} catch (IOException e) {
				this.cleanupFailure();
			}

			// Create
			this.descriptor = StoredBlobUploadHandler.this.delegate.createDescriptor(this.storedBlob);

			this.updateStoredBlob();

			ApiResponseData responseData = this.createResponseData();
			return responseData;
		}

		/**
		 * Creates and saves a stored blob.
		 * <p>
		 * Saving occurs synchronously so an identifier is attached to the
		 * {@link StoredBlob}.
		 * </p>
		 */
		private StoredBlob createStoredBlob() {
			StoredBlob blob = new StoredBlob();

			blob.setBlobType(StoredBlobUploadHandler.this.blobType);
			blob.setBlobName(this.info.getFilename());
			blob.setDate(this.info.getCreationDate());

			StoredBlobUploadHandler.this.storedBlobSetter.save(blob, false);

			return blob;
		}

		/**
		 * Updates the stored blob to use the saved element.
		 * <p>
		 * Saving occurs based on how the {@link ConfiguredSetter} is
		 * configured.
		 * </p>
		 */
		private void updateStoredBlob() {
			String filePath = this.storedContent.getFilePath();

			this.storedBlob.setDescriptor(this.descriptor);
			this.storedBlob.setFilePath(filePath);

			StoredBlobUploadHandler.this.storedBlobSetter.save(this.storedBlob);
		}

		/**
		 * Cleans up the instance, deleting any created components to prevent
		 * orphaned elements.
		 */
		private void cleanupFailure() {
			if (this.storedBlob != null) {
				StoredBlobUploadHandler.this.storedBlobSetter.delete(this.storedBlob);
			}

			if (this.storedContent != null) {
				try {
					StoredBlobUploadHandler.this.storageSystem.deleteFile(this.storedContent);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * Creates the {@link ApiResponseData} to return.
		 */
		private ApiResponseData createResponseData() {
			ModelKey key = this.storedBlob.getModelKey();

			UploadedStoredBlobData uploadData = new UploadedStoredBlobData();
			uploadData.setModelKey(key);
			uploadData.setDescriptor(this.descriptor);

			uploadData.setSize(this.info.getFileSize());

			if (StoredBlobUploadHandler.this.downloadKeyService != null) {
				String downloadKey = StoredBlobUploadHandler.this.downloadKeyService
				        .makeDownloadKey(this.storedContent);
				uploadData.setDownloadKey(downloadKey);
			}

			ApiResponseDataImpl data = new ApiResponseDataImpl("UploadedStoredBlobData", uploadData);
			return data;
		}

		@Override
		public String toString() {
			return "Instance [bytes=" + Arrays.toString(this.bytes) + ", info=" + this.info + ", storedBlob="
			        + this.storedBlob + ", storedContent=" + this.storedContent + ", descriptor=" + this.descriptor
			        + "]";
		}

	}

	@Override
	public String toString() {
		return "StoredBlobUploadHandler [blobType=" + this.blobType + ", storageSystem=" + this.storageSystem
		        + ", storedBlobSetter=" + this.storedBlobSetter + ", delegate=" + this.delegate + "]";
	}

}
