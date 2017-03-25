package com.dereekb.gae.test.applications.api.api.geoplace;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.geo.place.search.query.GeoPlaceQueryInitializer.ObjectifyGeoPlaceQuery;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.applications.api.api.tests.ApiSearchTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;

public class GeoPlaceApiSearchTest extends ApiSearchTest<GeoPlace> {

	@Autowired
	@Qualifier("geoPlaceRegistry")
	private ObjectifyRegistry<GeoPlace> registry;

	@Override
	@Autowired
	@Qualifier("geoPlaceSearchType")
	public void setSearchType(String type) {
		super.setSearchType(type);
	}

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

	@Test
	public void testGeoPlaceParentQuerying() {
		int count = 10;

		GeoPlace parent = this.generator.generate();
		List<GeoPlace> children = this.generator.generate(count);
		// List<GeoPlace> notChildren = this.generator.generate(count);

		for (GeoPlace child : children) {
			child.setParent(parent.getObjectifyKey());
		}

		this.registry.update(children, false);

		Integer limit = null;
		ObjectifyGeoPlaceQuery query = new ObjectifyGeoPlaceQuery();
		query.setParent(parent.getObjectifyKey());
		Map<String, String> parameters = query.getParameters();

		ApiResponse response = this.controller.querySingle(this.searchType, parameters, limit, true);
		ApiResponseData data = response.getResponsePrimaryData();

		Assert.assertNotNull(response);
		Assert.assertNotNull(data);

		Object responseData = data.getResponseData();

		@SuppressWarnings("unchecked")
		List<String> responseKeys = (List<String>) responseData;
		List<String> expectedKeys = ModelKey.readStringKeys(children);

		// Check only the children are returned.
		Assert.assertTrue(expectedKeys.containsAll(responseKeys));
		Assert.assertTrue(responseKeys.size() == count);
	}

}
