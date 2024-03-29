package com.dereekb.gae.test.applications.api.model.login.login;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.StagedDocumentBuilder;
import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.model.extension.search.document.search.service.model.ModelDocumentSearchService;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.search.document.query.LoginSearchRequest;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.deprecated.applications.api.model.tests.extension.ModelSearchDocumentTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

@Disabled
@Deprecated
public class LoginSearchDocumentTest extends ModelSearchDocumentTest<Login> {

	@Autowired
	@Qualifier("loginRegistry")
	private ObjectifyRegistry<Login> loginRegistry;
	@Override
	@Autowired
	@Qualifier("loginTestModelGenerator")
	public void setGenerator(TestModelGenerator<Login> generator) {
		super.setGenerator(generator);
	}

	@Override
	@Autowired
	@Qualifier("loginDocumentIndexService")
	public void setIndexService(DocumentIndexService<Login> service) {
		super.setIndexService(service);
	}

	@Override
	@Autowired
	@Qualifier("loginSearchDocumentBuilder")
	public void setBuilder(StagedDocumentBuilder<Login> builder) {
		super.setBuilder(builder);
	}

	@Autowired
	@Qualifier("loginSearchService")
	private ModelDocumentSearchService<Login, LoginSearchRequest> searchService;

	@Test
	public void testSearchService() {
		/*
		List<Login> models = this.getGenerator().generate(10);

		// Index Models
		Login model = models.get(0);

		Date date = new Date(0); // Set for later query test.
		model.setDate(date);

		this.loginRegistry.save(model, false);

		assertNotNull(date);
		this.indexService.indexChange(models, IndexAction.INDEX);

		// No-args search
		LoginSearchRequest request = new LoginSearchRequest();
		ModelDocumentSearchResponse<Login> response = this.searchService.search(request);

		Collection<Login> results = response.getModelResults();
		assertTrue(results.containsAll(models));

		// Date Search
		LoginSearch search = request.getSearch();
		DateSearch dateSearch = new DateSearch(date);

		dateSearch.setOperator(ExpressionOperator.LessThan);
		search.setDate(dateSearch);

		response = this.searchService.search(request);
		results = response.getModelResults();
		assertTrue(results.contains(model));
		 */
	}

}
