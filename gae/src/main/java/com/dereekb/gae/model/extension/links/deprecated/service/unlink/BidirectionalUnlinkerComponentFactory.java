package com.dereekb.gae.model.extension.links.deprecated.service.unlink;

import com.dereekb.gae.model.extension.links.deprecated.functions.BidirectionalLinkerFunction;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.FactoryMakeFailureException;

public class BidirectionalUnlinkerComponentFactory<T extends UniqueModel>
        implements Factory<BidirectionalUnlinkerComponent<T>> {

	private Factory<BidirectionalLinkerFunction<T, ?>> linkerFunctionFactory;
	private BidirectionalUnlinkerComponentDelegate<T> delegate;
	private String type;

	@Override
	public BidirectionalUnlinkerComponent<T> make() throws FactoryMakeFailureException {
		BidirectionalUnlinkerComponent<T> component = new BidirectionalUnlinkerComponent<T>();
		component.setType(this.type);
		component.setDelegate(this.delegate);
		component.setLinkerFactory(this.linkerFunctionFactory);
		return component;
	}

	public BidirectionalUnlinkerComponentDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(BidirectionalUnlinkerComponentDelegate<T> delegate) {
		this.delegate = delegate;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Factory<BidirectionalLinkerFunction<T, ?>> getLinkerFunctionFactory() {
		return this.linkerFunctionFactory;
	}

	public void setLinkerFunctionFactory(Factory<BidirectionalLinkerFunction<T, ?>> linkerFunctionFactory) {
		this.linkerFunctionFactory = linkerFunctionFactory;
	}

}
