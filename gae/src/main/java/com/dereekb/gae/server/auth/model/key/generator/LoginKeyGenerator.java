package com.dereekb.gae.server.auth.model.key.generator;

import java.util.Date;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractModelGenerator;
import com.dereekb.gae.model.extension.generation.impl.keys.LongModelKeyGenerator;
import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.keys.generator.ObjectifyKeyGenerator;
import com.googlecode.objectify.Key;

/**
 * Implementation of {@link Generator} for {@link LoginKey}.
 *
 * @author dereekb
 */
public final class LoginKeyGenerator extends AbstractModelGenerator<LoginKey> {

	private static final ObjectifyKeyGenerator<LoginPointer> LOGIN_POINTER_GENERATOR = ObjectifyKeyGenerator
	        .nameKeyGenerator(LoginPointer.class);

	private Generator<Key<LoginPointer>> loginPointerGenerator = LOGIN_POINTER_GENERATOR;

	public LoginKeyGenerator() {
		this(LongModelKeyGenerator.GENERATOR);
	};

	public LoginKeyGenerator(Generator<ModelKey> keyGenerator) {
		super(LoginKey.class, keyGenerator);
	};

	@Override
	public LoginKey generateModel(ModelKey key,
	                              GeneratorArg arg) {
		LoginKey loginKey = new LoginKey();

		loginKey.setDate(new Date());
		loginKey.setMask(0L);

		if (this.loginPointerGenerator != null) {
			loginKey.setLoginPointer(this.loginPointerGenerator.generate(arg));
		}

		loginKey.setVerification("VERIFICATION");

		return loginKey;
	}

}
