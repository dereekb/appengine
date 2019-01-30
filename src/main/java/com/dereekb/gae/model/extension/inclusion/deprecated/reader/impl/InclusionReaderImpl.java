package com.dereekb.gae.model.extension.inclusion.reader.impl;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.components.AtomicReadService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.inclusion.reader.InclusionReader;
import com.dereekb.gae.model.extension.inclusion.reader.InclusionReaderAnalysis;
import com.dereekb.gae.model.extension.inclusion.reader.InclusionReaderSetAnalysis;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link InclusionReader} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class InclusionReaderImpl<T extends UniqueModel> extends ModelInclusionReaderImpl<T>
        implements InclusionReader {

	private AtomicReadService<T> readService;

	public InclusionReaderImpl(AtomicReadService<T> readService, InclusionReaderSetAnalysisDelegate<T> delegate) {
		super(delegate);
		this.setReadService(readService);
	}

	public AtomicReadService<T> getReadService() {
		return this.readService;
	}

	public void setReadService(AtomicReadService<T> readService) {
		this.readService = readService;
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

	@Override
	public String toString() {
		return "InclusionReaderImpl [readService=" + this.readService + "]";
	}

}
