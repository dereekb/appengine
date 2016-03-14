package com.dereekb.gae.model.extension.inclusion.reader.impl;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.components.AtomicReadService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.inclusion.reader.InclusionReaderAnalysis;
import com.dereekb.gae.model.extension.inclusion.reader.InclusionReaderSetAnalysis;
import com.dereekb.gae.model.extension.inclusion.reader.ModelInclusionReader;
import com.dereekb.gae.model.extension.inclusion.reader.ModelInclusionReaderAnalysis;
import com.dereekb.gae.model.extension.inclusion.reader.ModelInclusionReaderSetAnalysis;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link ModelInclusionReader} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelInclusionReaderImpl<T extends UniqueModel>
        implements ModelInclusionReader<T> {

	private AtomicReadService<T> readService;
	private InclusionReaderSetAnalysisImplDelegate<T> delegate;

	public ModelInclusionReaderImpl(AtomicReadService<T> readService, InclusionReaderSetAnalysisImplDelegate<T> delegate) {
		this.setReadService(readService);
		this.setDelegate(delegate);
	}

	public AtomicReadService<T> getReadService() {
		return this.readService;
	}

	public void setReadService(AtomicReadService<T> readService) {
		this.readService = readService;
	}

	public InclusionReaderSetAnalysisImplDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(InclusionReaderSetAnalysisImplDelegate<T> delegate) {
		this.delegate = delegate;
	}

	// MARK: InclusionReader
	@Override
	public InclusionReaderAnalysis analyzeInclusions(ModelKey modelKey)
	        throws AtomicOperationException,
	            IllegalArgumentException {
		T model = this.readService.read(modelKey);
		return this.analyzeInclusionsForModel(model);
	}

	@Override
	public InclusionReaderSetAnalysis analyzeInclusions(Collection<ModelKey> modelKeys)
	        throws AtomicOperationException,
	            IllegalArgumentException {
		Collection<T> models = this.readService.read(modelKeys);
		return this.analyzeInclusionsForModels(models);
	}

	// MARK: ModelInclusionReader
	@Override
	public ModelInclusionReaderAnalysis<T> analyzeInclusionsForModel(T model) throws IllegalArgumentException {
		return new ModelInclusionReaderAnalysisImpl<T>(model, this.delegate);
	}

	@Override
	public ModelInclusionReaderSetAnalysis<T> analyzeInclusionsForModels(Collection<T> models)
	        throws IllegalArgumentException {
		return new ModelInclusionReaderSetAnalysisImpl<T>(models, this.delegate);
	}

	@Override
	public String toString() {
		return "InclusionReaderImpl [delegate=" + this.delegate + "]";
	}

}
