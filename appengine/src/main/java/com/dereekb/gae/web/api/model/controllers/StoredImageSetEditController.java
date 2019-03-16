package com.dereekb.gae.web.api.model.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.model.stored.image.set.dto.StoredImageSetData;
import com.dereekb.gae.web.api.model.crud.controller.EditModelController;
import com.dereekb.gae.web.api.model.crud.controller.EditModelControllerConversionDelegate;
import com.dereekb.gae.web.api.model.crud.controller.EditModelControllerDelegate;

@RestController
@RequestMapping({ "/imageset", "/storedimageset" })
public class StoredImageSetEditController extends EditModelController<StoredImageSet, StoredImageSetData> {

	public StoredImageSetEditController(EditModelControllerDelegate<StoredImageSet> delegate,
	                                       EditModelControllerConversionDelegate<StoredImageSet, StoredImageSetData> conversionDelegate) {
		super(delegate, conversionDelegate);
	}

}
