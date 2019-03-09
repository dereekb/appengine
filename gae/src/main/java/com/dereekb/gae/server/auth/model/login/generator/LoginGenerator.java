package com.dereekb.gae.server.auth.model.login.generator;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractModelGenerator;
import com.dereekb.gae.model.extension.generation.impl.keys.LongModelKeyGenerator;
import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Implementation of {@link Generator} for {@link Login}.
 *
 * @author dereekb
 */
public final class LoginGenerator extends AbstractModelGenerator<Login> {

	private Generator<Descriptor> descriptorGenerator;

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

		// Descriptor
		if (this.descriptorGenerator != null) {
			login.setDescriptor(this.descriptorGenerator.generate(arg));
		}

		return login;
	}

}
