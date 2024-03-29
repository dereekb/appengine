package com.dereekb.gae.test.app.server.datastore.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.extras.gen.test.model.foo.Foo;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.app.mock.context.AbstractAppContextOnlyTestingContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

/**
 * Abstract collection of tests related to querying the datastore.
 *
 * Tests are conducted using the {@link Foo} type.
 *
 * @author dereekb
 *
 */
public abstract class AbstractObjectifyQueryTests extends AbstractAppContextOnlyTestingContext {

	@Autowired
	@Qualifier("fooRegistry")
	protected ObjectifyRegistry<Foo> registry;

	@Autowired
	@Qualifier("fooTestModelGenerator")
	protected TestModelGenerator<Foo> modelGenerator;

}
