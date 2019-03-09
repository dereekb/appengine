package com.dereekb.gae.web.api.model.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.extras.gen.test.model.foo.Foo;
import com.dereekb.gae.extras.gen.test.model.foo.dto.FooData;
import com.dereekb.gae.web.api.model.crud.controller.EditModelController;
import com.dereekb.gae.web.api.model.crud.controller.EditModelControllerConversionDelegate;
import com.dereekb.gae.web.api.model.crud.controller.EditModelControllerDelegate;

@RestController
@RequestMapping("/foo")
public class FooEditController extends EditModelController<Foo, FooData> {

	public FooEditController(EditModelControllerDelegate<Foo> delegate,
	        EditModelControllerConversionDelegate<Foo, FooData> conversionDelegate) {
		super(delegate, conversionDelegate);
	}

}
