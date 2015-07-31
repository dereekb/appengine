package com.dereekb.gae.web.api.controllers.models;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.web.api.model.controller.ReadModelController;
import com.dereekb.gae.web.api.model.controller.ReadModelControllerConversionDelegate;
import com.dereekb.gae.web.api.model.controller.ReadModelControllerDelegate;

@RestController
@RequestMapping("/image")
public class StoredImageReadController extends ReadModelController<StoredImage> {

	public StoredImageReadController(ReadModelControllerDelegate<StoredImage> delegate,
	        ReadModelControllerConversionDelegate<StoredImage> conversionDelegate) {
		super(delegate, conversionDelegate);
	}

}
