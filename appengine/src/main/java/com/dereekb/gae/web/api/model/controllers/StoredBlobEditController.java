package com.dereekb.gae.web.api.model.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.dto.StoredBlobData;
import com.dereekb.gae.web.api.model.crud.controller.EditModelController;
import com.dereekb.gae.web.api.model.crud.controller.EditModelControllerConversionDelegate;
import com.dereekb.gae.web.api.model.crud.controller.EditModelControllerDelegate;

@RestController
@RequestMapping({ "/blob", "/storedblob" })
public class StoredBlobEditController extends EditModelController<StoredBlob, StoredBlobData> {

	public StoredBlobEditController(EditModelControllerDelegate<StoredBlob> delegate,
	        EditModelControllerConversionDelegate<StoredBlob, StoredBlobData> conversionDelegate) {
		super(delegate, conversionDelegate);
	}

}
