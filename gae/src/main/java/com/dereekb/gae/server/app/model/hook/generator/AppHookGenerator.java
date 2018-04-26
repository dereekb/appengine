package com.dereekb.gae.server.app.model.hook.generator;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.server.app.model.app.shared.generator.AbstractAppRelatedModelGenerator;
import com.dereekb.gae.server.app.model.hook.AppHook;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.event.model.shared.event.impl.CommonModelEventType;

/**
 * {@link Generator} for {@link AppHook}.
 *
 * @author dereekb
 *
 */
public class AppHookGenerator extends AbstractAppRelatedModelGenerator<AppHook> {

	public AppHookGenerator() {
		super(AppHook.class, ModelKeyType.NUMBER);
	}

	// MARK: AbstractModelGenerator
	@Override
	protected AppHook generateModel(ModelKey key,
	                                GeneratorArg arg) {
		AppHook model = new AppHook();
		model.setModelKey(key);

		model.setEventDetails(CommonModelEventType.CREATED);
		model.setPath("/v1/hook");

		return model;
	}

}
