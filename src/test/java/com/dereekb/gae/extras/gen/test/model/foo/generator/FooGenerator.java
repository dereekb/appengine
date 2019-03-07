package com.dereekb.gae.extras.gen.test.model.foo.generator;

import com.dereekb.gae.extras.gen.test.model.foo.Foo;
import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractModelGenerator;
import com.dereekb.gae.model.extension.generation.impl.keys.LongModelKeyGenerator;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Implementation of {@link Generator} for {@link Foo}.
 *
 * @author dereekb
 */
public final class FooGenerator extends AbstractModelGenerator<Foo> {

	public FooGenerator(Generator<ModelKey> keyGenerator) {
		super(Foo.class, LongModelKeyGenerator.GENERATOR);
	};

	@Override
	public Foo generateModel(ModelKey key,
	                                  GeneratorArg arg) {
		Foo Foo = new Foo();

		// TODO

		return Foo;
	}

}
