package com.dereekb.gae.test.applications.api.api.upload;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.storage.UploadedStoredBlobData;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.storage.upload.handler.impl.delegate.FileUploadHandlerDelegate;
import com.dereekb.gae.server.storage.upload.handler.impl.delegate.FileUploadHandlerDelegateResult;
import com.dereekb.gae.server.storage.upload.handler.impl.delegate.UploadedFileResult;
import com.dereekb.gae.server.storage.upload.reader.UploadedFileSet;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.applications.api.api.upload.util.TestUploadedFileSetImpl;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;

/**
 * Used for testing a {@link FileUploadHandlerDelegate} configured in the api.
 *
 * @author dereekb
 *
 */
@Ignore
public abstract class AbstractStoredImageUploadTest extends ApiApplicationTestContext {

	private FileUploadHandlerDelegate uploadHandler;

	@Autowired
	@Qualifier("storedBlobRegistry")
	public Getter<StoredBlob> storedBlobGetter;

	@Autowired
	@Qualifier("storedImageRegistry")
	public Getter<StoredImage> storedImageGetter;

	public FileUploadHandlerDelegate getUploadHandler() {
		return this.uploadHandler;
	}

	public void setUploadHandler(FileUploadHandlerDelegate uploadHandler) {
		this.uploadHandler = uploadHandler;
	}

	public Getter<StoredBlob> getStoredBlobGetter() {
		return this.storedBlobGetter;
	}

	public void setStoredBlobGetter(Getter<StoredBlob> storedBlobGetter) {
		this.storedBlobGetter = storedBlobGetter;
	}

	public Getter<StoredImage> getStoredImageGetter() {
		return this.storedImageGetter;
	}

	public void setStoredImageGetter(Getter<StoredImage> storedImageGetter) {
		this.storedImageGetter = storedImageGetter;
	}

	// MARK: Upload
	@Test
	public void testValidUploadRequest() {
		UploadedFileSet set = this.generateValidTestSet();

		Assert.assertFalse(set.getAllUploadedFiles().isEmpty());

		FileUploadHandlerDelegateResult result = this.uploadHandler.handleUploadedFiles(set);

		Collection<UploadedFileResult> uploadedFiles = result.getUsedFiles();

		Assert.assertTrue(uploadedFiles.size() == set.getAllUploadedFiles().size());
		this.checkStoredBlobUploadedFileResults(uploadedFiles);
	}

	public abstract TestUploadedFileSetImpl generateValidTestSet();

	private void checkStoredBlobUploadedFileResults(Collection<UploadedFileResult> uploadedFiles) {
		for (UploadedFileResult uploadedFile : uploadedFiles) {
			ApiResponseData data = uploadedFile.getResponseData();
			UploadedStoredBlobData uploadedBlobData = (UploadedStoredBlobData) data.getResponseData();

			String storedBlobIdString = uploadedBlobData.getIdentifier();
			StoredBlob storedBlob = this.storedBlobGetter.get(ModelKey.convert(storedBlobIdString));

			Assert.assertNotNull(storedBlob);

			String descriptorId = uploadedBlobData.getDescriptorId();
			String descriptorType = uploadedBlobData.getDescriptorType();

			Assert.assertNotNull(descriptorId);
			Assert.assertNotNull(descriptorType);

			StoredImage storedImage = this.storedImageGetter.get(ModelKey.convert(descriptorId));

			Assert.assertNotNull(storedImage);
			Assert.assertTrue(storedImage.getStoredBlob().equivalent(storedBlob.getObjectifyKey()));
		}
	}

	// MARK: Download
	@Test
	public void testInvalidUploadRequest() {
		UploadedFileSet set = this.generateInvalidTestSet();

		Assert.assertFalse(set.getAllUploadedFiles().isEmpty());

		FileUploadHandlerDelegateResult result = this.uploadHandler.handleUploadedFiles(set);

		Collection<UploadedFileResult> failedFiles = result.getFailedFiles();
		Collection<UploadedFileResult> uploadedFiles = result.getUsedFiles();

		Assert.assertTrue(failedFiles.size() == set.getAllUploadedFiles().size());
		Assert.assertTrue(uploadedFiles.isEmpty());
	}

	public abstract TestUploadedFileSetImpl generateInvalidTestSet();

}
