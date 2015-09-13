package com.dereekb.gae.test.applications.api.api.upload;

import java.util.Collection;

import org.junit.Assert;
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
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.applications.api.api.upload.util.TestUploadedFileSetImpl;
import com.dereekb.gae.test.model.extension.generator.data.TestImageByteGenerator;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;

/**
 * Used for testing the "iconFileUploadHandler" configured in the api.
 *
 * @author dereekb
 *
 */
public class IconStoredImageUploadTest extends ApiApplicationTestContext {

	@Autowired
	@Qualifier("iconFileUploadHandler")
	private FileUploadHandlerDelegate uploadHandler;

	@Autowired
	@Qualifier("storedBlobRegistry")
	public Getter<StoredBlob> storedBlobGetter;

	@Autowired
	@Qualifier("storedImageRegistry")
	public Getter<StoredImage> storedImageGetter;

	@Test
	public void testValidUploadRequest() {
		TestImageByteGenerator generator = new TestImageByteGenerator(512, 512);
		TestUploadedFileSetImpl set = new TestUploadedFileSetImpl();

		set.addItem(generator.generateBytes(), "a", "image/png");
		set.addItem(generator.generateBytes(), "b", "image/png");

		Assert.assertFalse(set.getAllUploadedFiles().isEmpty());

		FileUploadHandlerDelegateResult result = this.uploadHandler.handleUploadedFiles(set);
		Collection<UploadedFileResult> uploadedFiles = result.getUsedFiles();

		Assert.assertTrue(uploadedFiles.size() == set.getAllUploadedFiles().size());
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
			Assert.assertTrue(storedImage.getBlob().equivalent(storedBlob.getObjectifyKey()));
		}

	}

	@Test
	public void testInvalidUploadRequest() {
		TestImageByteGenerator generator = new TestImageByteGenerator(1024, 1024);
		TestUploadedFileSetImpl set = new TestUploadedFileSetImpl();

		set.addItem(generator.generateBytes(), "tooBig", "image/png");

		generator.setHeight(128);
		set.addItem(generator.generateBytes(), "notOneToOne", "image/png");

		generator.setWidth(128);
		set.addItem(generator.generateBytes(), "tooSmall", "image/png");

		Assert.assertFalse(set.getAllUploadedFiles().isEmpty());

		FileUploadHandlerDelegateResult result = this.uploadHandler.handleUploadedFiles(set);
		Collection<UploadedFileResult> failedFiles = result.getFailedFiles();
		Collection<UploadedFileResult> uploadedFiles = result.getUsedFiles();

		Assert.assertTrue(failedFiles.size() == set.getAllUploadedFiles().size());
		Assert.assertTrue(uploadedFiles.isEmpty());
	}

}
