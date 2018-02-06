package com.dereekb.gae.web.api.model.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.app.model.hook.AppHook;
import com.dereekb.gae.server.app.model.hook.dto.AppHookData;
import com.dereekb.gae.web.api.model.crud.controller.EditModelController;
import com.dereekb.gae.web.api.model.crud.controller.EditModelControllerConversionDelegate;
import com.dereekb.gae.web.api.model.crud.controller.EditModelControllerDelegate;

@RestController
@RequestMapping("/apphook")
public class AppHookEditController extends EditModelController<AppHook, AppHookData> {

	public AppHookEditController(EditModelControllerDelegate<AppHook> delegate,
	        EditModelControllerConversionDelegate<AppHook, AppHookData> conversionDelegate) {
		super(delegate, conversionDelegate);
	}

}