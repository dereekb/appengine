package com.dereekb.gae.test.app.mock.server.api.google.cloud.storage;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dereekb.gae.server.api.google.cloud.storage.GoogleCloudStorageService;
import com.dereekb.gae.test.app.mock.context.AbstractAppContextOnlyTestingContext;
import com.dereekb.gae.utilities.data.StringUtility;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;

/**
 * Unit tests for the {@link GoogleCloudStorageService}.
 *
 * @author dereekb
 *
 */
public class GoogleCloudStorageTests extends AbstractAppContextOnlyTestingContext {

	private static final String TEST_BUCKET_NAME = "test_bucket";

	@Autowired
	private GoogleCloudStorageService storageService;

	@BeforeEach()
	private void resetBucket() {
		Storage storage = this.storageService.getGoogleCloudStorageService();
		storage.delete(TEST_BUCKET_NAME);
	}

	// MARK: Service
	@Test
	public void testGetService() {
		Storage storage = this.storageService.getGoogleCloudStorageService();
		assertNotNull(storage);
	}

	@Test
	public void testUploadingObject() throws UnsupportedEncodingException {
		Storage storage = this.storageService.getGoogleCloudStorageService();

		String testFileName = "test";

		BlobId blobId = BlobId.of(TEST_BUCKET_NAME, testFileName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();

		String blobTextString = "Hello, Cloud Storage!";
		byte[] blobBytes = StringUtility.getUTF8Bytes(blobTextString);

		Blob blob = storage.create(blobInfo, blobBytes);
		assertNotNull(blob);
	}

	// MARK: Full API Examples
	/**
	 * Test not supported by the in-memory system, but provided for usage
	 * example.
	 */
	@Test
	@Disabled
	public void testCreateBucket() {
		Storage storage = this.storageService.getGoogleCloudStorageService();

		BucketInfo bucketInfo = BucketInfo.of(TEST_BUCKET_NAME);
		Bucket bucket = storage.create(bucketInfo);

		assertNotNull(bucket);
	}

}
