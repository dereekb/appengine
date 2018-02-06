package com.dereekb.gae.web.api.model.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.app.dto.AppData;
import com.dereekb.gae.web.api.model.crud.controller.EditModelController;
import com.dereekb.gae.web.api.model.crud.controller.EditModelControllerConversionDelegate;
import com.dereekb.gae.web.api.model.crud.controller.EditModelControllerDelegate;

@RestController
@RequestMapping("/app")
public class AppEditController extends EditModelController<App, AppData> {

	public AppEditController(EditModelControllerDelegate<App> delegate,
	        EditModelControllerConversionDelegate<App, AppData> conversionDelegate) {
		super(delegate, conversionDelegate);
	}

}
