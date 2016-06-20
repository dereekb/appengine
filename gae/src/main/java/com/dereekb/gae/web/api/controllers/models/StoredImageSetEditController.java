package com.dereekb.gae.web.api.controllers.models;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.model.stored.image.set.dto.StoredImageSetData;
import com.dereekb.gae.web.api.model.controller.EditModelController;
import com.dereekb.gae.web.api.model.controller.EditModelControllerConversionDelegate;
import com.dereekb.gae.web.api.model.controller.EditModelControllerDelegate;

@RestController
@RequestMapping({ "/imageset", "/storedimageset" })
public class StoredImageSetEditController extends EditModelController<StoredImageSet, StoredImageSetData> {

	public StoredImageSetEditController(EditModelControllerDelegate<StoredImageSet> delegate,
	                                       EditModelControllerConversionDelegate<StoredImageSet, StoredImageSetData> conversionDelegate) {
		super(delegate, conversionDelegate);
	}

}
