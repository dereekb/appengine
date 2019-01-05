package com.dereekb.gae.extras.gen.app.config.project.app;

/**
 * Utility beans used within the App Config.
 *Ï
 * @author dereekb
 *
 */
public interface AppUtilityBeansConfiguration {

	// MARK: Remote
	/**
	 * May only by available at runtime when a remote service is provided.
	 */
	public String getClientLoginTokenModelContextServiceEntryFactoryBeanId();

}
