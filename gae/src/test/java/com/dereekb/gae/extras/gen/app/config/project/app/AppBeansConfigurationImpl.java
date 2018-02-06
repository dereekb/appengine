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
	public static final String LINK_SERVICE_BEAN_ID = "linkService";
	public static final String TASK_SCHEDULER_BEAN_ID = "taskScheduler";
	public static final String MODEL_KEY_TYPE_CONVERTER_ID = "modelKeyTypeConverter";

	private String eventServiceId = EVENT_SERVICE_BEAN_ID;
	private String linkServiceId = LINK_SERVICE_BEAN_ID;
	private String taskSchedulerId = TASK_SCHEDULER_BEAN_ID;
	private String modelKeyTypeConverterId = MODEL_KEY_TYPE_CONVERTER_ID;

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

	@Override
	public String getLinkServiceId() {
		return this.linkServiceId;
	}

	public void setLinkServiceId(String linkServiceId) {
		if (linkServiceId == null) {
			throw new IllegalArgumentException("linkServiceId cannot be null.");
		}

		this.linkServiceId = linkServiceId;
	}

	@Override
	public String getTaskSchedulerId() {
		return this.taskSchedulerId;
	}

	public void setTaskSchedulerId(String taskSchedulerId) {
		if (taskSchedulerId == null) {
			throw new IllegalArgumentException("taskSchedulerId cannot be null.");
		}

		this.taskSchedulerId = taskSchedulerId;
	}

	@Override
	public String getModelKeyTypeConverterId() {
		return this.modelKeyTypeConverterId;
	}

	public void setModelKeyTypeConverterId(String modelKeyTypeConverterId) {
		if (modelKeyTypeConverterId == null) {
			throw new IllegalArgumentException("modelKeyTypeConverterId cannot be null.");
		}

		this.modelKeyTypeConverterId = modelKeyTypeConverterId;
	}

}
