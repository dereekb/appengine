package com.dereekb.gae.model.crud.task.save;

import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;

/**
 * Type of change to make using a {@link ConfiguredSetter}.
 *
 * @author dereekb
 * 
 * @see ConfiguredSetterModelsTask
 */
public enum SetterChangeState {

	UPDATE,

	DELETE

}
