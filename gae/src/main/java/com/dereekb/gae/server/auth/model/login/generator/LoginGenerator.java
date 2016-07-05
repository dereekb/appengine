package com.dereekb.gae.server.auth.model.login.generator;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractModelGenerator;
import com.dereekb.gae.model.extension.generation.impl.keys.LongModelKeyGenerator;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Implementation of {@link Generator} for {@link Login}.
 *
 * @author dereekb
 */
public final class LoginGenerator extends AbstractModelGenerator<Login> {

	public LoginGenerator() {
		super(LongModelKeyGenerator.GENERATOR);
	};

	public LoginGenerator(Generator<ModelKey> keyGenerator) {
		super(keyGenerator);
	};

	@Override
	public Login generateModel(ModelKey key,
	                           GeneratorArg arg) {
		Login login = new Login();

		return login;
	}

}
