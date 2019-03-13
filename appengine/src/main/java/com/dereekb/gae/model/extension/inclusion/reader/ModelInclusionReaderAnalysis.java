package com.dereekb.gae.model.extension.inclusion.reader;


/**
 * Single element analysis from a {@link ModelInclusionReader}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelInclusionReaderAnalysis<T> extends InclusionReaderAnalysis {

	/**
	 * Returns the analyzed model.
	 *
	 * @return the analyzed model. Never {@code null}.
	 */
	public T getAnalyzedModel();

}

