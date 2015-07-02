package com.dereekb.gae.model.crud.function.utility.saver;

import com.dereekb.gae.server.datastore.Setter;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;
import com.dereekb.gae.utilities.function.staged.delegates.StagedFunctionSaveDelegate;

/**
 * Default implementation of {@link StagedFunctionSaveDelegate} for saving or
 * deleting using a {@link Setter}.
 *
 * @author dereekb
 * @param <T>
 */
public final class SetterStagedFunctionSaveDelegate<T>
        implements StagedFunctionSaveDelegate<T> {

	private ConfiguredSetter<T> setter;
	private SetterChangeState state = SetterChangeState.SAVE;

	public SetterStagedFunctionSaveDelegate(ConfiguredSetter<T> setter) {
		this.setter = setter;
	}

	public SetterStagedFunctionSaveDelegate(ConfiguredSetter<T> setter, SetterChangeState state) {
		this.setter = setter;
		this.state = state;
	}

	@Override
	public boolean saveFunctionChanges(Iterable<T> models) {

		switch (this.state) {
			case SAVE:
				this.setter.save(models);
				break;
			case DELETE:
				this.setter.delete(models);
				break;
		}

		return true;
	}

	public ConfiguredSetter<T> getSetter() {
		return this.setter;
	}

	public void setSetter(ConfiguredSetter<T> setter) {
		this.setter = setter;
	}

	public SetterChangeState getState() {
	    return this.state;
    }

	public void setState(SetterChangeState state) {
	    this.state = state;
    }

}
