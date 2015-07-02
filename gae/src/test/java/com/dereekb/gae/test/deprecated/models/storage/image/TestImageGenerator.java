package com.thevisitcompany.gae.test.deprecated.models.storage.image;

import com.thevisitcompany.gae.deprecated.model.storage.image.Image;
import com.thevisitcompany.gae.test.deprecated.models.generator.TestingGenerator;
import com.thevisitcompany.gae.test.deprecated.server.storage.gcs.generator.TestGcsDataGenerator;

public final class TestImageGenerator extends TestingGenerator<Image, Long> {

	private TestGcsDataGenerator<Image> dataGenerator;

	@Override
	protected Image generateModelWithType(String type) {
		Image image = this.generateModelWithType(null, type);
		return image;
	}

	@Override
	protected Image generateModelWithType(Long identifier,
	                                      String type) {
		Image image = this.generateModel(identifier);

		if (this.dataGenerator != null) {
			dataGenerator.generateData(image, image);
		}

		return image;
	}

	public TestGcsDataGenerator<Image> getDataGenerator() {
		return dataGenerator;
	}

	public void setDataGenerator(TestGcsDataGenerator<Image> dataGenerator) {
		this.dataGenerator = dataGenerator;
	}

}
