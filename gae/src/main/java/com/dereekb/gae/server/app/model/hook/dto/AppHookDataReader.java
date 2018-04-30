package com.dereekb.gae.server.app.model.hook.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.app.model.app.shared.dto.AbstractAppRelatedModelDataReader;
import com.dereekb.gae.server.app.model.hook.AppHook;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * {@link DirectionalConverter} for converting a {@link AppHook} to
 * {@link AppHookData}.
 *
 * @author dereekb
 */
public final class AppHookDataReader extends AbstractAppRelatedModelDataReader<AppHook, AppHookData> {

	public AppHookDataReader() {
		super(AppHook.class);
	}

	public AppHookDataReader(Factory<AppHook> factory) {
		super(factory);
	}

	@Override
	public AppHook convertSingle(AppHookData input) throws ConversionFailureException {
		AppHook model = super.convertSingle(input);

		// Info
		model.setGroup(input.getGroup());
		model.setEvent(input.getEvent());
		model.setPath(input.getPath());
		model.setEnabled(input.getEnabled());
		model.setFailures(input.getFailures());

		// Link

		return model;
	}

}
