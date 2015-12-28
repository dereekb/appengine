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
import com.dereekb.gae.web.api.shared.response.ApiResponse;


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
		Boolean getModels = true;
		Map<String, String> parameters = new HashMap<>();

		ApiResponse response = this.controller.searchSingle(this.searchType, parameters, limit, getModels);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getResponsePrimaryData());
	}

	@Test
	public void testNoTextMultiSearch() {
		this.generateIndexedModels();

		String query = "";
		Integer limit = null;
		Boolean getModels = true;
		Map<String, String> parameters = new HashMap<>();
		Set<String> types = new HashSet<String>();

		types.add(this.searchType);

		ApiResponse response = this.controller.searchMultiple(query, types, parameters, limit, getModels);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getResponseIncludedData().get(this.searchType));
	}

	protected List<T> generateIndexedModels() {
		return this.generateIndexedModels(10);
	}

	protected List<T> generateIndexedModels(int count) {
		List<T> models = this.generator.generate(count);
		this.indexService.indexChange(models, IndexAction.INDEX);
		return models;
	}

}
