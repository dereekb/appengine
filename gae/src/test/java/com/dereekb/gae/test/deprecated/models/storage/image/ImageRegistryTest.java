package com.thevisitcompany.gae.test.deprecated.models.storage.image;

import com.thevisitcompany.gae.deprecated.model.storage.image.Image;
import com.thevisitcompany.gae.deprecated.model.storage.image.ImageRegistry;
import com.thevisitcompany.gae.deprecated.model.storage.image.utility.ImageGenerator;
import com.thevisitcompany.gae.test.deprecated.models.support.registry.AbstractModelRegistryTest;

public class ImageRegistryTest extends AbstractModelRegistryTest<Image> {

	private final static ImageRegistry registry = new ImageRegistry();
	private final static ImageGenerator generator = new ImageGenerator();

	public ImageRegistryTest() {
		super(registry);
	}

	@Override
	protected Image generateModel() {
		return generator.generateModel(null);
	}

}
