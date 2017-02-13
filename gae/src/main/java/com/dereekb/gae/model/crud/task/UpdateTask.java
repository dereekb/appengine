package com.dereekb.gae.model.crud.task;

import com.dereekb.gae.model.crud.pairs.UpdatePair;
import com.dereekb.gae.model.crud.task.config.UpdateTaskConfig;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.task.ConfigurableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.web.api.util.attribute.exception.AttributeUpdateFailureException;

/**
 * Task for updating models.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface UpdateTask<T extends UniqueModel>
        extends ConfigurableTask<Iterable<UpdatePair<T>>, UpdateTaskConfig> {

	/**
	 * {@inheritDoc}
	 * 
	 * @throws AttributeUpdateFailureException
	 *             if atomic configuration and one update pair is invalid.
	 */
	@Override
	public void doTask(Iterable<UpdatePair<T>> input) throws FailedTaskException, AttributeUpdateFailureException;

	/**
	 * {@inheritDoc}
	 * 
	 * @throws AttributeUpdateFailureException
	 *             if atomic configuration and one update pair is invalid.
	 */
	@Override
	public void doTask(Iterable<UpdatePair<T>> input,
	                   UpdateTaskConfig configuration)
	        throws FailedTaskException,
	            AttributeUpdateFailureException;

}
