package com.dereekb.gae.web.api.controllers.models;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.web.api.model.controller.ReadModelController;
import com.dereekb.gae.web.api.model.controller.ReadModelControllerConversionDelegate;
import com.dereekb.gae.web.api.model.controller.ReadModelControllerDelegate;

@RestController
@RequestMapping("/imageset")
public class StoredImageSetReadController extends ReadModelController<StoredImageSet> {

	public StoredImageSetReadController(ReadModelControllerDelegate<StoredImageSet> delegate,
	        ReadModelControllerConversionDelegate<StoredImageSet> conversionDelegate) {
		super(delegate, conversionDelegate);
	}

}
