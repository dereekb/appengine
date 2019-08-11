package com.dereekb.gae.test.server.search;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.search.request.KeyedSearchDocumentPutRequestPair;
import com.dereekb.gae.server.search.request.SearchServiceDeleteRequest;
import com.dereekb.gae.server.search.request.SearchServiceIndexRequest;
import com.dereekb.gae.server.search.request.SearchServiceIndexRequestPair;
import com.dereekb.gae.server.search.request.SearchServiceReadRequest;
import com.dereekb.gae.server.search.request.impl.SearchServiceDeleteRequestImpl;
import com.dereekb.gae.server.search.request.impl.SearchServiceIndexRequestImpl;
import com.dereekb.gae.server.search.request.impl.SearchServiceReadRequestImpl;
import com.dereekb.gae.server.search.response.SearchServiceReadResponse;
import com.dereekb.gae.server.search.service.SearchService;
import com.dereekb.gae.server.search.service.impl.GcsSearchServiceImpl;
import com.dereekb.gae.test.app.mock.context.AbstractGaeTestingContext;
import com.dereekb.gae.utilities.collections.IteratorUtility;
import com.dereekb.gae.utilities.collections.pairs.impl.ResultPairImpl;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;

/**
 * Tests the {@link SearchService}.
 *
 * @author dereekb
 *
 */
public class SearchServiceTests extends AbstractGaeTestingContext {

	public static final String TEST_INDEX = "test";
	public static final String TEST_FIELD = "test";
	public static final String TEST_FIELD_VALUE = "true";

	private SearchService searchService;

	@BeforeEach
	public void initSearchService() {
		this.searchService = new GcsSearchServiceImpl();
	}

	@Test
	public void testUpdatingIndexWithOne() {
		List<SearchServiceIndexRequestPair> requestPairs = new ArrayList<SearchServiceIndexRequestPair>();

		SearchServiceIndexRequestPair pair = makePair();
		requestPairs.add(pair);

		SearchServiceIndexRequest indexRequest = new SearchServiceIndexRequestImpl(TEST_INDEX, requestPairs);
		this.searchService.updateIndex(indexRequest);

		String documentKey = pair.getDocumentKey();
		assertNotNull(documentKey);
	}

	@Test
	public void testReadingDocument() {
		SearchServiceIndexRequest indexRequest = makeIndexRequest();
		this.searchService.updateIndex(indexRequest);

		SearchServiceReadRequest readRequest = makeReadRequest(indexRequest);
		SearchServiceReadResponse readResponse = this.searchService.readDocuments(readRequest);

		List<String> keys = IteratorUtility.iterableToList(readRequest.getDocumentKeys());
		List<Document> documents = readResponse.getDocuments();

		assertTrue(documents.size() > 0);
		assertTrue(documents.size() == keys.size());

		Document document = readResponse.getFirstDocument();
		assertTrue(document.getFieldNames().contains(TEST_FIELD));
		assertTrue(document.getFields(TEST_FIELD).iterator().next().getAtom().equals(TEST_FIELD_VALUE));
	}

	@Test
	public void testDeletingDocument() {
		SearchServiceIndexRequest indexRequest = makeIndexRequest();
		this.searchService.updateIndex(indexRequest);

		SearchServiceReadRequest readRequest = makeReadRequest(indexRequest);
		SearchServiceReadResponse readResponse = this.searchService.readDocuments(readRequest);

		List<String> keys = IteratorUtility.iterableToList(readRequest.getDocumentKeys());
		List<Document> documents = readResponse.getDocuments();

		assertTrue(documents.size() == keys.size());

		SearchServiceDeleteRequest deleteRequest = new SearchServiceDeleteRequestImpl(readRequest);
		this.searchService.deleteFromIndex(deleteRequest);

		readResponse = this.searchService.readDocuments(readRequest);
		documents = readResponse.getDocuments();

		assertTrue(readResponse.getMissingDocuments().size() == keys.size());
	}

	// MARK: Internal
	private static SearchServiceIndexRequestImpl makeIndexRequest() {
		List<SearchServiceIndexRequestPair> requestPairs = new ArrayList<SearchServiceIndexRequestPair>();

		SearchServiceIndexRequestPair pair = makePair();
		requestPairs.add(pair);

		SearchServiceIndexRequestImpl indexRequest = new SearchServiceIndexRequestImpl(TEST_INDEX, requestPairs);
		return indexRequest;
	};

	private static SearchServiceReadRequestImpl makeReadRequest(SearchServiceIndexRequest indexRequest) {
		List<String> documentKeys = ResultPairImpl.getResults(indexRequest.getRequestPairs());
		SearchServiceReadRequestImpl readRequest = new SearchServiceReadRequestImpl(indexRequest.getIndexName(),
		        documentKeys);
		return readRequest;
	};

	private static KeyedSearchDocumentPutRequestPair<ModelKey> makePair() {
		return makePair(ModelKey.generatorForKeyType(ModelKeyType.NUMBER).generate());
	}

	private static KeyedSearchDocumentPutRequestPair<ModelKey> makePair(ModelKey key) {

		Document.Builder documentBuilder = Document.newBuilder();
		documentBuilder.addField(Field.newBuilder().setName(TEST_FIELD).setAtom(TEST_FIELD_VALUE));

		Document document = documentBuilder.build();
		KeyedSearchDocumentPutRequestPair<ModelKey> pair = new KeyedSearchDocumentPutRequestPair<ModelKey>(key,
		        document);

		return pair;
	}

}
