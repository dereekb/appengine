package com.dereekb.gae.model.crud.function.pairs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.utilities.collections.pairs.SuccessResultsPair;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;

/**
 * Basic delete pair.
 * Result is boolean of whether or not the object was deleted.
 *
 * @author dereekb
 * @param <T>
 *            Model type being deleted.
 */
public class DeletePair<T> extends SuccessResultsPair<T>
        implements StagedFunctionObject<T> {

	public DeletePair(T object) {
		super(object);
		this.object = true;
	}

	@Override
	public T getFunctionObject(StagedFunctionStage stage) {
		return this.getSource();
	}

	public static <T> List<DeletePair<T>> deletePairsForModels(Collection<T> models) {
		List<DeletePair<T>> pairs = new ArrayList<DeletePair<T>>();

		for (T model : models) {
			DeletePair<T> pair = new DeletePair<T>(model);
			pairs.add(pair);
		}

		return pairs;
	}

}
