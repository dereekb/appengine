package com.dereekb.gae.test.server.search;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.model.general.geo.impl.PointImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.search.query.SearchServiceQueryExpression;
import com.dereekb.gae.server.search.query.expression.builder.impl.field.GeoDistanceField;
import com.dereekb.gae.server.search.request.KeyedSearchDocumentPutRequestPair;
import com.dereekb.gae.server.search.request.SearchServiceDeleteRequest;
import com.dereekb.gae.server.search.request.SearchServiceIndexRequest;
import com.dereekb.gae.server.search.request.SearchServiceIndexRequestPair;
import com.dereekb.gae.server.search.request.SearchServiceQueryRequest;
import com.dereekb.gae.server.search.request.SearchServiceReadRequest;
import com.dereekb.gae.server.search.request.impl.SearchServiceDeleteRequestImpl;
import com.dereekb.gae.server.search.request.impl.SearchServiceIndexRequestImpl;
import com.dereekb.gae.server.search.request.impl.SearchServiceQueryRequestImpl;
import com.dereekb.gae.server.search.request.impl.SearchServiceReadRequestImpl;
import com.dereekb.gae.server.search.response.SearchServiceQueryResponse;
import com.dereekb.gae.server.search.response.SearchServiceReadResponse;
import com.dereekb.gae.server.search.service.SearchService;
import com.dereekb.gae.server.search.service.impl.GcsSearchServiceImpl;
import com.dereekb.gae.test.app.mock.context.AbstractGaeTestingContext;
import com.dereekb.gae.utilities.collections.IteratorUtility;
import com.dereekb.gae.utilities.collections.pairs.impl.ResultPairImpl;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.GeoPoint;

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

	public static final String TEST_FIELD_B = "testb";
	public static final String TEST_FIELD_B_VALUE = "false";

	private static final TestDocumentBuilder DEFAULT_TEST_DOCUMENT_BUILDER = new TestDocumentBuilder() {

		@Override
		public Builder buildDocumentForKey(ModelKey key) {
			Document.Builder documentBuilder = Document.newBuilder();
			documentBuilder.addField(Field.newBuilder().setName(TEST_FIELD).setAtom(TEST_FIELD_VALUE));
			documentBuilder.addField(Field.newBuilder().setName(TEST_FIELD_B).setAtom(TEST_FIELD_B_VALUE));
			return documentBuilder;
		}

	};

	private SearchService searchService;

	@BeforeEach
	public void initSearchService() {
		this.searchService = new GcsSearchServiceImpl();
	}

	// MARK: Read/Write Tests
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
		assertTrue(document.getOnlyField(TEST_FIELD).getAtom().equals(TEST_FIELD_VALUE));
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

	// MARK: Query Tests
	@Test
	public void testGeospacialQuery() {

		SearchServiceIndexRequest indexRequest = makeIndexRequest(new TestDocumentBuilder() {

			@Override
			public Builder buildDocumentForKey(ModelKey key) {
				Document.Builder documentBuilder = Document.newBuilder();

				GeoPoint geoPoint = new GeoPoint(0, 0);

				documentBuilder.addField(Field.newBuilder().setName(TEST_FIELD).setAtom(TEST_FIELD_VALUE));
				documentBuilder.addField(Field.newBuilder().setName(TEST_FIELD_B).setGeoPoint(geoPoint));

				return documentBuilder;
			}

		});

		this.searchService.updateIndex(indexRequest);

		// Search For Result
		SearchServiceQueryExpression expression = new GeoDistanceField(TEST_FIELD_B, new PointImpl(0, 0), 10);
		SearchServiceQueryRequest queryRequest = new SearchServiceQueryRequestImpl(TEST_INDEX, expression);

		SearchServiceQueryResponse response = this.searchService.queryIndex(queryRequest);
		Integer results = response.getReturnedResults();

		assertTrue(results > 0, "Should have returned a result.");

		// Search For No Results
		expression = new GeoDistanceField(TEST_FIELD_B, new PointImpl(80, 80), 1);
		queryRequest = new SearchServiceQueryRequestImpl(TEST_INDEX, expression);

		response = this.searchService.queryIndex(queryRequest);
		results = response.getReturnedResults();

		assertTrue(results == 0, "Should have not returned a result.");
	}

	// MARK: Internal
	private static SearchServiceIndexRequestImpl makeIndexRequest() {
		return makeIndexRequest(DEFAULT_TEST_DOCUMENT_BUILDER);
	};

	private static SearchServiceIndexRequestImpl makeIndexRequest(TestDocumentBuilder builder) {
		List<SearchServiceIndexRequestPair> requestPairs = new ArrayList<SearchServiceIndexRequestPair>();

		SearchServiceIndexRequestPair pair = makePair(builder);
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
		return makePair(DEFAULT_TEST_DOCUMENT_BUILDER);
	}

	private static KeyedSearchDocumentPutRequestPair<ModelKey> makePair(TestDocumentBuilder builder) {
		return makePair(ModelKey.generatorForKeyType(ModelKeyType.NUMBER).generate(), builder);
	}

	private static KeyedSearchDocumentPutRequestPair<ModelKey> makePair(ModelKey key,
	                                                                    TestDocumentBuilder builder) {
		Document document = builder.buildDocumentForKey(key).build();
		KeyedSearchDocumentPutRequestPair<ModelKey> pair = new KeyedSearchDocumentPutRequestPair<ModelKey>(key,
		        document);
		return pair;
	}

	private interface TestDocumentBuilder {

		public Document.Builder buildDocumentForKey(ModelKey key);

	}

}
