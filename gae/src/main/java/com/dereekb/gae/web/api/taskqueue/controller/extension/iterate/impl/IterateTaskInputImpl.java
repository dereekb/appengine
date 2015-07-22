package com.dereekb.gae.web.api.taskqueue.controller.extension.iterate.impl;

import java.util.Map;

import com.dereekb.gae.web.api.taskqueue.controller.extension.iterate.IterateTaskInput;
import com.google.appengine.api.datastore.Cursor;

/**
 * {@link IterateTaskInput} implementation.
 *
 * @author dereekb
 *
 */
public class IterateTaskInputImpl
        implements IterateTaskInput {

	private String taskName;
	private String modelType;
	private Integer iterationStep;
	private Cursor queryCursor;
	private Map<String, String> parameters;

	public IterateTaskInputImpl(String taskName, String modelType) {
		this.setTaskName(taskName);
		this.setModelType(modelType);
	}

	public IterateTaskInputImpl(String taskName,
	        String modelType,
	        Integer iterationStep,
	        Cursor queryCursor,
	        Map<String, String> parameters) {
		this(taskName, modelType);
		this.iterationStep = iterationStep;
		this.queryCursor = queryCursor;
		this.parameters = parameters;
	}

	@Override
	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Override
	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	@Override
	public Integer getIterationStep() {
		return this.iterationStep;
	}

	public void setIterationStep(Integer iterationStep) {
		this.iterationStep = iterationStep;
	}

	@Override
	public Cursor getQueryCursor() {
		return this.queryCursor;
	}

	public void setQueryCursor(Cursor queryCursor) {
		this.queryCursor = queryCursor;
	}

	@Override
	public Map<String, String> getParameters() {
		return this.parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "IterateTaskInputImpl [taskName=" + this.taskName + ", modelType=" + this.modelType + ", iterationStep="
		        + this.iterationStep + ", queryCursor=" + this.queryCursor + ", parameters=" + this.parameters + "]";
	}

}
