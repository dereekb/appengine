package com.dereekb.gae.utilities.function.staged.observer;

import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionStagesSpecific;

/**
 * A specific observer that always is fired at the specified stages.
 * 
 * @author dereekb
 */
public interface StagedFunctionSpecificObjectObserver<T, W extends StagedFunctionObject<T>>
        extends StagedFunctionObjectObserver<T, W>, StagedFunctionStagesSpecific {

	/**
	 * @return Returns the stages that this observer is called at.
	 */
	public StagedFunctionStage[] getStages();

}
