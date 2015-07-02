package com.dereekb.gae.model.crud.function.pairs;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.collections.pairs.ResultsPair;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;

/**
 * Defines a pair to process a creation with, and return the results.
 * @author dereekb
 *
 * @param <T>
 */
public class CreatePair<T> extends ResultsPair<T, T>
        implements StagedFunctionObject<T> {

	/**
	 * Source object to use during creation.
	 *
	 * @param source
	 */
	public CreatePair(T source) {
		super(source);
	}

	@Override
    public T getFunctionObject(StagedFunctionStage stage) {
	    T object = null;

		if (stage.before(StagedFunctionStage.FUNCTION_FINISHED)) {
			object = this.getSource();
		} else {
			object = this.getResult();
		}

		return object;
    }

	public static <T extends UniqueModel> List<CreatePair<T>> createPairsForModels(Iterable<T> models) {
		List<CreatePair<T>> pairs = new ArrayList<CreatePair<T>>();

		for (T model : models) {
			CreatePair<T> pair = new CreatePair<T>(model);
			pairs.add(pair);
		}

		return pairs;
	}
}