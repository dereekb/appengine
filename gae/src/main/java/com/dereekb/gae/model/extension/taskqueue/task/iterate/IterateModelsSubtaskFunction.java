package com.dereekb.gae.model.extension.taskqueue.task.iterate;

import java.util.List;

import com.dereekb.gae.model.extension.taskqueue.api.CustomTaskInfo;
import com.dereekb.gae.utilities.collections.pairs.SuccessResultsPair;
import com.dereekb.gae.utilities.function.staged.filter.FilteredStagedFunction;

/**
 * Function that does nothing itself, and relies on it's observers to modify the
 * target models.
 *
 * @author dereekb
 * @param <T>
 * @deprecated I don't like the look of this. There are better ways to do this
 *             without using a staged function. Replace this with a better
 *             iteration type...
 */
@Deprecated
public class IterateModelsSubtaskFunction<T> extends FilteredStagedFunction<T, SuccessResultsPair<T>>
        implements IterateModelsSubtask<T> {

	@Override
	protected void doFunction() {
		// Does Nothing.
	}

	@Override
	public void initTask(CustomTaskInfo request) {
		// Does Nothing.
	}

	@Override
	public void useModelBatch(List<T> batch) {
		for (T model : batch) {
			SuccessResultsPair<T> pair = new SuccessResultsPair<T>(model);
			this.addObject(pair);
		}

		this.run();
	}

	@Override
	public void endTask() {
		// Does Nothing.
	}

}
