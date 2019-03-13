package com.dereekb.gae.model.extension.inclusion.reader.impl;

import java.util.Collection;

import com.dereekb.gae.model.extension.inclusion.reader.ModelInclusionReader;
import com.dereekb.gae.model.extension.inclusion.reader.ModelInclusionReaderAnalysis;
import com.dereekb.gae.model.extension.inclusion.reader.ModelInclusionReaderSetAnalysis;
import com.dereekb.gae.model.extension.links.system.readonly.TypedLinkModelReader;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link ModelInclusionReader} implementation.
 * 
 * @author dereekb
 *
 * @param <T> model type
 */
public class ModelInclusionReaderImpl<T extends UniqueModel>
        implements ModelInclusionReader<T> {
	
	private TypedLinkModelReader<T> accessor;

	public ModelInclusionReaderImpl(TypedLinkModelReader<T> accessor) {
		this.setAccessor(accessor);
	}

	public TypedLinkModelReader<T> getAccessor() {
		return this.accessor;
	}
	
	public void setAccessor(TypedLinkModelReader<T> accessor) {
		if (accessor == null) {
			throw new IllegalArgumentException("accessor cannot be null.");
		}
	
		this.accessor = accessor;
	}

	// MARK: ModelInclusionReader
	@Override
	public ModelInclusionReaderAnalysis<T> analyzeInclusionsForModel(T model) throws IllegalArgumentException {
		return new LazyModelInclusionReaderAnalysis<T>(model, this.accessor);
	}

	@Override
	public ModelInclusionReaderSetAnalysis<T> analyzeInclusionsForModels(Collection<T> models)
	        throws IllegalArgumentException {
		return ModelInclusionReaderSetAnalysisImpl.makeSetAnalysis(models, this);
	}

	@Override
	public String toString() {
		return "ModelInclusionReaderImpl [accessor=" + this.accessor + "]";
	}

}
