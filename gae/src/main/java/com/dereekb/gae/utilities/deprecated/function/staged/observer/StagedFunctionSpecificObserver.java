package com.dereekb.gae.utilities.function.staged.observer;

import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionStagesSpecific;

/**
 * A specific observer that always is fired at the specified stages.
 * @author dereekb
 */
public interface StagedFunctionSpecificObserver<T> extends StagedFunctionObserver<T>, StagedFunctionStagesSpecific {
	
	/**
	 * @return Returns the stages that this observer is called at.
	 */
	public StagedFunctionStage[] getStages();
	
}
