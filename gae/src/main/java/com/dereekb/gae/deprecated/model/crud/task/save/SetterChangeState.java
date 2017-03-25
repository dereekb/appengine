package com.dereekb.gae.model.crud.task.save;

import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;

/**
 * Type of change to make using a {@link ConfiguredSetter}.
 *
 * @author dereekb
 * 
 * @deprecated
 * @see ConfiguredSetterModelsTask
 */
@Deprecated
public enum SetterChangeState {

	/**
	 * Store new models.
	 */
	STORE,

	/**
	 * Update existing models.
	 */
	UPDATE,

	/**
	 * Delete existing models.
	 */
	DELETE

}
