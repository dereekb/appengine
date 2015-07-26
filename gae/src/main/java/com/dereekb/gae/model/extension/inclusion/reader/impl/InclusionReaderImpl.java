package com.dereekb.gae.model.extension.inclusion.reader.impl;

import java.util.Collection;

import com.dereekb.gae.model.extension.inclusion.reader.InclusionReader;
import com.dereekb.gae.model.extension.inclusion.reader.InclusionReaderAnalysis;
import com.dereekb.gae.model.extension.inclusion.reader.InclusionReaderSetAnalysis;

/**
 * {@link InclusionReader} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class InclusionReaderImpl<T>
        implements InclusionReader<T> {

	private InclusionReaderSetAnalysisImplDelegate<T> delegate;

	public InclusionReaderImpl(InclusionReaderSetAnalysisImplDelegate<T> delegate) {
		this.delegate = delegate;
	}

	public InclusionReaderSetAnalysisImplDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(InclusionReaderSetAnalysisImplDelegate<T> delegate) {
		this.delegate = delegate;
	}

	// MARK: InclusionReader
	@Override
	public InclusionReaderAnalysis<T> analyzeInclusions(T model) throws IllegalArgumentException {
		return new InclusionReaderAnalysisImpl<T>(model, this.delegate);
	}

	@Override
	public InclusionReaderSetAnalysis<T> analyzeInclusions(Collection<T> models) throws IllegalArgumentException {
		return new InclusionReaderSetAnalysisImpl<T>(models, this.delegate);
	}

	@Override
	public String toString() {
		return "InclusionReaderImpl [delegate=" + this.delegate + "]";
	}

}
