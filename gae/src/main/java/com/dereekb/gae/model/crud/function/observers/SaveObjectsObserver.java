package com.dereekb.gae.model.crud.function.observers;

import java.util.List;

import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;
import com.dereekb.gae.utilities.function.staged.StagedFunction;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.observer.StagedFunctionObserver;

/**
 * Additional way to save objects in a function. It is recommended however to save during the normal save period.
 *
 * This function is useful for saving actions that occur after created objects are initially put into the datastore.
 *
 * @author dereekb
 * @param <T>
 */
public class SaveObjectsObserver<T>
        implements StagedFunctionObserver<T> {

	private ConfiguredSetter<T> setter;

	public SaveObjectsObserver(ConfiguredSetter<T> setter) {
	    this.setter = setter;
    }

	@Override
	public void functionHandlerCallback(StagedFunctionStage stage,
	                                    StagedFunction<T, ?> handler) {
		List<T> functionObjects = handler.getFunctionObjects();
		this.setter.save(functionObjects);
	}

	public ConfiguredSetter<T> getSetter() {
		return this.setter;
	}

	public void setSetter(ConfiguredSetter<T> setter) {
		this.setter = setter;
	}

}
