package com.dereekb.gae.model.extension.inclusion.reader.impl;

import java.util.Collection;
import java.util.Set;

import com.dereekb.gae.model.extension.inclusion.exception.InclusionTypeUnavailableException;
import com.dereekb.gae.model.extension.inclusion.reader.InclusionReaderAnalysis;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link InclusionReaderAnalysis} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class InclusionReaderAnalysisImpl<T>
        implements InclusionReaderAnalysis<T> {

	private final T readModel;
	private final InclusionReaderAnalysisImplDelegate<T> delegate;

	public InclusionReaderAnalysisImpl(T readModel, InclusionReaderAnalysisImplDelegate<T> delegate) {
		this.readModel = readModel;
		this.delegate = delegate;
	}

	@Override
	public T getReadModel() {
		return this.readModel;
	}

	@Override
	public Set<String> getRelatedTypes() {
		return this.delegate.getRelatedTypes();
	}

	@Override
	public Collection<ModelKey> getKeysForType(String type) throws InclusionTypeUnavailableException {
		return this.delegate.getRelatedKeysForType(type, this.readModel);
	}

	@Override
	public String toString() {
		return "InclusionReaderAnalysisImpl [readModel=" + this.readModel + ", delegate=" + this.delegate + "]";
	}

}
