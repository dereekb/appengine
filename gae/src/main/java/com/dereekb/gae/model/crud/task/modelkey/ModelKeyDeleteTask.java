package com.dereekb.gae.model.crud.task.modelkey;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.task.Task;

/**
 * Task for deleting models.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelKeyDeleteTask
        extends Task<Iterable<ModelKey>> {

}
