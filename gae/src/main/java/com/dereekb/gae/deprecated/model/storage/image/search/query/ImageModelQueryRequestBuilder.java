package com.thevisitcompany.gae.deprecated.model.storage.image.search.query;

import com.thevisitcompany.gae.deprecated.model.mod.search.query.ObjectifyModelQueryRequest;
import com.thevisitcompany.gae.deprecated.model.storage.image.Image;
import com.thevisitcompany.gae.deprecated.model.storage.support.search.query.StorageModelQueryRequestBuilder;
import com.thevisitcompany.gae.server.datastore.objectify.query.ObjectifyQueryFilterOperator;
import com.thevisitcompany.gae.server.datastore.objectify.query.ObjectifyQueryOrdering.QueryOrdering;

public class ImageModelQueryRequestBuilder extends StorageModelQueryRequestBuilder<Image> {

	public ImageModelQueryRequestBuilder() {
		super(Image.class);
	}

	public ObjectifyModelQueryRequest<Image> typeSearch(Integer type) {
		return super.recentFieldSearch("type", ObjectifyQueryFilterOperator.Equal, type, QueryOrdering.Ascending);
	}

}
