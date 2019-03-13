package com.dereekb.gae.test.applications.api.api.upload;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.storage.upload.handler.impl.delegate.FileUploadHandlerDelegate;
import com.dereekb.gae.test.deprecated.applications.api.api.upload.util.TestUploadedFileSetImpl;
import com.dereekb.gae.test.model.extension.generator.data.TestImageByteGenerator;

/**
 * Used for testing the "snImageFileUploadHandler" configured in the api.
 *
 * @author dereekb
 *
 */
@Disabled
public class HdImageStoredImageUploadTest extends AbstractStoredImageUploadTest {

	@Override
    @Autowired
	@Qualifier("hdImageFileUploadHandler")
	public void setUploadHandler(FileUploadHandlerDelegate uploadHandler) {
		super.setUploadHandler(uploadHandler);
	}

	@Override
	public TestUploadedFileSetImpl generateValidTestSet() {
		TestImageByteGenerator generator = new TestImageByteGenerator(1280, 720);
		TestUploadedFileSetImpl set = new TestUploadedFileSetImpl();

		set.addItem(generator.generateBytes(), "a", "image/png");
		set.addItem(generator.generateBytes(), "b", "image/png");

		return set;
	}

	@Override
	public TestUploadedFileSetImpl generateInvalidTestSet() {
		TestImageByteGenerator generator = new TestImageByteGenerator(1936, 1089);
		TestUploadedFileSetImpl set = new TestUploadedFileSetImpl();

		set.addItem(generator.generateBytes(), "tooBig", "image/png");

		generator.setWidth(1264);
		set.addItem(generator.generateBytes(), "notSixteenToNine", "image/png");

		generator.setWidth(711);
		set.addItem(generator.generateBytes(), "tooSmall", "image/png");

		return set;
	}

}
