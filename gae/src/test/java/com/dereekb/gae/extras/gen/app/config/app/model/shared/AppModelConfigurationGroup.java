package com.dereekb.gae.extras.gen.app.config.app.model.shared;

import java.util.List;

/**
 * Represents a group of model configurations.
 *
 * @author dereekb
 *
 */
public interface AppModelConfigurationGroup {

	/**
	 * Group name, or {@code null} if the default unnamed group.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getGroupName();

	/**
	 *
	 * @return {@link List}. Never {@code null}.
	 */
	public List<? extends AppModelConfiguration> getModelConfigurations();

}
