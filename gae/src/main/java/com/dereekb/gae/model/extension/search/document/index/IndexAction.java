package com.dereekb.gae.model.extension.search.document.index;


/**
 * Describes the kind of action/change to perform with the given items.
 *
 * @author dereekb
 *
 * @see {@link IndexPair}
 */
public enum IndexAction {

	/**
	 * Indexes the target model in the search index.
	 *
	 * If the model is not indexed, it will be indexed and saved.
	 */
	INDEX,

	/**
	 * Removes the model from the search index.
	 */
	UNINDEX;

}
