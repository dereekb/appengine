package com.dereekb.gae.test.server.search.deprecated;

import static org.junit.assertFalse;
import static org.junit.assertNotNull;
import static org.junit.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.server.search.DocumentSearchController;
import com.dereekb.gae.server.search.DocumentSearchController.DocumentSearchIndexIterator;
import com.dereekb.gae.server.search.document.DocumentIdentifierRequest;
import com.dereekb.gae.server.search.document.DocumentQueryBuilder;
import com.dereekb.gae.server.search.document.fields.DocumentQueryTextField;
import com.dereekb.gae.test.spring.CoreServiceTestingContext;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.GeoPoint;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;

/**
 * Tests for the Search Controller.
 * 
 * @author dereekb
 * 
 */
public class DocumentSearchControllerTest extends CoreServiceTestingContext {

	private static final String DEFAULT_INDEX = "index";

	public Document buildTestDocument(String documentId) {

		Document.Builder documentBuilding = Document.newBuilder();
		documentBuilding = documentBuilding.setId(documentId);
		documentBuilding = documentBuilding.addField(Field.newBuilder().setName("atom").setAtom("Atom").build());
		documentBuilding = documentBuilding.addField(Field.newBuilder().setName("text")
		        .setText("This is the text value for " + documentId).build());
		documentBuilding = documentBuilding.addField(Field.newBuilder().setName("html")
		        .setHTML("This is the <bold>text value</bold> for " + documentId).build());

		Date date = new Date();
		documentBuilding = documentBuilding.addField(Field.newBuilder().setName("date").setDate(date).build());
		documentBuilding = documentBuilding.addField(Field.newBuilder().setName("number").setNumber(100).build());
		documentBuilding = documentBuilding.addField(Field.newBuilder().setName("location")
		        .setGeoPoint(new GeoPoint(0, 0)).build());

		return documentBuilding.build();
	}

	@Test
	public void testSavingRawDocument() {

		String indexName = DEFAULT_INDEX + "save";

		Document testDocument = buildTestDocument(null);
		String testDocumentId = DocumentSearchController.put(testDocument, indexName, false);
		assertNotNull(testDocumentId);

		List<Document> documents = new ArrayList<Document>();
		documents.add(testDocument);
		documents.add(testDocument);
		documents.add(testDocument);

		List<String> documentIds = DocumentSearchController.put(documents, indexName, false);
		assertNotNull(documentIds);
		assertTrue(documentIds.size() == 3);
	}

	@Test
	public void testReadingRawDocument() {

		String indexName = DEFAULT_INDEX + "read";

		Document testDocument = buildTestDocument(null);
		String testDocumentId = DocumentSearchController.put(testDocument, indexName, false);
		assertNotNull(testDocumentId);

		DocumentIdentifierRequest request = new DocumentIdentifierRequest(indexName, testDocumentId);
		List<Document> readDocuments = DocumentSearchController.read(request);
		assertNotNull(readDocuments);
		assertTrue(readDocuments.size() == 1);
	}

	@Test
	public void testSearchingDocuments() {

		String indexName = DEFAULT_INDEX + "search";

		Document testDocument = buildTestDocument(null);
		String testDocumentId = DocumentSearchController.put(testDocument, indexName, false);
		assertNotNull(testDocumentId);

		DocumentIdentifierRequest request = new DocumentIdentifierRequest(indexName, testDocumentId);
		List<Document> readDocuments = DocumentSearchController.read(request);
		assertNotNull(readDocuments);
		assertTrue(readDocuments.size() == 1);

		DocumentQueryTextField textFieldQuery = new DocumentQueryTextField("text", "this");
		DocumentQueryBuilder documentQuery = new DocumentQueryBuilder(indexName, textFieldQuery);

		Results<ScoredDocument> results = DocumentSearchController.search(documentQuery);
		Collection<ScoredDocument> documentResults = results.getResults();

		assertNotNull(documentResults);
		assertFalse(documentResults.isEmpty());
	}

	@Test
	public void testDocumentSearchIndexIterator() {
		String indexName = DEFAULT_INDEX + "iterator";
		Integer genCount = 50;
		Integer limit = (genCount / 2);

		for (int i = 0; i < genCount; i += 1) {
			Document testDocument = buildTestDocument(null);
			DocumentSearchController.put(testDocument, indexName, false);
		}

		DocumentSearchIndexIterator iterator = DocumentSearchController.makeIndexIterator(indexName);
		iterator.setLimit(limit);

		List<String> firstBatch = iterator.getNextDocumentsIdentifiers();
		assertTrue(firstBatch.size() == limit);

		List<String> secondBatch = iterator.getNextDocumentsIdentifiers();
		assertTrue(secondBatch.size() == limit);

		List<String> thirdBatch = iterator.getNextDocumentsIdentifiers();
		assertTrue(thirdBatch.isEmpty());
		assertTrue(iterator.hasReachedEnd());
	}

	@Test
	public void testDeletingRawDocument() {
		String indexName = DEFAULT_INDEX + "delete";

		Document testDocument = buildTestDocument(null);
		String testDocumentId = DocumentSearchController.put(testDocument, indexName, false);
		assertNotNull(testDocumentId);

		DocumentSearchController.delete(testDocumentId, indexName, false);

		DocumentIdentifierRequest request = new DocumentIdentifierRequest(indexName, testDocumentId);
		List<Document> readDocuments = DocumentSearchController.read(request);
		assertNotNull(readDocuments);
		assertTrue(readDocuments.get(0) == null);

		// Try Multiple
		List<Document> documents = new ArrayList<Document>();
		documents.add(testDocument);
		documents.add(testDocument);
		documents.add(testDocument);

		List<String> testDocumentIds = DocumentSearchController.put(documents, indexName, false);
		assertNotNull(testDocumentIds);
		assertTrue(testDocumentIds.size() == 3);

		List<String> deleteDocumentIds = new ArrayList<String>(testDocumentIds);
		deleteDocumentIds.remove(2);

		DocumentSearchController.delete(deleteDocumentIds, indexName, false);

		List<Document> readTestDocuments = DocumentSearchController.read(testDocumentIds, indexName);
		assertNotNull(readTestDocuments);
		assertFalse(readTestDocuments.isEmpty());
	}

	@Test
	public void testDeleteDocumentSearchIndexWithIterator() {
		String indexName = DEFAULT_INDEX + "deleteIterator";
		Integer genCount = 50;
		Integer limit = (genCount / 2);

		for (int i = 0; i < genCount; i += 1) {
			Document testDocument = buildTestDocument(null);
			DocumentSearchController.put(testDocument, indexName, false);
		}

		DocumentSearchIndexIterator iterator = DocumentSearchController.makeIndexIterator(indexName);
		iterator.setLimit(limit);

		while (iterator.hasReachedEnd() == false) {
			List<String> batch = iterator.getNextDocumentsIdentifiers();
			DocumentSearchController.delete(batch, indexName, false);
		}

		List<String> finalBatch = iterator.getNextDocumentsIdentifiers();
		assertTrue(finalBatch.isEmpty());
		assertTrue(iterator.hasReachedEnd());
	}

}
