package com.thevisitcompany.gae.test.deprecated.models.storage.image;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.thevisitcompany.gae.deprecated.model.mod.search.query.ObjectifyModelQueryRequest;
import com.thevisitcompany.gae.deprecated.model.storage.image.Image;
import com.thevisitcompany.gae.deprecated.model.storage.image.ImageRegistry;
import com.thevisitcompany.gae.deprecated.model.storage.image.search.query.ImageModelQueryRequestBuilder;
import com.thevisitcompany.gae.test.spring.CoreServiceTestingContext;

public class ImageModelQueryTest extends CoreServiceTestingContext {

	private static final ImageRegistry registry = new ImageRegistry();

	@Test
	public void testListQuery() {

		for (Integer i = 0; i < 20; i += 1) {
			Image d1 = new Image();
			d1.setName("i" + i);
			registry.save(d1, false);
		}

		ImageModelQueryRequestBuilder queryRequestBuilder = new ImageModelQueryRequestBuilder();
		ObjectifyModelQueryRequest<Image> ascendingRequest = queryRequestBuilder.listQuery();

		List<Image> ascendingResults = registry.queryEntities(ascendingRequest.getQuery());
		Assert.assertTrue(ascendingResults.size() == 20);
		Assert.assertTrue(ascendingResults.get(0).getName().equals("i0"));
	}

	@Test
	public void testCreatedQuery() {
		Date date = new Date();
		Long time = date.getTime() - 10000000000L;

		for (Integer i = 0; i < 20; i += 1) {
			Image d1 = new Image();
			date = new Date(time += 100000000);
			d1.setCreationDate(date);

			d1.setName("i" + i);
			registry.save(d1, false);
		}

		ImageModelQueryRequestBuilder queryRequestBuilder = new ImageModelQueryRequestBuilder();
		ObjectifyModelQueryRequest<Image> request = queryRequestBuilder.recentlyCreatedSearch();
		request.getQuery().setLimit(5);

		List<Image> results = registry.queryEntities(request.getQuery());
		Assert.assertTrue(results.size() == 5);
		Assert.assertTrue(results.get(0).getName().equals("i19"));
	}

	@Test
	public void testTypeQuery() {
		for (Integer i = 0; i < 20; i += 1) {
			Image d = new Image();
			d.setName("i" + i);
			d.setType((i % 3));
			registry.save(d, false);
		}

		ImageModelQueryRequestBuilder queryRequestBuilder = new ImageModelQueryRequestBuilder();
		ObjectifyModelQueryRequest<Image> ascendingRequest = queryRequestBuilder.typeSearch(2);
		ascendingRequest.getQuery().setLimit(3);

		List<Image> ascendingResults = registry.queryEntities(ascendingRequest.getQuery());
		Assert.assertTrue(ascendingResults.size() == 3);
		Assert.assertTrue(ascendingResults.get(0).getName().equals("i17"));
	}

}
