package com.dereekb.gae.test.applications.api.model.extension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.search.system.SearchDocumentSystem;
import com.dereekb.gae.server.search.system.request.DocumentPutRequest;
import com.dereekb.gae.server.search.system.request.DocumentPutRequestModel;
import com.dereekb.gae.server.search.system.request.impl.DocumentModelIdentifierRequestImpl;
import com.dereekb.gae.server.search.system.response.SearchDocumentReadResponse;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.GeoPoint;

public class SearchDocumentServiceTest extends ApiApplicationTestContext {

	private static final String TEST_INDEX = "TestIndex";
	private static final Integer MODEL_GENERATION_COUNT = 1;

	@Autowired
	@Qualifier("searchSystem")
	private SearchDocumentSystem service;

	@Test
	public void testReadService() {
		Collection<DocumentPutRequestModel> models = this.createAndIndexModels();

		TestDocumentRequestModel.assertModelsIdentifierCheck(models, true);
		this.assertAllModelDocumentsExist(models);
	}

	@Test
	public void testIndexService() {
		final Collection<DocumentPutRequestModel> models = this.makeRequestModels();

		TestDocumentRequestModel.assertModelsIdentifierCheck(models, false);

		this.putModels(models, false);

		// Check that models have an identifier.
		TestDocumentRequestModel.assertModelsIdentifierCheck(models, true);
	}

	@Test
	public void testDeleteService() {
		Collection<DocumentPutRequestModel> models = this.createAndIndexModels();

		this.assertAllModelDocumentsExist(models);

		this.deleteModels(models);

		this.assertAllModelDocumentsDoNotExist(models);
	}

	@Test
	public void testQueryService() {

	}

	@Test
	public void testSearchIndexIterator() {

	}

	// MARK: Assertions
	public void assertAllModelDocumentsExist(Collection<DocumentPutRequestModel> models) {
		DocumentModelIdentifierRequestImpl idRequest = new DocumentModelIdentifierRequestImpl(
		        SearchDocumentServiceTest.TEST_INDEX, models);

		SearchDocumentReadResponse readResponse = this.service.readDocuments(idRequest);
		List<Document> documents = readResponse.getDocuments();

		Assert.assertTrue(documents.size() == models.size());
		Assert.assertTrue(readResponse.getMissingDocuments().isEmpty());
	}

	public void assertAllModelDocumentsDoNotExist(Collection<DocumentPutRequestModel> models) {
		DocumentModelIdentifierRequestImpl idRequest = new DocumentModelIdentifierRequestImpl(
		        SearchDocumentServiceTest.TEST_INDEX, models);

		SearchDocumentReadResponse readResponse = this.service.readDocuments(idRequest);
		List<String> missingDocuments = readResponse.getMissingDocuments();

		Assert.assertTrue(missingDocuments.size() == models.size());
		Assert.assertTrue(readResponse.getDocuments().isEmpty());
	}

	// MARK: Internal

	private Collection<DocumentPutRequestModel> createAndIndexModels() {
		Collection<DocumentPutRequestModel> models = this.makeRequestModels();
		this.putModels(models, false);
		return models;
	}

	private void putModels(final Collection<DocumentPutRequestModel> models,
	                       final boolean isUpdate) {

		DocumentPutRequest request = new DocumentPutRequest() {

			@Override
			public String getIndexName() {
				return SearchDocumentServiceTest.TEST_INDEX;
			}

			@Override
			public boolean isUpdate() {
				return isUpdate;
			}

			@Override
			public Collection<DocumentPutRequestModel> getPutRequestModels() {
				return models;
			}

		};

		this.service.put(request);
	}

	private void deleteModels(final Collection<DocumentPutRequestModel> models) {
		DocumentModelIdentifierRequestImpl request = new DocumentModelIdentifierRequestImpl(
		        SearchDocumentServiceTest.TEST_INDEX, models);
		this.service.deleteDocuments(request);
	}

	public Collection<DocumentPutRequestModel> makeRequestModels() {
		Factory<Document.Builder> factory = new Factory<Document.Builder>() {

			@Override
			public Document.Builder make() throws FactoryMakeFailureException {
				Document.Builder builder = Document.newBuilder();

				builder = builder.addField(Field.newBuilder().setName("atom").setAtom("Atom").build());
				builder = builder.addField(Field.newBuilder().setName("text").setText("This is the text value.")
				        .build());
				builder = builder.addField(Field.newBuilder().setName("html")
				        .setHTML("This is the <bold>text value</bold>.").build());

				Date date = new Date();
				builder = builder.addField(Field.newBuilder().setName("date").setDate(date).build());
				builder = builder.addField(Field.newBuilder().setName("number").setNumber(100).build());
				builder = builder.addField(Field.newBuilder().setName("location").setGeoPoint(new GeoPoint(0, 0))
				        .build());

				return builder;
			}

		};

		return this.makeRequestModels(factory);
	}

	public Collection<DocumentPutRequestModel> makeRequestModels(Factory<Document.Builder> factory) {
		List<DocumentPutRequestModel> models = new ArrayList<>();

		for (Integer i = 0; i < MODEL_GENERATION_COUNT; i += 1) {
			TestDocumentRequestModel model = new TestDocumentRequestModel();
			model.setDocumentBuilder(factory.make());
			models.add(model);
		}

		return models;
	}

	private static class TestDocumentRequestModel
	        implements DocumentPutRequestModel {

		private Document.Builder builder;
		private String searchIdentifier;

		@Override
		public Document getDocument() {
			this.builder.setId(this.searchIdentifier);
			return this.builder.build();
		}

		public void setDocumentBuilder(Document.Builder builder) {
			this.builder = builder;
		}

		@Override
		public String getSearchIdentifier() {
			return this.searchIdentifier;
		}

		@Override
		public void setSearchIdentifier(String identifier) {
			this.searchIdentifier = identifier;
		}

		public static void assertModelsIdentifierCheck(Iterable<DocumentPutRequestModel> models,
		                                               boolean hasIdentifier) {
			boolean match = checkModelsForIdentifiers(models, hasIdentifier);
			Assert.assertTrue(match);
		}

		public static boolean checkModelsForIdentifiers(Iterable<DocumentPutRequestModel> models,
		                                                boolean hasIdentifier) {
			boolean match = true;

			for (DocumentPutRequestModel model : models) {
				boolean modelHasIdentifier = (model.getSearchIdentifier() != null);

				if (modelHasIdentifier != hasIdentifier) {
					match = false;
					break;
				}
			}

			return match;
		}

	}

}
