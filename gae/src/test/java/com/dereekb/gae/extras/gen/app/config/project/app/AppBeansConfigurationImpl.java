package com.dereekb.gae.extras.gen.app.config.project.app;

/**
 * {@link AppBeansConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public class AppBeansConfigurationImpl
        implements AppBeansConfiguration {

	public static final String EVENT_SERVICE_BEAN_ID = "eventService";

	private String eventServiceId = EVENT_SERVICE_BEAN_ID;

	@Override
	public String getEventServiceId() {
		return this.eventServiceId;
	}

	public void setEventServiceId(String eventServiceId) {
		if (eventServiceId == null) {
			throw new IllegalArgumentException("eventServiceId cannot be null.");
		}

		this.eventServiceId = eventServiceId;
	}

}
