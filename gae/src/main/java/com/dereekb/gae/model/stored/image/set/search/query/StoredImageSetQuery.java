package com.dereekb.gae.model.stored.image.set.search.query;

import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableEncodedQueryParameters;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeySetQueryFieldParameterBuilder;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeySetQueryFieldParameterBuilder.ModelKeySetQueryFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.utility.ParameterUtility;

/**
 * Utility used for querying a {@link LoginPointer}.
 * 
 * @author dereekb
 *
 */
public class StoredImageSetQuery
        implements ConfigurableEncodedQueryParameters {

	public static final String IMAGES_FIELD = "images";

	private static final ModelKeySetQueryFieldParameterBuilder IMAGE_FIELD_BUILDER = ModelKeySetQueryFieldParameterBuilder
	        .number();

	private ModelKeySetQueryFieldParameter images;

	public ModelKeySetQueryFieldParameter getImage() {
		return this.images;
	}

	public void setImage(ModelKeySetQueryFieldParameter image) {
		this.images = image;
	}

	public void setImage(ModelKey image) {
		this.images = IMAGE_FIELD_BUILDER.make(IMAGES_FIELD, image);
	}

	public void setImage(String image) {
		this.images = IMAGE_FIELD_BUILDER.makeModelKeyParameter(IMAGES_FIELD, image);
	}

	// MARK: ConfigurableQueryParameters
	@Override
	public Map<String, String> getParameters() {
		Map<String, String> parameters = new HashMap<String, String>();
		ParameterUtility.put(parameters, this.images);
		return parameters;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {
		this.setImage(parameters.get(IMAGES_FIELD));
	}

}
