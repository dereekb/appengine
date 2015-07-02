package com.dereekb.gae.utilities.function.staged.components;

public class StagedFunctionObjectWrapper<T> implements StagedFunctionObject<T> {

	private final T object;
	
	public StagedFunctionObjectWrapper(T object) {
		this.object = object;
	}

	public T getObject() {
	    return object;
    }

	@Override
    public T getFunctionObject(StagedFunctionStage stage) {
	    return object;
    }
	
}
