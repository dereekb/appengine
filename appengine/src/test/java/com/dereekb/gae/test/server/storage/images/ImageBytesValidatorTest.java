package com.dereekb.gae.test.server.storage.images;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dereekb.gae.server.storage.upload.data.image.ImageBytesValidator;
import com.dereekb.gae.test.model.extension.generator.data.TestImageByteGenerator;
import com.dereekb.gae.test.model.extension.generator.data.TestRandomByteGenerator;
import com.google.appengine.api.images.Image.Format;
import com.google.appengine.tools.development.testing.LocalImagesServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class ImageBytesValidatorTest {

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
	private static final Integer imageDimension = 512;

	@BeforeAll
	public static void setUpImageGenerator() {
		imageGenerator.setHeight(imageDimension);
		imageGenerator.setWidth(imageDimension);
		imageGenerator.setImageType("png");
	}

	@Test
	public void testValidator() {
		ImageBytesValidator validator = new ImageBytesValidator();
		validator.setMinHeight(imageDimension - (imageDimension / 2));
		validator.setMinWidth(imageDimension - (imageDimension / 2));

		Set<Format> acceptableFormats = new HashSet<Format>();
		acceptableFormats.add(Format.PNG);
		acceptableFormats.add(Format.JPEG);

		validator.setAcceptableFormats(acceptableFormats);

		byte[] bytes = imageGenerator.generateBytes();
		boolean isValid = validator.safeValidateContent(bytes);

		assertTrue(isValid);
	}

	@Test
	public void testOnRandomBytes() {
		TestRandomByteGenerator byteGenerator = new TestRandomByteGenerator();

		byte[] bytes = byteGenerator.generateBytes();

		ImageBytesValidator validator = new ImageBytesValidator();
		Set<Format> acceptableFormats = new HashSet<Format>();
		acceptableFormats.add(Format.PNG);
		acceptableFormats.add(Format.JPEG);
		validator.setAcceptableFormats(acceptableFormats);

		try {
			validator.safeValidateContent(bytes);
			fail();
		} catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void testDimensionValidation() {

		ImageBytesValidator validator = new ImageBytesValidator();
		validator.setMinHeight(imageDimension);
		validator.setMinWidth(imageDimension);

		byte[] bytes = imageGenerator.generateBytes();
		boolean isValid = validator.safeValidateContent(bytes);
		assertTrue(isValid);

		validator.setMinHeight(imageDimension * 2);
		validator.setMinWidth(imageDimension * 2);

		isValid = validator.safeValidateContent(bytes);
		assertFalse(isValid);
	}

	@Test
	public void testAspectRatioValidation() {

		TestImageByteGenerator imageGenerator = new TestImageByteGenerator();
		ImageBytesValidator validator = new ImageBytesValidator();

		validator.setAspectRatio(1.333333333);
		imageGenerator.setWidth(1600);
		imageGenerator.setHeight(1200);

		byte[] bytes = imageGenerator.generateBytes();
		boolean isValid = validator.safeValidateContent(bytes);
		assertTrue(isValid);

		imageGenerator.setWidth(1600);
		imageGenerator.setHeight(1199);

		bytes = imageGenerator.generateBytes();
		isValid = validator.safeValidateContent(bytes);
		assertFalse(isValid);

		validator.setAspectRatio(1.0);
		imageGenerator.setWidth(1600);
		imageGenerator.setHeight(1600);

		bytes = imageGenerator.generateBytes();
		isValid = validator.safeValidateContent(bytes);
		assertTrue(isValid);

		imageGenerator.setWidth(1600);
		imageGenerator.setHeight(1599);

		bytes = imageGenerator.generateBytes();
		isValid = validator.safeValidateContent(bytes);
		assertFalse(isValid);

		validator.setAspectRatio(1.6180); // Golden Ratio
		imageGenerator.setWidth(1618);
		imageGenerator.setHeight(1000);

		bytes = imageGenerator.generateBytes();
		isValid = validator.safeValidateContent(bytes);
		assertTrue(isValid);

		imageGenerator.setWidth(1617);
		imageGenerator.setHeight(999);

		bytes = imageGenerator.generateBytes();
		isValid = validator.safeValidateContent(bytes);
		assertFalse(isValid);
	}

	@Test
	public void testFormatValidation() {

		ImageBytesValidator validator = new ImageBytesValidator();
		Set<Format> acceptableFormats = new HashSet<Format>();
		acceptableFormats.add(Format.PNG);
		acceptableFormats.add(Format.JPEG);

		validator.setAcceptableFormats(acceptableFormats);

		byte[] bytes = imageGenerator.generateBytes();
		boolean isValid = validator.safeValidateContent(bytes);
		assertTrue(isValid);

		// All formats allowed
		Set<Format> noAcceptableFormats = new HashSet<Format>();
		validator.setAcceptableFormats(noAcceptableFormats);

		Set<Format> jpgOnlyFormats = new HashSet<Format>();
		jpgOnlyFormats.add(Format.JPEG);
		validator.setAcceptableFormats(jpgOnlyFormats);

		isValid = validator.safeValidateContent(bytes);
		assertFalse(isValid);
	}

}
