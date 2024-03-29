package com.dereekb.gae.test.server.storage.images;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dereekb.gae.server.storage.services.images.ImageBytesEditor;
import com.dereekb.gae.test.model.extension.generator.data.TestImageByteGenerator;
import com.google.appengine.api.images.Image;
import com.google.appengine.tools.development.testing.LocalImagesServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class ImageBytesEditorTest {

	@Autowired
	protected LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalImagesServiceTestConfig());

	@BeforeEach
	public void setUp() {
		this.helper.setUp();
	}

	@AfterEach
	public void tearDown() {
		this.helper.tearDown();
	}

	private static final TestImageByteGenerator imageGenerator = new TestImageByteGenerator();

	@BeforeAll
	public static void setUpImageGenerator() {
		imageGenerator.setHeight(512);
		imageGenerator.setWidth(512);
		imageGenerator.setImageType("png");
	}

	@Test
	public void testImageBytesEditorInstanceCreation() {

		ImageBytesEditor editor = new ImageBytesEditor();
		byte[] bytes = imageGenerator.generateBytes();

		ImageBytesEditor.Instance newInstance = editor.newInstance(bytes);
		assertNotNull(newInstance);

		Image testImage = newInstance.getImage();
		ImageBytesEditor.Instance forImage = editor.newInstance(testImage);
		assertNotNull(forImage);

	}

	@Test
	public void testImageBytesEditorFunctions() {

		ImageBytesEditor editor = new ImageBytesEditor();
		byte[] bytes = imageGenerator.generateBytes();

		ImageBytesEditor.Instance cropTest = editor.newInstance(bytes);
		assertNotNull(cropTest);

		cropTest.cropImage(0.5, 0.5);
		Image cropImage = cropTest.createEditedImage();
		assertNotNull(cropImage);

		ImageBytesEditor.Instance rotateTest = editor.newInstance(bytes);
		assertNotNull(rotateTest);

		rotateTest.rotateImage(90);
		Image rotateImage = rotateTest.createEditedImage();
		assertNotNull(rotateImage);

		ImageBytesEditor.Instance resizeTest = editor.newInstance(bytes);
		assertNotNull(resizeTest);

		resizeTest.resizeImage(90, 90);
		Image resizeImage = resizeTest.createEditedImage();
		assertNotNull(resizeImage);

	}

	@Test
	public void testImageBytesEditorMultiTransformFunction() {

		ImageBytesEditor editor = new ImageBytesEditor();
		byte[] bytes = imageGenerator.generateBytes();

		ImageBytesEditor.Instance instance = editor.newInstance(bytes);
		instance.cropImage(0.5, 0.5);
		instance.rotateImage(90);
		instance.resizeImage(90, 90);

		Image finalImage = instance.createEditedImage();
		assertNotNull(finalImage);
	}
}
