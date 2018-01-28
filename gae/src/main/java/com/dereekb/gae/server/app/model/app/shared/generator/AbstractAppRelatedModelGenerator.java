package com.dereekb.gae.server.app.model.app.shared.generator;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractModelGenerator;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.app.shared.impl.AbstractAppRelatedModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.keys.generator.ObjectifyKeyGenerator;

/**
 * {@link Generator} for {@link AbstractAppRelatedModel}.
 *
 * @author dereekb
 */
public abstract class AbstractAppRelatedModelGenerator<T extends AbstractAppRelatedModel<T>> extends AbstractModelGenerator<T> {

	private static final ObjectifyKeyGenerator<App> APP_KEY_GENERATOR = ObjectifyKeyGenerator
	        .numberKeyGenerator(App.class);

	public AbstractAppRelatedModelGenerator(Class<T> type, ModelKeyType keyType) {
		super(type, keyType);
	}

	public AbstractAppRelatedModelGenerator(Class<T> type, Generator<ModelKey> keyGenerator) {
		super(type, keyGenerator);
	}

	// MARK: AbstractAppRelatedModelGenerator
	protected void applyAppRelatedModelValues(T model,
	                                          GeneratorArg arg) {
		model.setApp(APP_KEY_GENERATOR.generate(arg));
	}

}
