package com.dereekb.gae.model.extension.search.document.index.service.impl;

import java.util.List;

import com.dereekb.gae.model.extension.search.document.index.IndexPair;
import com.dereekb.gae.model.extension.search.document.index.component.IndexingDocumentSetBuilder;
import com.dereekb.gae.server.datastore.Updater;
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

	private Updater<T> updater;

	public SavingDocumentIndexServiceImpl(SearchDocumentSystem indexService,
	        IndexingDocumentSetBuilder<T> builder,
	        Updater<T> updater) throws IllegalArgumentException {
		super(indexService, builder);
		this.setSetter(updater);
	}

	public Updater<T> getSetter() {
		return this.updater;
	}

	public void setSetter(Updater<T> updater) throws IllegalArgumentException {
		if (updater == null) {
			throw new IllegalArgumentException("Setter cannot be null.");
		}

		this.updater = updater;
	}

	// MARK: Override
	@Override
	public void doTask(Iterable<IndexPair<T>> input) throws FailedTaskException {
		try {
			super.doTask(input);
			List<T> models = IndexPair.getKeys(input);
			this.updater.update(models);
		} catch (FailedTaskException e) {
			throw e;
		}
	}

	@Override
	public String toString() {
		return "SavingDocumentIndexServiceImpl [updater=" + this.updater + ", toString()=" + super.toString() + "]";
	}

}
