package com.dereekb.gae.test.applications.api.model.geoplace;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.search.document.index.IndexAction;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.StagedDocumentBuilder;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.util.ModelDocumentBuilderUtility;
import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.model.extension.search.document.search.model.DateSearch;
import com.dereekb.gae.model.extension.search.document.search.service.model.ModelDocumentSearchResponse;
import com.dereekb.gae.model.extension.search.document.search.service.model.ModelDocumentSearchService;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.geo.place.search.document.index.GeoPlaceDocumentBuilderStep;
import com.dereekb.gae.model.geo.place.search.document.query.GeoPlaceSearchBuilder.GeoPlaceSearch;
import com.dereekb.gae.model.geo.place.search.document.query.GeoPlaceSearchRequest;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.test.applications.api.model.tests.extension.ModelSearchDocumentTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.google.appengine.api.search.Document;

public class GeoPlaceSearchDocumentTest extends ModelSearchDocumentTest<GeoPlace> {

	@Autowired
	@Qualifier("geoPlaceRegistry")
	private ObjectifyRegistry<GeoPlace> geoPlaceRegistry;

	@Override
	@Autowired
	@Qualifier("geoPlaceTestModelGenerator")
	public void setGenerator(TestModelGenerator<GeoPlace> generator) {
		super.setGenerator(generator);
	}

	@Override
	@Autowired
	@Qualifier("geoPlaceDocumentIndexService")
	public void setIndexService(DocumentIndexService<GeoPlace> service) {
		super.setIndexService(service);
	}

	@Override
	@Autowired
	@Qualifier("geoPlaceSearchDocumentBuilder")
	public void setBuilder(StagedDocumentBuilder<GeoPlace> builder) {
		super.setBuilder(builder);
	}

	@Autowired
	@Qualifier("geoPlaceSearchService")
	private ModelDocumentSearchService<GeoPlace, GeoPlaceSearchRequest> searchService;

	@Test
	public void testNoArgsSearch() {
		List<GeoPlace> models = this.getGenerator().generate(10);
		this.indexService.indexChange(models, IndexAction.INDEX);

		// No-args search
		GeoPlaceSearchRequest request = new GeoPlaceSearchRequest();
		ModelDocumentSearchResponse<GeoPlace> response = this.searchService.search(request);

		Collection<GeoPlace> results = response.getModelResults();
		Assert.assertTrue(results.containsAll(models));
	}

	@Test
	public void testDateSearchService() {
		List<GeoPlace> models = this.getGenerator().generate(1);

		// Index Models
		GeoPlace model = models.get(0);

		Date date = new Date(10); // Set for later query test.
		model.setDate(date);

		this.geoPlaceRegistry.update(model);

		Assert.assertNotNull(date);
		this.indexService.indexChange(models, IndexAction.INDEX);

		// Date Search
		GeoPlaceSearchRequest request = new GeoPlaceSearchRequest();
		GeoPlaceSearch search = request.getSearch();

		DateSearch dateSearch = new DateSearch(date);
		dateSearch.setOperator(ExpressionOperator.EQUAL);
		search.setDate(dateSearch);

		ModelDocumentSearchResponse<GeoPlace> response = this.searchService.search(request);
		Collection<ModelKey> resultKeys = response.getKeyResults();
		Assert.assertFalse(resultKeys.isEmpty());

		Collection<GeoPlace> results = response.getModelResults();
		Assert.assertTrue(results.contains(model));
	}

	@Test
	public void testSearchIndexFields() {
		GeoPlace model = this.getGenerator().generate();
		Document document = this.builder.buildSearchDocument(model);

		Set<String> names = document.getFieldNames();
		Assert.assertTrue(names.contains(ModelDocumentBuilderUtility.DATE_FIELD));
		Assert.assertTrue(names.contains(GeoPlaceDocumentBuilderStep.REGION_FIELD));
	}

}
