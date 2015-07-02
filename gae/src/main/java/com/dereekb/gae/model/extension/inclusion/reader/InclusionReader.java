package com.dereekb.gae.model.extension.inclusion.reader;

/**
 * Used to build a map of models for the
 *
 * @author dereekb
 *
 * @param <T>
 *            Primary model
 */
public interface InclusionReader<T> {

	/**
	 * Analyzes the input model.
	 *
	 * @param model
	 *            Model to analyze.
	 * @return {@link InclusionReaderAnalysis} for the input model.
	 * @throws IllegalArgumentException
	 *             if the model is null.
	 */
	public InclusionReaderAnalysis<T> analyzeInclusions(T model) throws IllegalArgumentException;

	/**
	 * Analyzes the input models.
	 *
	 * @param models
	 *            Models to analyze.
	 * @return {@link InclusionReadersetAnalysis} containing the input models.
	 * @throws IllegalArgumentException
	 *             if the models are null or empty.
	 */
	public InclusionReaderSetAnalysis<T> analyzeInclusions(Iterable<T> models) throws IllegalArgumentException;

}
