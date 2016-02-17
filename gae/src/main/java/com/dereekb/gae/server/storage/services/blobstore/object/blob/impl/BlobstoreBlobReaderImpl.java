package com.dereekb.gae.server.storage.services.blobstore.object.blob.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.dereekb.gae.server.storage.services.blobstore.object.blob.BlobstoreBlobReader;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;

/**
 * Used for reading blobs from the Blobstore.
 *
 * @author dereekb
 *
 */
public class BlobstoreBlobReaderImpl
        implements BlobstoreBlobReader {

	private BlobstoreService service;
	private Long bufferSize = 0x800L;

	public BlobstoreBlobReaderImpl(BlobstoreService service) {
		this.setService(service);
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

	// MARK: BlobstoreBlobReader
	@Override
    public byte[] readBlobBytes(BlobKey blobKey,
	                            Long size) throws IOException {
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

	@Override
	public String toString() {
		return "BlobstoreBlobReaderImpl [indexService=" + this.service + ", bufferSize=" + this.bufferSize + "]";
	}

}
