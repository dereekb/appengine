package com.dereekb.gae.model.stored.image.set.search.query;

import java.util.Map;

import com.dereekb.gae.model.deprecated.stored.image.StoredImage;
import com.dereekb.gae.model.deprecated.stored.image.set.search.query.StoredImageSetQueryInitializer.ObjectifyStoredImageSetQuery;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.objectify.query.builder.AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl;
import com.dereekb.gae.server.datastore.objectify.query.builder.ConfigurableObjectifyQueryRequestConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryFactory;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyAbstractQueryFieldParameter;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyKeySetFieldParameterBuilder;
import com.googlecode.objectify.Key;

/**
 * {@link ObjectifyQueryRequestLimitedBuilderInitializer} implementation for a
 * {@link StoredImageSetQuery}.
 *
 * @author dereekb
 *
 */
public class StoredImageSetQueryInitializer extends AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl
        implements ObjectifyQueryFactory<ObjectifyStoredImageSetQuery> {

	private static final ObjectifyKeySetFieldParameterBuilder<StoredImage> STORED_IMAGE_BUILDER = ObjectifyKeySetFieldParameterBuilder
	        .make(ModelKeyType.NUMBER, StoredImage.class);

	@Override
	protected ConfigurableObjectifyQueryRequestConfigurer makeConfigurer() {
		return new ObjectifyStoredImageSetQuery();
	}

	// MARK: ObjectifyQueryFactory
	@Override
	public ObjectifyStoredImageSetQuery makeQuery(Map<String, String> parameters) throws IllegalArgumentException {
		return new ObjectifyStoredImageSetQuery(parameters);
	}

	public static class ObjectifyStoredImageSetQuery extends StoredImageSetQuery
	        implements ConfigurableObjectifyQueryRequestConfigurer {

		public ObjectifyStoredImageSetQuery() {
			super();
		}

		public ObjectifyStoredImageSetQuery(Map<String, String> parameters) throws IllegalArgumentException {
			super(parameters);
		}

		public void setImages(StoredImage image) throws NullPointerException {
			this.setImages(image.getModelKey());
		}

		public void setImages(Key<StoredImage> image) {
			this.setImages(STORED_IMAGE_BUILDER.getUtil().toModelKey(image));
		}

		// MARK: ConfigurableObjectifyQueryRequestConfigurer
		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {
			STORED_IMAGE_BUILDER.configure(request, this.getImages());
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getOwnerId());
		}

	}

}
