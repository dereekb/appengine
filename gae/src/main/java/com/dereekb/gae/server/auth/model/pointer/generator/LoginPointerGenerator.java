package com.dereekb.gae.server.auth.model.pointer.generator;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractModelGenerator;
import com.dereekb.gae.model.extension.generation.impl.keys.StringModelKeyGenerator;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.password.impl.PasswordLoginServiceImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.keys.generator.ObjectifyKeyGenerator;
import com.googlecode.objectify.Key;

/**
 * Implementation of {@link Generator} for {@link LoginPointer}.
 *
 * @author dereekb
 */
public final class LoginPointerGenerator extends AbstractModelGenerator<LoginPointer> {

	private static final ObjectifyKeyGenerator<Login> LOGIN_GENERATOR = ObjectifyKeyGenerator
	        .numberKeyGenerator(Login.class);

	private Generator<Key<Login>> loginKeyGenerator = LOGIN_GENERATOR;

	public LoginPointerGenerator() {
		this(StringModelKeyGenerator.GENERATOR);
	};

	public LoginPointerGenerator(Generator<ModelKey> keyGenerator) {
		super(LoginPointer.class, keyGenerator);
	};

	@Override
	public LoginPointer generateModel(ModelKey key,
	                                  GeneratorArg arg) {
		LoginPointer loginPointer = new LoginPointer();

		loginPointer.setLoginPointerType(LoginPointerType.PASSWORD);
		loginPointer.setPassword("ENCODEDPASSWORD");
		loginPointer.setIdentifier(PasswordLoginServiceImpl.PASSWORD_PREFIX + key.toString());

		if (this.loginKeyGenerator != null) {
			loginPointer.setLogin(this.loginKeyGenerator.generate(arg));
		}

		return loginPointer;
	}

}
