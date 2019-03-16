package com.dereekb.gae.web.taskqueue.model.extension.iterate.impl;

import java.util.Map;

import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;

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
	private String stepCursor;
	private Integer iterationStep;
	private CaseInsensitiveMap<String> parameters;

	public IterateTaskInputImpl(String taskName, String modelType, String stepCursor, Map<String, String> parameters) {
		this(taskName, modelType, stepCursor, 0, parameters);
	}

	public IterateTaskInputImpl(String taskName,
	        String modelType,
	        String stepCursor,
	        Integer iterationStep,
	        Map<String, String> parameters) {
		this.setTaskName(taskName);
		this.setModelType(modelType);
		this.setStepCursor(stepCursor);
		this.setIterationStep(iterationStep);
		this.setParameters(parameters);
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
		if (iterationStep == null) {
			iterationStep = 0;
		}

		this.iterationStep = iterationStep;
	}

	@Override
	public String getStepCursor() {
		return this.stepCursor;
	}

	public void setStepCursor(String stepCursor) {
		this.stepCursor = stepCursor;
	}

	@Override
	public CaseInsensitiveMap<String> getParameters() {
		return this.parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		if (parameters == null) {
			this.parameters = null;
		} else {
			this.parameters = new CaseInsensitiveMap<String>(parameters);
		}
	}

	@Override
	public String toString() {
		return "IterateTaskInputImpl [taskName=" + this.taskName + ", modelType=" + this.modelType + ", iterationStep="
		        + this.iterationStep + ", stepCursor=" + this.stepCursor + ", parameters=" + this.parameters + "]";
	}

}
