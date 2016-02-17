package com.dereekb.gae.web.api.controllers.models;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.web.api.model.controller.ReadModelController;
import com.dereekb.gae.web.api.model.controller.ReadModelControllerConversionDelegate;
import com.dereekb.gae.web.api.model.controller.ReadModelControllerDelegate;

@RestController
@RequestMapping("/blob")
public class StoredBlobReadController extends ReadModelController<StoredBlob> {

	public StoredBlobReadController(ReadModelControllerDelegate<StoredBlob> delegate,
	        ReadModelControllerConversionDelegate<StoredBlob> conversionDelegate) {
		super(delegate, conversionDelegate);
	}

}
