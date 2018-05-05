package com.dereekb.gae.extras.gen.app.config.app.model.local;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelConfigurationGroup;

/**
 * Represents a group of configured models.
 *
 * @author dereekb
 *
 */
public interface LocalModelConfigurationGroup
        extends AppModelConfigurationGroup {

	/**
	 *
	 * @return {@link List}. Never {@code null}.
	 */
	@Override
	public List<LocalModelConfiguration> getModelConfigurations();

}
