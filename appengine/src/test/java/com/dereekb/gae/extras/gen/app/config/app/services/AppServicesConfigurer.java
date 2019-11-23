package com.dereekb.gae.extras.gen.app.config.app.services;

/**
 * Used for configuring important app services.
 *
 * @author dereekb
 *
 */
public interface AppServicesConfigurer {

	/**
	 * @return {@link AppServerInitializationConfigurer}, or {@code null} if no
	 *         initialization is necessary.
	 */
	public AppServerInitializationConfigurer getAppServerInitializationConfigurer();

	public AppLoginTokenSecurityConfigurer getAppLoginTokenSecurityConfigurer();

	public AppEventServiceListenersConfigurer getAppEventServiceListenersConfigurer();

	public AppModelKeyEventListenerConfigurer getAppModelKeyEventListenerConfigurer();

	public AppWebHookEventServiceConfigurer getAppWebHookEventServiceConfigurer();

	public AppTaskSchedulerEnqueuerConfigurer getAppTaskSchedulerEnqueuerConfigurer();

	public AppMailServiceConfigurer getAppMailServiceConfigurer();

	/**
	 * @return {@link AppFirebaseServiceConfigurer}, or {@code null} if
	 *         no firebase components.
	 */
	public AppFirebaseServiceConfigurer getAppFirebaseServiceConfigurer();

	/**
	 * @return {@link AppGoogleCloudStorageServiceConfigurer}, or {@code null} if
	 *         no firebase components.
	 */
	public AppGoogleCloudStorageServiceConfigurer getAppGoogleCloudStorageServiceConfigurer();

	/**
	 * @return {@link AppUserNotificationServiceConfigurer}, or {@code null} if
	 *         no
	 *         user notification components.
	 */
	public AppUserNotificationServiceConfigurer getAppUserNotificationServiceConfigurer();

	/**
	 * @return {@link AppDebugApiConfigurer}, or {@code null} if no
	 *         debug components.
	 */
	public AppDebugApiConfigurer getAppDebugApiConfigurer();

}
