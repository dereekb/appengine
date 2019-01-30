package com.dereekb.gae.extras.gen.app.config.app.model.remote;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelConfigurationGroup;

/**
 * Represents a group of model configurations.
 *
 * @author dereekb
 *
 */
public interface RemoteModelConfigurationGroup
        extends AppModelConfigurationGroup {

	/**
	 * Group name, or {@code null} if the default unnamed group.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	@Override
	public String getGroupName();

	/**
	 *
	 * @return {@link List}. Never {@code null}.
	 */
	@Override
	public List<RemoteModelConfiguration> getModelConfigurations();

}
