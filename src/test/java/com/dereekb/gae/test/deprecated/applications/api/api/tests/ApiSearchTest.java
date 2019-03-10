package com.dereekb.gae.test.applications.api.api.tests;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.search.document.SearchableUniqueModel;
import com.dereekb.gae.model.extension.search.document.index.IndexAction;
import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.api.model.extension.search.SearchExtensionApiController;
import com.dereekb.gae.web.api.model.extension.search.impl.ApiMultiSearchResponseData;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseDataImpl.ApiResponseTypeDataWrapper;


@Deprecated
public abstract class ApiSearchTest<T extends SearchableUniqueModel> extends ApiApplicationTestContext {

	protected String searchType;
	protected TestModelGenerator<T> generator;
	protected DocumentIndexService<T> indexService;

	@Autowired
	@Qualifier("searchExtensionApiController")
	protected SearchExtensionApiController controller;

	public String getSearchType() {
		return this.searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public TestModelGenerator<T> getGenerator() {
		return this.generator;
	}

	public void setGenerator(TestModelGenerator<T> generator) {
		this.generator = generator;
	}

	public DocumentIndexService<T> getIndexService() {
		return this.indexService;
	}

	public void setIndexService(DocumentIndexService<T> indexService) {
		this.indexService = indexService;
	}

	public SearchExtensionApiController getController() {
		return this.controller;
	}

	public void setController(SearchExtensionApiController controller) {
		this.controller = controller;
	}

	@Test
	public void testNoTextSingleSearch() {
		this.generateIndexedModels();

		Integer limit = null;
		Boolean getKeys = false;
		Map<String, String> parameters = new HashMap<>();

		ApiResponse response = this.controller.searchSingle(this.searchType, parameters, limit, getKeys);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getResponsePrimaryData());
	}

	@Test
	public void testNoTextMultiSearch() {
		this.generateIndexedModels();

		String query = "";
		Integer limit = null;
		Boolean getKeys = false;
		Map<String, String> parameters = new HashMap<>();
		Set<String> types = new HashSet<String>();

		types.add(this.searchType);

		ApiResponse response = this.controller.searchMultiple(query, types, parameters, limit, getKeys);
		Assert.assertNotNull(response);

		ApiResponseTypeDataWrapper data = (ApiResponseTypeDataWrapper) response.getResponsePrimaryData();
		ApiMultiSearchResponseData responseData = (ApiMultiSearchResponseData) data.getResponseData();
		Assert.assertNotNull(responseData.getResults().get(this.searchType.toLowerCase()));
	}

	@Test
	public void testQueryingModels() {
		int count = 10;
		this.generator.generate(count);

		Integer limit = null;
		Map<String, String> parameters = null;
		ApiResponse response = this.controller.querySingle(this.searchType, parameters, limit, false);
		ApiResponseData data = response.getResponsePrimaryData();

		Assert.assertNotNull(response);
		Assert.assertNotNull(data);
	}

	@Test
	public void testQueryingKeys() {
		int count = 10;
		this.generator.generate(count);

		Integer limit = null;
		Map<String, String> parameters = null;
		ApiResponse response = this.controller.querySingle(this.searchType, parameters, limit, true);
		ApiResponseData data = response.getResponsePrimaryData();

		Assert.assertNotNull(response);
		Assert.assertNotNull(data);
	}

	protected List<T> generateIndexedModels() {
		return this.generateIndexedModels(10);
	}

	protected List<T> generateIndexedModels(int count) {
		List<T> models = this.generate(count);
		this.indexService.indexChange(models, IndexAction.INDEX);
		return models;
	}

	protected List<T> generate(int count) {
		List<T> models = this.generator.generate(count);

		for (T model : models) {
			this.createRelated(model);
		}

		return models;
	}

	protected void createRelated(T model) {}

}
