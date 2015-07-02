package com.dereekb.gae.model.extension.search.document.index.filter;

public interface CanIndexDocumentFilterDelegate<T> {

	/**
	 * Returns true if the object is eligible for indexing.
	 * 
	 * @param indexable
	 * @return
	 */
	public boolean canIndex(T indexable);

}
