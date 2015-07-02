package com.thevisitcompany.gae.deprecated.model.storage.image;

import com.thevisitcompany.gae.deprecated.model.storage.support.StorageModelFunctionsServiceFactory;

public class ImageFunctionsServiceFactory extends StorageModelFunctionsServiceFactory<ImageFunctionsService, Image> {

	@Override
	protected ImageFunctionsService newService() {
		ImageFunctionsService service = new ImageFunctionsService();
		return service;
	}

}
