package com.dereekb.gae.test.server.storage.images;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dereekb.gae.server.storage.gcs.blobstore.images.ImageEditor;
import com.dereekb.gae.server.storage.gcs.blobstore.images.ImageEditor.ImageEditorInstance;
import com.dereekb.gae.test.model.extension.generator.data.TestImageByteGenerator;
import com.google.appengine.api.images.Image;
import com.google.appengine.tools.development.testing.LocalImagesServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class GcsImageEditorTest {

	@Autowired
	protected LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalImagesServiceTestConfig());

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	private static final TestImageByteGenerator imageGenerator = new TestImageByteGenerator();

	@BeforeClass
	public static void setUpImageGenerator() {
		imageGenerator.setHeight(512);
		imageGenerator.setWidth(512);
		imageGenerator.setImageType("png");
	}

	@Test
	public void testImageEditorInstanceCreation() {

		ImageEditor editor = new ImageEditor();
		byte[] bytes = imageGenerator.generateBytes();

		ImageEditorInstance forBytes = editor.forBytes(bytes);
		Assert.assertNotNull(forBytes);

		Image testImage = forBytes.getImage();
		ImageEditorInstance forImage = editor.forImage(testImage);
		Assert.assertNotNull(forImage);

	}

	@Test
	public void testImageEditorFunctions() {

		ImageEditor editor = new ImageEditor();
		byte[] bytes = imageGenerator.generateBytes();

		ImageEditorInstance cropTest = editor.forBytes(bytes);
		Assert.assertNotNull(cropTest);

		cropTest.cropImage(0.5, 0.5);
		Image cropImage = cropTest.applyTransforms();
		Assert.assertNotNull(cropImage);

		ImageEditorInstance rotateTest = editor.forBytes(bytes);
		Assert.assertNotNull(rotateTest);

		rotateTest.rotateImage(90);
		Image rotateImage = rotateTest.applyTransforms();
		Assert.assertNotNull(rotateImage);

		ImageEditorInstance resizeTest = editor.forBytes(bytes);
		Assert.assertNotNull(resizeTest);

		resizeTest.resizeImage(90, 90);
		Image resizeImage = resizeTest.applyTransforms();
		Assert.assertNotNull(resizeImage);

	}

	@Test
	public void testImageEditorMultiTransformFunction() {

		ImageEditor editor = new ImageEditor();
		byte[] bytes = imageGenerator.generateBytes();

		ImageEditorInstance instance = editor.forBytes(bytes);
		instance.cropImage(0.5, 0.5);
		instance.rotateImage(90);
		instance.resizeImage(90, 90);

		Image finalImage = instance.applyTransforms();
		Assert.assertNotNull(finalImage);
	}
}
