package com.dereekb.gae.extras.gen.app.config.app.model.local;

import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelContextConfigurer;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityKeyEnforcement;

/**
 * {@link AppModelConfiguration} extension for local models.
 *
 * @author dereekb
 *
 */
public interface LocalModelConfiguration
        extends AppModelConfiguration, LocalModelCrudsConfiguration, LocalModelBeansConfiguration {

	@Override
	public LocalModelCrudsConfiguration getCrudsConfiguration();

	@Override
	public LocalModelBeansConfiguration getBeansConfiguration();

	// Utility
	public LocalModelUtilityBeansConfiguration getUtilityBeans();

	/**
	 * Returns the key enforcement for this model.
	 */
	public ObjectifyDatabaseEntityKeyEnforcement getKeyEnforcement();

	public Class<?> getModelQueryClass();

	public Class<?> getModelQueryInitializerClass();

	public Class<?> getModelGeneratorClass();

	public Class<?> getModelLinkSystemBuilderEntryClass();

	public Class<?> getModelSecurityContextServiceEntryClass();

	public Class<?> getModelEditControllerClass();

	public Class<?> getModelOwnedModelQuerySecurityDelegateClass();

	@Override
	@Deprecated
	public boolean isLocalModel();

	// Custom Configuration
	/**
	 * Returns the custom configuration for configuring model contexts.
	 *
	 * @return {@link LocalModelContextConfigurer}. Never {@code null}.
	 */
	public LocalModelContextConfigurer getCustomModelContextConfigurer();

}
