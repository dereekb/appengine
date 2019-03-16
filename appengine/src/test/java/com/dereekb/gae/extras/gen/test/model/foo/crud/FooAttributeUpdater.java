package com.dereekb.gae.extras.gen.test.model.foo.crud;

import com.dereekb.gae.extras.gen.test.model.foo.Foo;
import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

public class FooAttributeUpdater
        implements UpdateTaskDelegate<Foo> {

	@Override
	public void updateTarget(Foo target,
	                         Foo template) throws InvalidAttributeException {

		// TODO

	}

}
