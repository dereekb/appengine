package com.dereekb.gae.model.extension.search.document.index.filter;

/**
 * {@link CanIndexDocumentFilter} delegate.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface CanIndexDocumentFilterDelegate<T> {

	/**
	 * Returns {@code true} if the object is eligible for indexing.
	 *
	 * @param indexable
	 *            Object to test.
	 * @return {@code true} if the object can be indexed.
	 */
	public boolean canIndex(T indexable);

}
