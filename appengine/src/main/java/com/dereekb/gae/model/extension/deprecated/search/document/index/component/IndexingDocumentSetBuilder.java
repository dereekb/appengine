package com.dereekb.gae.model.extension.search.document.index.component;

import com.dereekb.gae.server.deprecated.search.UniqueSearchModel;

/**
 * Used for building a {@link IndexingDocumentSet}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface IndexingDocumentSetBuilder<T extends UniqueSearchModel> {

	/**
	 * Returns the default document index.
	 *
	 * @return {@link String} for index. Never {@code null}.
	 */
	public String getIndex();

	/**
	 * Builds a new {@link IndexingDocumentSet}.
	 *
	 * @param models
	 *            input models. Never {@code null}.
	 * @param update
	 *
	 *
	 * @return {@link IndexingDocumentSet} for the input model. Never
	 *         {@code null}.
	 */
	public IndexingDocumentSet<T> buildSearchDocuments(Iterable<T> models,
	                                                   boolean update);

	/**
	 * Builds a new {@link IndexingDocumentSet} at the given index.
	 *
	 * @param models
	 *            input models. Never {@code null}.
	 * @param index
	 *            search index. Never {@code null}.
	 * @param update
	 *
	 * @return {@link IndexingDocumentSet} for the input model. Never
	 *         {@code null}.
	 */
	public IndexingDocumentSet<T> buildSearchDocuments(Iterable<T> models,
	                                                   String index,
	                                                   boolean update);

}
