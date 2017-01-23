package com.dereekb.gae.model.stored.image.set.search.query;

import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.objectify.query.builder.AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl;
import com.dereekb.gae.server.datastore.objectify.query.builder.ConfigurableObjectifyQueryRequestConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyKeySetFieldParameterBuilder;
import com.googlecode.objectify.Key;

/**
 * {@link ObjectifyQueryRequestLimitedBuilderInitializer} implementation for a
 * {@link StoredImageSetQuery}.
 *
 * @author dereekb
 *
 */
public class StoredImageSetQueryInitializer extends AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl {

	private static final ObjectifyKeySetFieldParameterBuilder<StoredImage> STORED_IMAGE_BUILDER = ObjectifyKeySetFieldParameterBuilder
	        .make(ModelKeyType.NUMBER, StoredImage.class);

	@Override
	protected ConfigurableObjectifyQueryRequestConfigurer makeConfigurer() {
		return new ObjectifyStoredImageSetQuery();
	}

	public static class ObjectifyStoredImageSetQuery extends StoredImageSetQuery
	        implements ConfigurableObjectifyQueryRequestConfigurer {

		public void setImage(StoredImage image) throws NullPointerException {
			this.setImage(image.getObjectifyKey());
		}

		public void setImage(Key<StoredImage> image) {
			this.setImage(STORED_IMAGE_BUILDER.getUtil().toModelKey(image));
		}

		// MARK: ConfigurableObjectifyQueryRequestConfigurer
		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {
			STORED_IMAGE_BUILDER.configure(request, this.getImage());
		}

	}

}
