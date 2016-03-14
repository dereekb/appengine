package com.dereekb.gae.model.extension.inclusion.reader;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Used for analyzing related/included types for a model.
 *
 * @author dereekb
 *
 */
public interface InclusionReader {

	/**
	 * Analyzes the model at the specified key.
	 *
	 * @param model
	 *            Model to analyze.
	 * @return {@link ModelInclusionReaderAnalysis} for the input model.
	 * @throws IllegalArgumentException
	 *             if the model is null.
	 */
	public InclusionReaderAnalysis analyzeInclusions(ModelKey modelKey)
	        throws AtomicOperationException,
	            IllegalArgumentException;

	/**
	 * Analyzes the models with the specified key.
	 *
	 * @param models
	 *            Models to analyze.
	 * @return {@link InclusionReaderSetAnalysis} containing the input models.
	 * @throws IllegalArgumentException
	 *             if the models are null or empty.
	 */
	public InclusionReaderSetAnalysis analyzeInclusions(Collection<ModelKey> modelKeys)
	        throws AtomicOperationException,
	            IllegalArgumentException;

}
