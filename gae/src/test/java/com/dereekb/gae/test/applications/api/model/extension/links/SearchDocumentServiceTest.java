package com.dereekb.gae.test.applications.api.model.extension.links;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.search.service.SearchDocumentService;
import com.dereekb.gae.server.search.service.request.DocumentPutRequest;
import com.dereekb.gae.server.search.service.request.DocumentPutRequestModel;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.FactoryMakeFailureException;
import com.google.appengine.api.search.Document;

public class SearchDocumentServiceTest extends ApiApplicationTestContext {

	private static final String TEST_INDEX = "";
	private static final Integer MODEL_GENERATION_COUNT = 1;

	@Autowired
	@Qualifier("searchSystem")
	private SearchDocumentService service;

	@Test
	public void testReadService() {

	}

	@Test
	public void testIndexService() {
		final Collection<DocumentPutRequestModel> models = this.makeRequestModels();

		TestDocumentRequestModel.assertModelsIdentifierCheck(models, false);

		DocumentPutRequest request = new DocumentPutRequest() {

			@Override
			public String getIndexName() {
				return SearchDocumentServiceTest.TEST_INDEX;
			}

			@Override
			public boolean isUpdate() {
				return false;
			}

			@Override
			public Collection<DocumentPutRequestModel> getPutRequestModels() {
				return models;
			}

		};

		this.service.put(request);

		// Check that models have an identifier.
		TestDocumentRequestModel.assertModelsIdentifierCheck(models, true);
	}

	@Test
	public void testDeleteService() {

	}

	@Test
	public void testQueryService() {

	}

	// MARK: Internal
	public Collection<DocumentPutRequestModel> makeRequestModels() {
		Factory<Document.Builder> factory = new Factory<Document.Builder>() {

			@Override
			public Document.Builder make() throws FactoryMakeFailureException {
				Document.Builder builder = Document.newBuilder();

				// TODO: Build content.

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
		public String getSearchDocumentIdentifier() {
			return this.searchIdentifier;
		}

		@Override
		public void setSearchDocumentIdentifier(String identifier) {
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
				boolean modelHasIdentifier = (model.getSearchDocumentIdentifier() == null);

				if (modelHasIdentifier != hasIdentifier) {
					match = false;
					break;
				}
			}

			return match;
		}

	}

}
