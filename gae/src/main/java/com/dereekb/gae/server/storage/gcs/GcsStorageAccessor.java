package com.dereekb.gae.server.storage.gcs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;

import com.dereekb.gae.server.storage.StorageAccessor;
import com.dereekb.gae.server.storage.file.StorableContent;
import com.dereekb.gae.server.storage.file.StorableFile;
import com.dereekb.gae.server.storage.gcs.files.GcsStorageFileData;
import com.dereekb.gae.server.storage.gcs.files.GcsStorageFileRequestBuilder;
import com.google.appengine.tools.cloudstorage.GcsFileMetadata;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;
import com.google.common.io.ByteStreams;

/**
 * Used for writing/retrieving items from the Google Cloud Storage.
 *
 * @author dereekb
 *
 */
public class GcsStorageAccessor
        implements StorageAccessor {

	private final int DEFAULT_READ_BUFFER_SIZE = (0x08000); // 512KB buffer

	private final GcsService gcsService = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());

	/**
	 * Buffer size to use for the prefetching read channel.
	 */
	private Integer bufferSize = this.DEFAULT_READ_BUFFER_SIZE;

	/**
	 * Whether or not to automatically retrieve metadata for read files.
	 */
	private Boolean loadMetadataWithRead = false;

	private GcsStorageFileRequestBuilder requestBuilder;

	/**
	 * Google Cloud Storage bucket name.
	 */
	private final String bucket;

	public GcsStorageAccessor(String bucket) {
		this.bucket = bucket;
	}


	public Integer getBufferSize() {
		return this.bufferSize;
	}

	public void setBufferSize(Integer bufferSize) {
		this.bufferSize = bufferSize;
	}

	public Boolean getLoadMetadataWithRead() {
		return this.loadMetadataWithRead;
	}

	public void setLoadMetadataWithRead(Boolean loadMetadataWithRead) {
		this.loadMetadataWithRead = loadMetadataWithRead;
	}

	public GcsService getGcsService() {
		return this.gcsService;
	}

	public GcsStorageFileRequestBuilder getRequestBuilder() {
		return this.requestBuilder;
	}

	public void setRequestBuilder(GcsStorageFileRequestBuilder requestBuilder) {
		this.requestBuilder = requestBuilder;
	}
	
	public String getBucket() {
		return this.bucket;
	}

	@Override
    public String getStorageSystemName() {
	    return "Google Cloud Storage";
    }

	@Override
	public boolean saveFile(StorableContent file) {
		GcsStorageFileSaveRequest request = null;

		if (this.requestBuilder != null) {
			request = this.requestBuilder.createSaveRequest(this.bucket, file);
		} else {
			request = new GcsStorageFileSaveRequest(this.bucket, file);
		}

		return this.saveFile(request);
	}

	public boolean saveFile(GcsStorageFileSaveRequest request) {
		boolean success = true;

		try {
			this.writeToFile(request);
		} catch (IOException e) {
			e.printStackTrace();
			success = false;
		}

		return success;
	}

	@Override
	public GcsStorageFileData loadFile(StorableFile file) throws IOException {
		GcsStorageFileRequest request = new GcsStorageFileRequest(this.bucket, file);
		return this.loadFile(request);
	}

	public GcsStorageFileData loadFile(GcsStorageFileRequest request) throws IOException {
		GcsStorageFileData data = this.loadFile(request, this.loadMetadataWithRead);
		return data;
	}

	public GcsStorageFileData loadFile(GcsStorageFileRequest request,
	                                   boolean loadMetadata) throws IOException {
		GcsReadResults results = this.readFile(request, loadMetadata);
		byte[] bytes = results.getBytes();
		GcsFileMetadata metadata = results.getMetadata();
		GcsFilename filename = results.getFilename();
		GcsStorageFileData data = new GcsStorageFileData(request.getFile(), filename, bytes, metadata);
		return data;
	}

	public GcsFileMetadata loadMetadata(StorableFile file) throws IOException {
		GcsFilename filename = this.createFilename(file);
		return this.loadMetadata(filename);
	}

	public GcsFileMetadata loadMetadata(GcsStorageFileRequest request) throws IOException {
		GcsFilename filename = request.getGcsFilename();
		return this.loadMetadata(filename);
	}

	public GcsFileMetadata loadMetadata(GcsFilename filename) throws IOException {
		GcsFileMetadata metadata = this.gcsService.getMetadata(filename);
		return metadata;
	}

	private GcsFilename createFilename(StorableFile file) {
		String path = file.getFilePath();
		GcsFilename filename = new GcsFilename(this.bucket, path);
		return filename;
	}

	private void writeToFile(GcsStorageFileSaveRequest request) throws IOException {

		GcsFileOptions options = request.getOptions();
		GcsFilename filename = request.getGcsFilename();
		byte[] data = request.getData();

		GcsOutputChannel outputChannel = this.gcsService.createOrReplace(filename, options);
		outputChannel.write(ByteBuffer.wrap(data));
		outputChannel.close();
	}

	private GcsReadResults readFile(GcsStorageFileRequest request,
	                                boolean readMetadata) throws IOException {

		GcsFilename filename = request.getGcsFilename();
		GcsReadResults result = new GcsReadResults(filename);

		if (readMetadata) {
			GcsFileMetadata metadata = this.loadMetadata(filename);
			result.setMetadata(metadata);
		}

		byte[] bytes = this.readBytesFromFile(filename);
		result.setBytes(bytes);

		return result;
	}

	private byte[] readBytesFromFile(GcsFilename filename) throws IOException {
		GcsInputChannel readChannel = this.gcsService.openPrefetchingReadChannel(filename, 0, this.bufferSize);
		InputStream stream = Channels.newInputStream(readChannel);

		byte[] data = null;

		try {
			data = ByteStreams.toByteArray(stream);
		} finally {
			stream.close();
		}

		return data;
	}

	@Override
	public boolean deleteFile(StorableFile file) {
		GcsStorageFileRequest request = new GcsStorageFileRequest(this.bucket, file);
		return this.deleteFile(request);
	}

	public boolean deleteFile(GcsStorageFileRequest request) {
		boolean success = true;
		GcsFilename filename = request.getGcsFilename();

		try {
			this.gcsService.delete(filename);
		} catch (IOException e) {
			e.printStackTrace();
			success = false;
		}

		return success;
	}

	@Override
	public boolean fileExists(StorableFile file) throws IOException {
		boolean exists = true;

		try {
			this.loadMetadata(file);
		} catch (FileNotFoundException e) {
			exists = false;
		}

		return exists;
	}

	public boolean fileExists(GcsStorageFileRequest request) throws IOException {
		boolean exists = true;

		try {
			this.loadMetadata(request);
		} catch (FileNotFoundException e) {
			exists = false;
		}

		return exists;
	}

	/**
	 * Used as a wrapper for returned files.
	 *
	 * @author dereekb
	 */
	private static class GcsReadResults {

		private final GcsFilename filename;
		private byte[] bytes;
		private GcsFileMetadata metadata;

		public GcsReadResults(GcsFilename filename) {
			this.filename = filename;
		}

		public GcsFilename getFilename() {
			return this.filename;
		}

		public byte[] getBytes() {
			return this.bytes;
		}

		public void setBytes(byte[] bytes) {
			this.bytes = bytes;
		}

		public GcsFileMetadata getMetadata() {
			return this.metadata;
		}

		public void setMetadata(GcsFileMetadata metadata) {
			this.metadata = metadata;
		}

	}

}
