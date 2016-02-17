package com.dereekb.gae.web.api.controllers.models;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.dto.StoredImageData;
import com.dereekb.gae.web.api.model.controller.EditModelController;
import com.dereekb.gae.web.api.model.controller.EditModelControllerConversionDelegate;
import com.dereekb.gae.web.api.model.controller.EditModelControllerDelegate;

@RestController
@RequestMapping("/image")
public class StoredImageEditController extends EditModelController<StoredImage, StoredImageData> {

	public StoredImageEditController(EditModelControllerDelegate<StoredImage> delegate,
	        EditModelControllerConversionDelegate<StoredImage, StoredImageData> conversionDelegate) {
		super(delegate, conversionDelegate);
	}

}
