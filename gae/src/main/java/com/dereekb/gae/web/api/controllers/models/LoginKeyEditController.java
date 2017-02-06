package com.dereekb.gae.web.api.controllers.models;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.model.key.dto.LoginKeyData;
import com.dereekb.gae.web.api.model.controller.EditModelController;
import com.dereekb.gae.web.api.model.controller.EditModelControllerConversionDelegate;
import com.dereekb.gae.web.api.model.controller.EditModelControllerDelegate;

@RestController
@RequestMapping("/loginkey")
public class LoginKeyEditController extends EditModelController<LoginKey, LoginKeyData> {

	public LoginKeyEditController(EditModelControllerDelegate<LoginKey> delegate,
	        EditModelControllerConversionDelegate<LoginKey, LoginKeyData> conversionDelegate) {
		super(delegate, conversionDelegate);
	}

}
