package com.dereekb.gae.test.applications.api.api.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.storage.upload.handler.impl.delegate.FileUploadHandlerDelegate;
import com.dereekb.gae.test.applications.api.api.upload.util.TestUploadedFileSetImpl;
import com.dereekb.gae.test.model.extension.generator.data.TestImageByteGenerator;

/**
 * Used for testing the "iconFileUploadHandler" configured in the api.
 *
 * @author dereekb
 *
 */
public class IconStoredImageUploadTest extends AbstractStoredImageUploadTest {

	@Override
    @Autowired
	@Qualifier("iconFileUploadHandler")
	public void setUploadHandler(FileUploadHandlerDelegate uploadHandler) {
		super.setUploadHandler(uploadHandler);
	}

	@Override
	public TestUploadedFileSetImpl generateValidTestSet() {
		TestImageByteGenerator generator = new TestImageByteGenerator(512, 512);
		TestUploadedFileSetImpl set = new TestUploadedFileSetImpl();

		set.addItem(generator.generateBytes(), "a", "image/png");
		set.addItem(generator.generateBytes(), "b", "image/png");

		return set;
	}

	@Override
	public TestUploadedFileSetImpl generateInvalidTestSet() {
		TestImageByteGenerator generator = new TestImageByteGenerator(1024, 1024);
		TestUploadedFileSetImpl set = new TestUploadedFileSetImpl();

		set.addItem(generator.generateBytes(), "tooBig", "image/png");

		generator.setHeight(128);
		set.addItem(generator.generateBytes(), "notOneToOne", "image/png");

		generator.setWidth(128);
		set.addItem(generator.generateBytes(), "tooSmall", "image/png");

		return set;
	}

}
