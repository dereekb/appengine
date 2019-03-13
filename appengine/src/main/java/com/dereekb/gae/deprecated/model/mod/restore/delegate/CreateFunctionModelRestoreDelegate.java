package com.thevisitcompany.gae.deprecated.model.mod.restore.delegate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.thevisitcompany.gae.deprecated.model.mod.restore.ModelRestoreDelegate;
import com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated.KeyedModel;
import com.thevisitcompany.gae.model.crud.function.CreateFunction;
import com.thevisitcompany.gae.model.crud.function.pairs.CreatePair;

/**
 * Uses a create function to restore the given objects.
 *
 * @author dereekb
 *
 * @param <T>
 */
@Deprecated
public class CreateFunctionModelRestoreDelegate<T extends KeyedModel<K>, K>
        implements ModelRestoreDelegate<T> {

	public CreateFunction<T, K> createFunction;

	@Override
	public void restore(Collection<T> objects) {
		List<CreatePair<T, K>> creationPairs = new ArrayList<CreatePair<T, K>>();

		for (T source : objects) {
			CreatePair<T, K> creationPair = new CreatePair<T, K>(source);
			creationPairs.add(creationPair);
		}

		createFunction.addObjects(creationPairs);
		createFunction.run();
	}

	public CreateFunction<T, K> getCreateFunction() {
		return createFunction;
	}

	public void setCreateFunction(CreateFunction<T, K> createFunction) {
		this.createFunction = createFunction;
	}

}
