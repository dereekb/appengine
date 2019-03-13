package com.dereekb.gae.server.app.model.app.generator;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractModelGenerator;
import com.dereekb.gae.model.extension.generation.impl.keys.LongModelKeyGenerator;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.app.AppLoginSecurityLevel;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecretGenerator;
import com.dereekb.gae.server.auth.security.app.service.impl.AppLoginSecretGeneratorImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Implementation of {@link Generator} for {@link App}.
 *
 * @author dereekb
 */
public final class AppGenerator extends AbstractModelGenerator<App> {

	private AppLoginSecretGenerator secretGenerator = new AppLoginSecretGeneratorImpl();
	private AppLoginSecurityLevel level = AppLoginSecurityLevel.APP;

	public AppGenerator() {
		this(LongModelKeyGenerator.GENERATOR);
	};

	public AppGenerator(AppLoginSecurityLevel level) {
		this(LongModelKeyGenerator.GENERATOR);
		this.setLevel(level);
	};

	public AppGenerator(Generator<ModelKey> keyGenerator) {
		super(App.class, keyGenerator);
	};

	public AppLoginSecretGenerator getSecretGenerator() {
		return this.secretGenerator;
	}

	public void setSecretGenerator(AppLoginSecretGenerator secretGenerator) {
		if (secretGenerator == null) {
			throw new IllegalArgumentException("secretGenerator cannot be null.");
		}

		this.secretGenerator = secretGenerator;
	}

	public AppLoginSecurityLevel getLevel() {
		return this.level;
	}

	public void setLevel(AppLoginSecurityLevel level) {
		if (level == null) {
			throw new IllegalArgumentException("level cannot be null.");
		}

		this.level = level;
	}

	// MARK: AbstractModelGenerator
	@Override
	public App generateModel(ModelKey key,
	                         GeneratorArg arg) {
		App login = new App();

		login.setName("App");
		login.setSecret(this.secretGenerator.generateSecret());
		login.setLevel(this.level);

		return login;
	}

	@Override
	public String toString() {
		return "AppGenerator [secretGenerator=" + this.secretGenerator + "]";
	}

}
