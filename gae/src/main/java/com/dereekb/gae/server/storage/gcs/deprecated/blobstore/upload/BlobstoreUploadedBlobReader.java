package com.dereekb.gae.server.storage.gcs.deprecated.blobstore.upload;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.dereekb.gae.server.storage.upload.function.observers.UploadFunctionDataLoaderDelegate;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@Deprecated
public class BlobstoreUploadedBlobReader
        implements UploadFunctionDataLoaderDelegate<UploadedBlobFile> {

	private BlobstoreService service = BlobstoreServiceFactory.getBlobstoreService();
	private Long bufferSize = 0x800L;

	@Override
	public byte[] readBytes(UploadedBlobFile file) {

		Long size = file.getSize();
		byte[] bytes = null;

		if (size != null && size > 0) {
			bytes = this.readBlobData(file);
		}

		return bytes;
	}

	private byte[] readBlobData(UploadedBlobFile file) {

		BlobKey blobKey = file.getKey();
		Long size = file.getSize();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		Long startIndex = 0L;
		Long endIndex = (size >= this.bufferSize) ? this.bufferSize : size;
		byte[] bytes = null;

		boolean completedRead = false;

		try {
			do {
				completedRead = (endIndex == size);

				byte[] readBytes = this.service.fetchData(blobKey, startIndex, endIndex);
				stream.write(readBytes);

				startIndex = endIndex + 1;
				endIndex = startIndex + this.bufferSize;
				if (endIndex > size) {
					endIndex = size;
				}
			} while (completedRead == false);

			bytes = stream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bytes;
	}

	public BlobstoreService getService() {
		return this.service;
	}

	public void setService(BlobstoreService service) {
		this.service = service;
	}

	public Long getBufferSize() {
		return this.bufferSize;
	}

	public void setBufferSize(Long bufferSize) {
		this.bufferSize = bufferSize;
	}

}
