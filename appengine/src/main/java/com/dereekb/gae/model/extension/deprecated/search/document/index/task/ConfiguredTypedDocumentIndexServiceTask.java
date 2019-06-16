package com.dereekb.gae.model.extension.search.document.index.task;

import java.util.List;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.deprecated.search.document.SearchableUniqueModel;
import com.dereekb.gae.model.extension.deprecated.search.document.index.IndexAction;
import com.dereekb.gae.model.extension.deprecated.search.document.index.service.TypedDocumentIndexService;
import com.dereekb.gae.model.extension.deprecated.search.document.index.service.exception.UnregisteredSearchTypeException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link Task} for {@link ModelKeyListAccessor} input that performs the
 * specified action on the input.
 * <p>
 * Generally used for {@link IndexAction#UNINDEX}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ConfiguredTypedDocumentIndexServiceTask<T extends SearchableUniqueModel>
        implements Task<ModelKeyListAccessor<T>> {

	private TypedDocumentIndexService typedIndexService;
	private IndexAction action;

	public ConfiguredTypedDocumentIndexServiceTask(TypedDocumentIndexService typedIndexService) {
		this(typedIndexService, IndexAction.UNINDEX);
	}

	public ConfiguredTypedDocumentIndexServiceTask(TypedDocumentIndexService typedIndexService, IndexAction action) {
		this.setTypedIndexService(typedIndexService);
		this.setAction(action);
    }

	public TypedDocumentIndexService getTypedIndexService() {
		return this.typedIndexService;
	}

	public void setTypedIndexService(TypedDocumentIndexService typedIndexService) {
		this.typedIndexService = typedIndexService;
	}

	public IndexAction getAction() {
		return this.action;
	}

	public void setAction(IndexAction action) {
		this.action = action;
	}

	@Override
	public void doTask(ModelKeyListAccessor<T> input) throws FailedTaskException {
		String type = input.getModelType();
		List<ModelKey> keys = input.getModelKeys();

		try {
			this.typedIndexService.changeIndexWithKeys(type, keys, this.action);
		} catch (AtomicOperationException | UnregisteredSearchTypeException e) {
			throw new FailedTaskException(e);
		}
	}

	@Override
	public String toString() {
		return "ConfiguredTypedDocumentIndexServiceTask [typedIndexService=" + this.typedIndexService + ", action="
		        + this.action + "]";
	}

}
