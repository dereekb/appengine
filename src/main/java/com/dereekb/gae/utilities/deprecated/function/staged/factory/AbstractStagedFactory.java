package com.dereekb.gae.utilities.function.staged.factory;

import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * Basic {@link Factory} implementation that includes a {@link StagedFunctionStage} object that sub-classes rely on.
 * 
 * @author dereekb
 *
 * @param <T> Type this factory creates.
 */
public abstract class AbstractStagedFactory<T>
        implements Factory<T> {

	protected StagedFunctionStage functionStage;

	public AbstractStagedFactory(StagedFunctionStage functionStage) {
		super();
		this.functionStage = functionStage;
	}

	public StagedFunctionStage[] getStages(T object) {
		StagedFunctionStage[] stages = { functionStage };
		return stages;
	}

	public StagedFunctionStage getFunctionStage() {
		return functionStage;
	}

	public void setFunctionStage(StagedFunctionStage functionStage) {
		this.functionStage = functionStage;
	}

}
