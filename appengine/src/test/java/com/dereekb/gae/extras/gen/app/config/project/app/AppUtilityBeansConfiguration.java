package com.dereekb.gae.extras.gen.app.config.project.app;

/**
 * Utility beans used within the App Config.
 * Ï
 *
 * @author dereekb
 *
 */
public interface AppUtilityBeansConfiguration {

	// MARK: Remote
	/**
	 * May only by available at runtime when a remote service is provided.
	 */
	public String getClientLoginTokenModelContextServiceEntryFactoryBeanId();

	// MARK: Login Service
	public String getPasswordLoginServiceBeanId();

	public String getLoginRegisterServiceBeanId();

	public String getLoginAdminRolesBeanId();

	public String getLoginRolesServiceBeanId();

	// MARK: Login Token Services
	public String getLoginTokenUserDetailsBuilderBeanId();

	// MARK: Model Query Security Tasks
	public String getAdminOnlySecurityModelQueryTaskBeanId();

	public String getAllowAllSecurityModelQueryTaskBeanId();

	// MARK: Search
	public String getModelSearchServiceBeanId();

	public String getSearchServiceBeanId();

}
