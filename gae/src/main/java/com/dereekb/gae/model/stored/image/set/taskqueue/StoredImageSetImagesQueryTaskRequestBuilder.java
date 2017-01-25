package com.dereekb.gae.model.stored.image.set.taskqueue;

import java.util.List;

import com.dereekb.gae.model.extension.taskqueue.scheduler.builder.impl.AbstractQueryIterateTaskRequestBuilder;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.set.search.query.StoredImageSetQuery;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;

/**
 * {@link AbstractQueryIterateTaskRequestBuilder} implementation used to create
 * a {@link StoredImageQuery} for referenced images.
 * 
 * @author dereekb
 *
 */
public class StoredImageSetImagesQueryTaskRequestBuilder extends AbstractQueryIterateTaskRequestBuilder<StoredImage> {

	public StoredImageSetImagesQueryTaskRequestBuilder(TaskRequest baseRequest) throws IllegalArgumentException {
		super(baseRequest);
	}

	// MARK: AbstractQueryIterateTaskRequestBuilder
	@Override
	public StoredImageSetQuery getParametersForPartition(List<ModelKey> partition) {

		StoredImageSetQuery query = new StoredImageSetQuery();
		query.setImages(partition);

		return query;
	}

}
