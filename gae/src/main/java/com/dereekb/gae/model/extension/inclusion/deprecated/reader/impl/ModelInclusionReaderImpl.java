package com.dereekb.gae.model.extension.inclusion.reader.impl;

import java.util.Collection;

import com.dereekb.gae.model.extension.inclusion.reader.ModelInclusionReader;
import com.dereekb.gae.model.extension.inclusion.reader.ModelInclusionReaderAnalysis;
import com.dereekb.gae.model.extension.inclusion.reader.ModelInclusionReaderSetAnalysis;
import com.dereekb.gae.server.datastore.models.UniqueModel;

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

	private InclusionReaderSetAnalysisDelegate<T> delegate;

	public ModelInclusionReaderImpl(InclusionReaderSetAnalysisDelegate<T> delegate) {
		this.setDelegate(delegate);
	}

	public InclusionReaderSetAnalysisDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(InclusionReaderSetAnalysisDelegate<T> delegate) {
		this.delegate = delegate;
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
		return "ModelInclusionReaderImpl [delegate=" + this.delegate + "]";
	}

}
