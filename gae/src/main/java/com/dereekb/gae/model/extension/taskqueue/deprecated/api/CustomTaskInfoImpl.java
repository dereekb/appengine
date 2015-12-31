package com.dereekb.gae.model.extension.taskqueue.deprecated.api;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * {@link CustomTaskInfo} implementation.
 *
 * @author dereekb
 */
@Deprecated
public class CustomTaskInfoImpl
        implements CustomTaskInfo {

	private String taskName;
	private Integer taskStep;
	private List<String> stringIdentifiers;
	private Map<String, String> taskParameters;

	public CustomTaskInfoImpl(String taskName, Map<String, String> taskParameters) {
		this(taskName, 0, null, taskParameters);
	}

	public CustomTaskInfoImpl(String taskName,
	        Integer taskStep,
	        List<String> stringIdentifiers,
	        Map<String, String> taskParameters) {
		this.taskName = taskName;
		this.setTaskStep(taskStep);
		this.setStringIdentifiers(stringIdentifiers);
		this.setTaskParameters(taskParameters);
	}

	@Override
	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Override
	public Integer getTaskStep() {
		return this.taskStep;
	}

	public void setTaskStep(Integer taskStep) {
		this.taskStep = (taskStep != null) ? taskStep : 0;
	}

	@Override
	public List<String> getStringIdentifiers() {
		return this.stringIdentifiers;
	}

	public void setStringIdentifiers(List<String> stringIdentifiers) {
		if (stringIdentifiers == null) {
			this.stringIdentifiers = Collections.emptyList();
		} else {
			this.stringIdentifiers = stringIdentifiers;
		}
	}

	@Override
	public Map<String, String> getTaskParameters() {
		return this.taskParameters;
	}

	public void setTaskParameters(Map<String, String> taskParameters) {
		if (taskParameters == null) {
			this.taskParameters = Collections.emptyMap();
		} else {
			this.taskParameters = taskParameters;
		}
	}

}
