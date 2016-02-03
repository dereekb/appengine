package com.dereekb.gae.server.storage.services.gcs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.Arrays;

import com.dereekb.gae.server.storage.accessor.StorageSystem;
import com.dereekb.gae.server.storage.object.file.StorableContent;
import com.dereekb.gae.server.storage.object.file.StorableFile;
import com.dereekb.gae.server.storage.object.file.options.StorableFileOptions;
import com.dereekb.gae.server.storage.services.gcs.impl.GcsStorableFileRequestImpl;
import com.dereekb.gae.server.storage.services.gcs.object.request.GcsStorableFileRequest;
import com.dereekb.gae.server.storage.services.gcs.object.request.GcsStorableFileSaveRequest;
import com.dereekb.gae.server.storage.services.gcs.object.request.GcsStorableFileSaveRequestBuilder;
import com.dereekb.gae.utilities.misc.bit.ByteSizeUtility;
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
 * {@link StorageSystem} implementation.
 *
 * @author dereekb
 *
 */
public class GcsStorageSystem
        implements StorageSystem {

	private static final int DEFAULT_READ_BUFFER_SIZE = ByteSizeUtility.megaBytes(1).intValue();
	private static final String STORAGE_SYSTEM_NAME = "Google Cloud Storage";

	private GcsService gcsService;

	private String gcsBucket;

	private GcsStorableFileSaveRequestBuilder saveRequestBuilder;

	private int readBufferSize = DEFAULT_READ_BUFFER_SIZE;

	public GcsStorageSystem(String gcsBucket) {
		this(gcsBucket, null);
    }

	public GcsStorageSystem(String gcsBucket,
	            	        GcsStorableFileSaveRequestBuilder saveRequestBuilder) {
		this(defaultService(), gcsBucket, saveRequestBuilder);
	}

	public GcsStorageSystem(GcsService gcsService,
	        String gcsBucket,
	        GcsStorableFileSaveRequestBuilder saveRequestBuilder) {
		this.gcsService = gcsService;
		this.gcsBucket = gcsBucket;
		this.saveRequestBuilder = saveRequestBuilder;
	}

	public static GcsService defaultService() {
		return GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());
	}

	public GcsService getGcsService() {
		return this.gcsService;
	}

	public void setGcsService(GcsService gcsService) {
		this.gcsService = gcsService;
	}

	public String getGcsBucket() {
		return this.gcsBucket;
	}

	public void setGcsBucket(String gcsBucket) {
		this.gcsBucket = gcsBucket;
	}

	public GcsStorableFileSaveRequestBuilder getSaveRequestBuilder() {
		return this.saveRequestBuilder;
	}

	public void setSaveRequestBuilder(GcsStorableFileSaveRequestBuilder saveRequestBuilder) {
		this.saveRequestBuilder = saveRequestBuilder;
	}

	public int getReadBufferSize() {
		return this.readBufferSize;
	}

	public void setReadBufferSize(int readBufferSize) {
		this.readBufferSize = readBufferSize;
	}

	//MARK: StorageSystem
	@Override
    public String getStorageSystemName() {
		return STORAGE_SYSTEM_NAME;
    }

	// MARK: StorageReader
	@Override
    public StorableContent loadFile(StorableFile file) throws IOException {
		GcsStorableFileRequest request = this.makeGcsFileRequest(file);
		byte[] bytes = this.readBytes(request);
		GcsStorableContentReading reading = new GcsStorableContentReading(file, request.getGcsFilename(), bytes);
		return reading;
    }

	@Override
    public boolean fileExists(StorableFile file) throws IOException {
		GcsStorableFileRequest request = this.makeGcsFileRequest(file);
		boolean exists;

		try {
			GcsFileMetadata data = this.loadMetadata(request);
			exists = (data != null);
		} catch (FileNotFoundException e) {
			exists = false;
		}

		return exists;
    }

	// MARK: StorageWriter
	@Override
	public void saveFile(StorableContent content) throws IOException {
		GcsStorableFileSaveRequest saveRequest = this.makeGcsFileSaveRequest(content);
		this.saveFile(saveRequest);
    }

	@Override
	public boolean deleteFile(StorableFile file) throws IOException {
		GcsStorableFileRequest request = this.makeGcsFileRequest(file);
		return this.deleteFile(request);
    }

	//MARK: Gcs Storage System
	public boolean saveFile(GcsStorableFileSaveRequest request) {
		boolean success = true;

		try {
			GcsFileOptions options = request.getGcsFileOptions();
			GcsFilename filename = request.getGcsFilename();
			byte[] data = request.getFileData();

			GcsOutputChannel outputChannel = this.gcsService.createOrReplace(filename, options);
			outputChannel.write(ByteBuffer.wrap(data));
			outputChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
			success = false;
		}

		return success;
	}

	public GcsFileMetadata loadMetadata(GcsStorableFileRequest request) throws IOException {
		GcsFilename filename = request.getGcsFilename();
		return this.gcsService.getMetadata(filename);
	}

	public boolean deleteFile(GcsStorableFileRequest request) throws IOException {
		GcsFilename filename = request.getGcsFilename();
		return this.gcsService.delete(filename);
	}

	// MARK: Internal
	public GcsStorableFileRequest makeGcsFileRequest(StorableFile file) {
		return new GcsStorableFileRequestImpl(this.gcsBucket, file);
	}

	private GcsStorableFileSaveRequest makeGcsFileSaveRequest(StorableContent content) {
		return this.saveRequestBuilder.buildRequest(this.gcsBucket, content);
	}

	private byte[] readBytes(GcsStorableFileRequest request) throws IOException {
		GcsFilename filename = request.getGcsFilename();
		GcsInputChannel readChannel = this.gcsService.openPrefetchingReadChannel(filename, 0, this.readBufferSize);
		InputStream stream = Channels.newInputStream(readChannel);

		byte[] data = null;

		try {
			data = ByteStreams.toByteArray(stream);
		} finally {
			stream.close();
		}

		return data;
	}

	/**
	 * {@link StorableContent} returned from a file reading.
	 *
	 * <p>
	 * Provides lazy loading for metadata and other components.
	 * </p>
	 *
	 * @author dereekb
	 *
	 */
	private class GcsStorableContentReading
	        implements GcsStorableFileRequest, StorableContent {

		private final StorableFile file;
		private final GcsFilename gcsFilename;
		private final byte[] bytes;

		private GcsFileMetadata metaData = null;

		private GcsStorableContentReading(StorableFile file, GcsFilename gcsFilename, byte[] bytes) {
			this.file = file;
			this.gcsFilename = gcsFilename;
			this.bytes = bytes;
		}

		@Override
		public byte[] getFileData() {
			return this.bytes;
		}

		@Override
		public String getFilePath() {
			return this.file.getFilePath();
		}

		@Override
		public String getFilename() {
			return this.file.getFilename();
		}

		@Override
		public String getContentType() {
			GcsFileMetadata data = this.getMetadata();
			GcsFileOptions options = data.getOptions();
			return options.getMimeType();
		}

		@Override
		public StorableFileOptions getOptions() {
			return null;
		}

		// MARK: GcsStorableFile
		@Override
		public GcsFilename getGcsFilename() {
			return this.gcsFilename;
		}

		public GcsFileMetadata getMetadata() throws RuntimeException {
			if (this.metaData == null) {
				try {
					this.metaData = GcsStorageSystem.this.loadMetadata(this);
				} catch (IOException e) {
					throw new RuntimeException("Metadata was unavailable.");
				}
			}

			return this.metaData;
		}

		@Override
		public String toString() {
			return "GcsStorableContentReading [file=" + this.file + ", gcsFilename=" + this.gcsFilename + ", bytes="
			        + Arrays.toString(this.bytes) + ", metaData=" + this.metaData + "]";
		}

	}

}
