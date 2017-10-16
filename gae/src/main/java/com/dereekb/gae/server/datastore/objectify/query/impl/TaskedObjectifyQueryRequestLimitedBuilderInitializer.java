package com.dereekb.gae.server.datastore.objectify.query.impl;

import java.util.Map;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.objectify.query.builder.AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl;
import com.dereekb.gae.server.datastore.objectify.query.builder.ConfigurableObjectifyQueryRequestConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryFactory;
import com.dereekb.gae.utilities.query.exception.IllegalQueryArgumentException;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link ObjectifyQueryRequestLimitedBuilderInitializer} implementation that
 * uses a {@link ObjectifyQueryFactory} and {@link Task} to initialize/modify a
 * configurer before using it to configure the input builder.
 * 
 * @author dereekb
 *
 * @param <Q>
 *            query type
 */
public class TaskedObjectifyQueryRequestLimitedBuilderInitializer<Q extends ConfigurableObjectifyQueryRequestConfigurer> extends AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl {

	private ObjectifyQueryFactory<Q> queryFactory;

	private Task<Q> modifyQueryTask;

	public TaskedObjectifyQueryRequestLimitedBuilderInitializer(ObjectifyQueryFactory<Q> queryFactory,
	        Task<Q> modifyQueryTask) {
		this.setQueryFactory(queryFactory);
		this.setModifyQueryTask(modifyQueryTask);
	}

	public ObjectifyQueryFactory<Q> getQueryFactory() {
		return this.queryFactory;
	}

	public void setQueryFactory(ObjectifyQueryFactory<Q> queryFactory) throws IllegalArgumentException {
		if (queryFactory == null) {
			throw new IllegalArgumentException("QueryFactory cannot be null.");
		}

		this.queryFactory = queryFactory;
	}

	public Task<Q> getModifyQueryTask() {
		return this.modifyQueryTask;
	}

	public void setModifyQueryTask(Task<Q> modifyQueryTask) {
		if (modifyQueryTask == null) {
			throw new IllegalArgumentException("modifyQueryTask cannot be null.");
		}

		this.modifyQueryTask = modifyQueryTask;
	}

	// MARK: Override
	@Override
	public void initalizeBuilder(ObjectifyQueryRequestLimitedBuilder builder,
	                             Map<String, String> parameters)
	        throws IllegalQueryArgumentException {
		Q query = this.queryFactory.makeQuery(parameters);

		try {
			this.modifyQueryTask.doTask(query);
		} catch (FailedTaskException e) {
			Throwable cause = e.getCause();

			if (cause != null) {
				Class<?> causeClass = cause.getClass();

				// Only try and catch illegal argument exceptions that might
				// have come from the query, otherwise fail early so we can find
				// issues in the system.
				if (IllegalArgumentException.class.isAssignableFrom(causeClass)) {
					// TODO: Change to IllegalQueryArgumentException
					throw (IllegalArgumentException) cause;
				}
			}

			throw e;
		}

		query.configure(builder);
	}

	// MARK: Ignored
	@Override
	protected ConfigurableObjectifyQueryRequestConfigurer makeConfigurer() {
		return null;	// Unused.
	}

	@Override
	public String toString() {
		return "TaskedObjectifyQueryRequestLimitedBuilderInitializer [modifyQueryTask=" + this.modifyQueryTask
		        + ", queryFactory=" + this.queryFactory + "]";
	}

}
