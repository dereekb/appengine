package com.dereekb.gae.test.applications.api.model.geoplace;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.search.document.index.IndexAction;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.StagedDocumentBuilder;
import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.model.extension.search.document.search.service.model.ModelDocumentSearchResponse;
import com.dereekb.gae.model.extension.search.document.search.service.model.ModelDocumentSearchService;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.geo.place.search.document.query.GeoPlaceSearchRequest;
import com.dereekb.gae.test.applications.api.model.tests.extension.ModelSearchDocumentTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class GeoPlaceSearchDocumentTest extends ModelSearchDocumentTest<GeoPlace> {

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
	public void testSearchService() {
		List<GeoPlace> models = this.getGenerator().generate(10);
		GeoPlace model = models.get(0);
		Date date = model.getDate();

		Assert.assertNotNull(date);

		this.indexService.indexChange(models, IndexAction.INDEX);

		GeoPlaceSearchRequest request = new GeoPlaceSearchRequest();
		ModelDocumentSearchResponse<GeoPlace> response = this.searchService.search(request);

		Collection<GeoPlace> results = response.getModelSearchResults();
		Assert.assertTrue(results.containsAll(models));

	}


}
