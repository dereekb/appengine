package com.dereekb.gae.model.extension.inclusion.reader;

import java.util.Collection;

/**
 * Used to build a map of models for the
 *
 * @author dereekb
 *
 * @param <T>
 *            primary model
 */
public interface ModelInclusionReader<T>
        extends InclusionReader {

	/**
	 * Analyzes the input model.
	 *
	 * @param model
	 *            Model to analyze.
	 * @return {@link ModelInclusionReaderAnalysis} for the input model.
	 * @throws IllegalArgumentException
	 *             if the model is null.
	 */
	public ModelInclusionReaderAnalysis<T> analyzeInclusionsForModel(T model) throws IllegalArgumentException;

	/**
	 * Analyzes the input models.
	 *
	 * @param models
	 *            Models to analyze.
	 * @return {@link InclusionReaderSetAnalysis} containing the input models.
	 * @throws IllegalArgumentException
	 *             if the models are null or empty.
	 */
	public ModelInclusionReaderSetAnalysis<T> analyzeInclusionsForModels(Collection<T> models)
	        throws IllegalArgumentException;

}
