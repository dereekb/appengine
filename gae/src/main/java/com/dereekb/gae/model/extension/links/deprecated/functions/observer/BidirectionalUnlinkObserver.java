package com.dereekb.gae.model.extension.links.deprecated.functions.observer;

import java.util.List;

import com.dereekb.gae.model.extension.links.deprecated.functions.BidirectionalLinkerFunction;
import com.dereekb.gae.model.extension.links.deprecated.service.unlink.BidirectionalUnlinkerComponent;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.function.staged.StagedFunction;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.observer.StagedFunctionObserver;

/**
 * Function observer that uses a {@link BidirectionalLinkerFunction} to unlink objects.
 *
 * @author dereekb
 *
 * @param <T>
 */
public class BidirectionalUnlinkObserver<T extends UniqueModel>
        implements StagedFunctionObserver<T> {

	private BidirectionalUnlinkerComponent<T> unlinker;

	@Override
	public void functionHandlerCallback(StagedFunctionStage stage,
	                                    StagedFunction<T, ?> handler) {
		List<T> objects = handler.getFunctionObjects();
		this.unlinker.unlink(objects);
	}

	public BidirectionalUnlinkerComponent<T> getUnlinker() {
		return this.unlinker;
	}

	public void setUnlinker(BidirectionalUnlinkerComponent<T> unlinker) {
		this.unlinker = unlinker;
	}

}
