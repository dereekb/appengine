package com.dereekb.gae.model.taskqueue.updater.task;

import java.util.List;

import com.dereekb.gae.model.taskqueue.updater.RelatedModelUpdateType;
import com.dereekb.gae.model.taskqueue.updater.RelatedModelUpdater;
import com.dereekb.gae.model.taskqueue.updater.RelatedModelUpdaterFactory;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.task.impl.AbstractModelKeyListAccessorTask;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link Task} for updating model relations using a
 * {@link RelatedModelUpdaterFactory}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class RelatedModelUpdateTask<T extends UniqueModel> extends AbstractModelKeyListAccessorTask<T> {

	private RelatedModelUpdateType type;
	private RelatedModelUpdaterFactory<T> factory;

	public RelatedModelUpdateTask(RelatedModelUpdateType type, RelatedModelUpdaterFactory<T> factory) {
		this.setType(type);
		this.setFactory(factory);
	}

	public RelatedModelUpdateType getType() {
		return this.type;
	}

	public void setType(RelatedModelUpdateType type) {
		if (type == null) {
			throw new IllegalArgumentException("type cannot be null.");
		}

		this.type = type;
	}

	public RelatedModelUpdaterFactory<T> getFactory() {
		return this.factory;
	}

	public void setFactory(RelatedModelUpdaterFactory<T> factory) {
		if (factory == null) {
			throw new IllegalArgumentException("factory cannot be null.");
		}

		this.factory = factory;
	}

	// MARK: Task
	@Override
	public void doTask(ModelKeyListAccessor<T> input) throws FailedTaskException {
		RelatedModelUpdater<T> updater = this.factory.makeUpdater();

		List<T> models = input.getModels();
		updater.updateRelations(this.type, models);
	}

	@Override
	public String toString() {
		return "RelatedModelUpdateTask [type=" + this.type + ", factory=" + this.factory + "]";
	}

}
