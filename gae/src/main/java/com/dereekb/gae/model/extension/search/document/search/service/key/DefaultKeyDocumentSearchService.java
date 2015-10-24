package com.dereekb.gae.model.extension.search.document.search.service.key;

import java.util.List;

import com.dereekb.gae.model.extension.search.document.SearchDocumentUtility;
import com.dereekb.gae.model.extension.search.document.search.SearchPair;
import com.dereekb.gae.model.extension.search.document.search.function.DocumentSearchFunction;
import com.dereekb.gae.model.extension.search.document.search.utility.ScoredDocumentKeyReader;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.factory.Factory;
import com.google.appengine.api.search.ScoredDocument;

/**
 * Default {@link KeyDocumentSearchService} implementation that uses a
 * {@link ScoredDocumentKeyReader} and a {@link DocumentSearchFunction} factory
 * to retrieve document.
 *
 * @author dereekb
 *
 * @param <Q>
 *            Query type.
 */
@Deprecated
public final class DefaultKeyDocumentSearchService<Q>
        implements KeyDocumentSearchService<Q> {

	private final ScoredDocumentKeyReader<ModelKey> reader;
	private final Factory<DocumentSearchFunction<Q>> factory;

	public DefaultKeyDocumentSearchService(ScoredDocumentKeyReader<ModelKey> reader,
	        Factory<DocumentSearchFunction<Q>> factory) {
		this.reader = reader;
		this.factory = factory;
	}

	public Factory<DocumentSearchFunction<Q>> getFactory() {
		return this.factory;
	}

	public ScoredDocumentKeyReader<ModelKey> getReader() {
		return this.reader;
	}

	@Override
	public List<ModelKey> searchKeys(Q query) {
		DocumentSearchFunction<Q> function = this.factory.make();
		SearchPair<Q> pair = new SearchPair<Q>(query);

		function.addObject(pair);
		function.run();

		List<ScoredDocument> documents = pair.getResults();
		List<ModelKey> keys = SearchDocumentUtility.readModelKeys(this.reader, documents);
		return keys;
	}

}
