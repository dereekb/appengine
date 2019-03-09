package com.dereekb.gae.web.api.controllers.models;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.dto.LoginPointerData;
import com.dereekb.gae.web.api.model.controller.EditModelController;
import com.dereekb.gae.web.api.model.controller.EditModelControllerConversionDelegate;
import com.dereekb.gae.web.api.model.controller.EditModelControllerDelegate;

@RestController
@RequestMapping("/loginpointer")
public class LoginPointerEditController extends EditModelController<LoginPointer, LoginPointerData> {

	public LoginPointerEditController(EditModelControllerDelegate<LoginPointer> delegate,
	        EditModelControllerConversionDelegate<LoginPointer, LoginPointerData> conversionDelegate) {
		super(delegate, conversionDelegate);
	}

}
