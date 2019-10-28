package com.dereekb.gae.server.notification.model.token.generator;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractModelGenerator;
import com.dereekb.gae.model.extension.generation.impl.keys.LongModelKeyGenerator;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.notification.model.token.NotificationSettings;

/**
 * Implementation of {@link Generator} for {@link NotificationSettings}.
 *
 * @author dereekb
 */
public final class NotificationSettingsGenerator extends AbstractModelGenerator<NotificationSettings> {

	public NotificationSettingsGenerator() {
		this(LongModelKeyGenerator.GENERATOR);
	};

	public NotificationSettingsGenerator(Generator<ModelKey> keyGenerator) {
		super(NotificationSettings.class, keyGenerator);
	};

	@Override
	public NotificationSettings generateModel(ModelKey key,
	                                          GeneratorArg arg) {
		NotificationSettings model = new NotificationSettings();

		return model;
	}

}
