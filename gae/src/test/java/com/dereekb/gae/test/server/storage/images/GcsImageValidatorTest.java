package com.dereekb.gae.test.server.storage.images;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dereekb.gae.server.storage.gcs.blobstore.images.ImageValidator;
import com.dereekb.gae.test.model.extension.generator.data.TestImageByteGenerator;
import com.dereekb.gae.test.model.extension.generator.data.TestRandomByteGenerator;
import com.google.appengine.api.images.Image.Format;
import com.google.appengine.tools.development.testing.LocalImagesServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class GcsImageValidatorTest {

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
	private static final Integer imageDimension = 512;

	@BeforeClass
	public static void setUpImageGenerator() {
		imageGenerator.setHeight(imageDimension);
		imageGenerator.setWidth(imageDimension);
		imageGenerator.setImageType("png");
	}

	@Test
	public void testValidator() {
		ImageValidator validator = new ImageValidator();
		validator.setMinHeight(imageDimension - (imageDimension / 2));
		validator.setMinWidth(imageDimension - (imageDimension / 2));

		Set<Format> acceptableFormats = new HashSet<Format>();
		acceptableFormats.add(Format.PNG);
		acceptableFormats.add(Format.JPEG);

		validator.setAcceptableFormats(acceptableFormats);

		byte[] bytes = imageGenerator.generateBytes();
		boolean isValid = validator.validateImage(bytes);

		Assert.assertTrue(isValid);
	}

	@Test
	public void testOnRandomBytes() {
		TestRandomByteGenerator byteGenerator = new TestRandomByteGenerator();

		byte[] bytes = byteGenerator.generateBytes();

		ImageValidator validator = new ImageValidator();
		Set<Format> acceptableFormats = new HashSet<Format>();
		acceptableFormats.add(Format.PNG);
		acceptableFormats.add(Format.JPEG);
		validator.setAcceptableFormats(acceptableFormats);

		boolean isValid = validator.validateImage(bytes);
		Assert.assertFalse(isValid);
	}

	@Test
	public void testDimensionValidation() {

		ImageValidator validator = new ImageValidator();
		validator.setMinHeight(imageDimension);
		validator.setMinWidth(imageDimension);

		byte[] bytes = imageGenerator.generateBytes();
		boolean isValid = validator.validateImage(bytes);
		Assert.assertTrue(isValid);

		validator.setMinHeight(imageDimension * 2);
		validator.setMinWidth(imageDimension * 2);

		isValid = validator.validateImage(bytes);
		Assert.assertFalse(isValid);
	}

	@Test
	public void testAspectRatioValidation() {

		TestImageByteGenerator imageGenerator = new TestImageByteGenerator();
		ImageValidator validator = new ImageValidator();

		validator.setAspectRatio(1.333333333);
		imageGenerator.setWidth(1600);
		imageGenerator.setHeight(1200);

		byte[] bytes = imageGenerator.generateBytes();
		boolean isValid = validator.validateImage(bytes);
		Assert.assertTrue(isValid);

		imageGenerator.setWidth(1600);
		imageGenerator.setHeight(1199);

		bytes = imageGenerator.generateBytes();
		isValid = validator.validateImage(bytes);
		Assert.assertFalse(isValid);

		validator.setAspectRatio(1.0);
		imageGenerator.setWidth(1600);
		imageGenerator.setHeight(1600);

		bytes = imageGenerator.generateBytes();
		isValid = validator.validateImage(bytes);
		Assert.assertTrue(isValid);

		imageGenerator.setWidth(1600);
		imageGenerator.setHeight(1599);

		bytes = imageGenerator.generateBytes();
		isValid = validator.validateImage(bytes);
		Assert.assertFalse(isValid);

		validator.setAspectRatio(1.6180); // Golden Ratio
		imageGenerator.setWidth(1618);
		imageGenerator.setHeight(1000);

		bytes = imageGenerator.generateBytes();
		isValid = validator.validateImage(bytes);
		Assert.assertTrue(isValid);

		imageGenerator.setWidth(1617);
		imageGenerator.setHeight(999);

		bytes = imageGenerator.generateBytes();
		isValid = validator.validateImage(bytes);
		Assert.assertFalse(isValid);
	}

	@Test
	public void testFormatValidation() {

		ImageValidator validator = new ImageValidator();
		Set<Format> acceptableFormats = new HashSet<Format>();
		acceptableFormats.add(Format.PNG);
		acceptableFormats.add(Format.JPEG);

		validator.setAcceptableFormats(acceptableFormats);

		byte[] bytes = imageGenerator.generateBytes();
		boolean isValid = validator.validateImage(bytes);
		Assert.assertTrue(isValid);

		Set<Format> noAcceptableFormats = new HashSet<Format>();

		try {
			validator.setAcceptableFormats(noAcceptableFormats);
			Assert.fail();
		} catch (IllegalArgumentException e) {

		}

		Set<Format> jpgOnlyFormats = new HashSet<Format>();
		jpgOnlyFormats.add(Format.JPEG);
		validator.setAcceptableFormats(jpgOnlyFormats);

		isValid = validator.validateImage(bytes);
		Assert.assertFalse(isValid);
	}

}
