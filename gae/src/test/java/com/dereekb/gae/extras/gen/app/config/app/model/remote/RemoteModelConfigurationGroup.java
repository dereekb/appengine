package com.dereekb.gae.extras.gen.app.config.app.model.remote;

import java.util.List;

/**
 * Represents a group of model configurations.
 *
 * @author dereekb
 *
 */
public interface RemoteModelConfigurationGroup {

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
	public List<RemoteModelConfiguration> getRemoteModelConfigurations();

}
