package com.dereekb.gae.web.api.model.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.dto.LoginData;
import com.dereekb.gae.web.api.model.crud.controller.EditModelController;
import com.dereekb.gae.web.api.model.crud.controller.EditModelControllerConversionDelegate;
import com.dereekb.gae.web.api.model.crud.controller.EditModelControllerDelegate;

@RestController
@RequestMapping("/login")
public class LoginEditController extends EditModelController<Login, LoginData> {

	public LoginEditController(EditModelControllerDelegate<Login> delegate,
	        EditModelControllerConversionDelegate<Login, LoginData> conversionDelegate) {
		super(delegate, conversionDelegate);
	}

}
