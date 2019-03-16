package com.dereekb.gae.model.extension.links.deprecated.functions.factory;

import com.dereekb.gae.model.extension.links.deprecated.functions.BidirectionalLinkerFunction;
import com.dereekb.gae.model.extension.links.deprecated.functions.observer.BidirectionalUnlinkObserver;
import com.dereekb.gae.model.extension.links.deprecated.service.unlink.BidirectionalUnlinkerComponent;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.factory.observer.AbstractStagedFunctionObserverFactory;

/**
 * Factory for creating {@link BidirectionalUnlinkObserver} instances.
 *
 * The {@link BidirectionalLinkerFunction} created has safe set to false, since if the object cannot be loaded it most likely does not exist
 * anyways, and the after-effect is the same as if it did not happen.
 *
 * This can be turned off via the 'safe' variable for this factory, in-case we want to provide additional safetly/atomicity.
 *
 * @author dereekb
 *
 * @param <T>
 */
public class BidirectionalUnlinkObserverFactory<T extends UniqueModel> extends AbstractStagedFunctionObserverFactory<BidirectionalUnlinkObserver<T>, T> {

	private Factory<BidirectionalUnlinkerComponent<T>> unlinkerFactory;

	public BidirectionalUnlinkObserverFactory() {
		super(StagedFunctionStage.PRE_SAVING);
	}

	@Override
	public BidirectionalUnlinkObserver<T> generateObserver() {
		BidirectionalUnlinkObserver<T> observer = new BidirectionalUnlinkObserver<T>();

		if (this.unlinkerFactory != null) {
			BidirectionalUnlinkerComponent<T> unlinkerFunction = this.unlinkerFactory.make();
			observer.setUnlinker(unlinkerFunction);
		}

		return observer;
	}

	public Factory<BidirectionalUnlinkerComponent<T>> getUnlinkerFactory() {
		return this.unlinkerFactory;
	}

	public void setUnlinkerFactory(Factory<BidirectionalUnlinkerComponent<T>> unlinkerFactory) {
		this.unlinkerFactory = unlinkerFactory;
	}

}
