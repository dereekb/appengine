package com.dereekb.gae.web.api.model.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.dto.StoredImageData;
import com.dereekb.gae.web.api.model.crud.controller.EditModelController;
import com.dereekb.gae.web.api.model.crud.controller.EditModelControllerConversionDelegate;
import com.dereekb.gae.web.api.model.crud.controller.EditModelControllerDelegate;

@RestController
@RequestMapping({ "/image", "/storedimage" })
public class StoredImageEditController extends EditModelController<StoredImage, StoredImageData> {

	public StoredImageEditController(EditModelControllerDelegate<StoredImage> delegate,
	        EditModelControllerConversionDelegate<StoredImage, StoredImageData> conversionDelegate) {
		super(delegate, conversionDelegate);
	}

}
