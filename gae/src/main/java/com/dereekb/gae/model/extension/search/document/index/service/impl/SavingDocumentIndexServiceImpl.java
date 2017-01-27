package com.dereekb.gae.model.extension.search.document.index.service.impl;

import java.util.List;

import com.dereekb.gae.model.extension.search.document.index.IndexPair;
import com.dereekb.gae.model.extension.search.document.index.component.IndexingDocumentSetBuilder;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.dereekb.gae.server.search.system.SearchDocumentSystem;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link DocumentIndexServiceImpl} extension that also saves the input models.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class SavingDocumentIndexServiceImpl<T extends UniqueSearchModel> extends DocumentIndexServiceImpl<T> {

	private ConfiguredSetter<T> setter;

	public SavingDocumentIndexServiceImpl(SearchDocumentSystem indexService,
	        IndexingDocumentSetBuilder<T> builder,
	        ConfiguredSetter<T> setter) throws IllegalArgumentException {
		super(indexService, builder);
		this.setSetter(setter);
	}

	public ConfiguredSetter<T> getSetter() {
		return this.setter;
	}

	public void setSetter(ConfiguredSetter<T> setter) throws IllegalArgumentException {
		if (setter == null) {
			throw new IllegalArgumentException("Setter cannot be null.");
		}

		this.setter = setter;
	}

	// MARK: Override
	@Override
	public void doTask(Iterable<IndexPair<T>> input) throws FailedTaskException {
		try {
			super.doTask(input);
			List<T> models = IndexPair.getKeys(input);
			this.setter.save(models);
		} catch (FailedTaskException e) {
			throw e;
		}
	}

	@Override
	public String toString() {
		return "SavingDocumentIndexServiceImpl [setter=" + this.setter + ", toString()=" + super.toString() + "]";
	}

}
