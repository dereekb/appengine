package com.dereekb.gae.model.crud.function.factory;

import com.dereekb.gae.model.crud.function.ReadFunction;
import com.dereekb.gae.model.crud.function.observers.ReadFunctionFilterObserver;
import com.dereekb.gae.model.crud.pairs.ReadPair;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.function.staged.factory.AbstractFilteredStagedFunctionFactory;

@Deprecated
public class ReadFunctionFactory<T extends UniqueModel> extends AbstractFilteredStagedFunctionFactory<ReadFunction<T>, T, ReadPair<T>> {

	private Getter<T> getter;

	public ReadFunctionFactory() {
		super();
		this.setFiltersObserverSingleton(new ReadFunctionFilterObserver<T>());
	}

	public ReadFunctionFactory(Getter<T> getter) {
		this();
		this.getter = getter;
	}

	@Override
	protected ReadFunction<T> newStagedFunction() {
		ReadFunction<T> function = new ReadFunction<T>(this.getter);
		return function;
	}

	public Getter<T> getGetter() {
		return this.getter;
	}

	public void setGetter(Getter<T> getter) {
		this.getter = getter;
	}

}
