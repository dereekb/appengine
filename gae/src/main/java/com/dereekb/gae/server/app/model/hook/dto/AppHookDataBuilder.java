package com.dereekb.gae.server.app.model.hook.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.app.model.app.shared.dto.AbstractAppRelatedModelDataBuilder;
import com.dereekb.gae.server.app.model.hook.AppHook;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * {@link DirectionalConverter} for converting a {@link AppHook} to
 * {@link AppHookData}.
 *
 * @author dereekb
 */
public final class AppHookDataBuilder extends AbstractAppRelatedModelDataBuilder<AppHook, AppHookData> {

	public AppHookDataBuilder() {
		super(AppHookData.class);
	}

	public AppHookDataBuilder(Factory<AppHookData> factory) throws IllegalArgumentException {
		super(factory);
	}

	@Override
	public AppHookData convertSingle(AppHook input) throws ConversionFailureException {
		AppHookData data = super.convertSingle(input);

		// Info
		data.setGroup(input.getGroup());
		data.setEvent(input.getEvent());
		data.setPath(input.getPath());
		data.setEnabled(input.getEnabled());
		data.setFailures(input.getFailures());

		// Links

		return data;
	}

}
