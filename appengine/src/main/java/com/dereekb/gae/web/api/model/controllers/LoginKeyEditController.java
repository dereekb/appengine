package com.dereekb.gae.web.api.model.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.model.key.dto.LoginKeyData;
import com.dereekb.gae.web.api.model.crud.controller.EditModelController;
import com.dereekb.gae.web.api.model.crud.controller.EditModelControllerConversionDelegate;
import com.dereekb.gae.web.api.model.crud.controller.EditModelControllerDelegate;

@RestController
@RequestMapping("/loginkey")
public class LoginKeyEditController extends EditModelController<LoginKey, LoginKeyData> {

	public LoginKeyEditController(EditModelControllerDelegate<LoginKey> delegate,
	        EditModelControllerConversionDelegate<LoginKey, LoginKeyData> conversionDelegate) {
		super(delegate, conversionDelegate);
	}

}
