package com.dereekb.gae.model.extension.taskqueue.iterate.impl;

import java.util.Map;

import com.dereekb.gae.model.extension.taskqueue.iterate.IterateTaskInput;
import com.google.appengine.api.datastore.Cursor;

/**
 * {@link IterateTaskInput} implementation.
 *
 * @author dereekb
 *
 */
public class IterateTaskInputImpl
        implements IterateTaskInput {

	/**
	 * Default HTTP header
	 */
	public static final String DEFAULT_TASK_STEP_HEADER = "TQ_ITERATE_STEP";

	private Integer iterationStep;
	private Cursor queryCursor;
	private Map<String, String> queryParameters;

	public IterateTaskInputImpl(Integer iterationStep, Cursor queryCursor, Map<String, String> queryParameters) {
		this.iterationStep = iterationStep;
		this.queryCursor = queryCursor;
		this.queryParameters = queryParameters;
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
		return this.queryParameters;
	}

	public void setQueryParameters(Map<String, String> queryParameters) {
		this.queryParameters = queryParameters;
	}

}
