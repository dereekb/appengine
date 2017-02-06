package com.dereekb.gae.model.extension.search.document.search.service.utility;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.initializer.impl.ModelStagedDocumentBuilderInitializer;
import com.dereekb.gae.model.extension.search.document.search.service.impl.DocumentSearchServiceImpl;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.QueryOptions.Builder;

/**
 * {@link Task} that modifies the input {@link QueryOptions.Builder} to return
 * only a model field.
 * <p>
 * Generally used in conjuction with a {@link DocumentSearchServiceImpl}.
 *
 * @author dereekb
 *
 */
public class IdsOnlyQueryOptionsBuilderTask
        implements Task<QueryOptions.Builder> {

	private String modelIdField = ModelStagedDocumentBuilderInitializer.MODEL_KEY_FIELD_KEY;

	public IdsOnlyQueryOptionsBuilderTask() {}

	public IdsOnlyQueryOptionsBuilderTask(String modelIdField) {
		this.modelIdField = modelIdField;
	}

	public String getModelIdField() {
		return this.modelIdField;
	}

	public void setModelIdField(String modelIdField) {
		this.modelIdField = modelIdField;
	}

	@Override
	public void doTask(Builder input) throws FailedTaskException {
		input.setFieldsToReturn(this.modelIdField);
	}

	@Override
	public String toString() {
		return "IdsOnlyQueryOptionsBuilderTask [modelIdField=" + this.modelIdField + "]";
	}

}
