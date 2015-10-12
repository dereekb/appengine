package com.dereekb.gae.model.extension.search.document.search.function;

import java.util.List;

import com.dereekb.gae.model.extension.search.document.search.SearchPair;
import com.dereekb.gae.model.extension.search.document.search.components.DocumentSearchQueryConverter;
import com.dereekb.gae.model.extension.search.document.search.components.DocumentSearchResource;
import com.dereekb.gae.server.search.document.DocumentQueryBuilder;
import com.dereekb.gae.utilities.function.staged.StagedFunction;
import com.dereekb.gae.utilities.function.staged.filter.FilteredStagedFunction;
import com.google.appengine.api.search.ScoredDocument;

/**
 * {@link StagedFunction} used for retrieving {@link ScoredDocument} instances
 * from the Search API, using input {@link SearchPair} instances.
 *
 * @author dereekb
 *
 * @param <Q>
 *            Query type.
 */
@Deprecated
public final class DocumentSearchFunction<Q> extends FilteredStagedFunction<Q, SearchPair<Q>>
        implements DocumentSearchResource {

	private final DocumentSearchQueryConverter<Q> converter;
	private final DocumentSearchResource resource;

	public DocumentSearchFunction(DocumentSearchQueryConverter<Q> converter) {
		this.converter = converter;
		this.resource = this;
	}

	@Override
	protected void doFunction() {
		Iterable<SearchPair<Q>> pairs = this.getWorkingObjects();

		for (SearchPair<Q> pair : pairs) {
			Q query = pair.getQuery();
			DocumentQueryBuilder builder = this.converter.convertQuery(query);
			List<ScoredDocument> results = this.resource.search(builder);
			pair.setResults(results);
		}
	}

	public DocumentSearchQueryConverter<Q> getConverter() {
		return this.converter;
	}

	/**
	 * Temporary. Will be replaced later, probably, when the
	 * DocumentSearchController type changes.
	 */
	@Override
	public List<ScoredDocument> search(DocumentQueryBuilder builder) {
		return DocumentSearchController.searchDocuments(builder);
	}

}
