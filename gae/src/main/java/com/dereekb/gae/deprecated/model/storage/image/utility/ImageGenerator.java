package com.thevisitcompany.gae.deprecated.model.storage.image.utility;

import com.thevisitcompany.gae.deprecated.model.storage.image.Image;
import com.thevisitcompany.gae.deprecated.model.storage.support.StorageModelGenerator;

public class ImageGenerator extends StorageModelGenerator<Image> {

	@Override
	public Image generateModel(Long identifier) {

		Image image = new Image(identifier);
		image.setName("A sample Image");
		image.setSummary("This is a sample image to use for testing. Images can have a name, summary, and location.");
		image.setLocation(this.locationGenerator.generate());

		return image;
	}

}
