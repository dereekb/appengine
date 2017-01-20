package com.dereekb.gae.model.stored.image.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;

/**
 * {@link DirectionalConverter} for converting a {@link StoredImage} to
 * {@link StoredImageData}.
 *
 * @author dereekb
 */
public class StoredImageDataBuilder extends AbstractDirectionalConverter<StoredImage, StoredImageData> {

	@Override
	public StoredImageData convertSingle(StoredImage input) throws ConversionFailureException {
		StoredImageData data = new StoredImageData();

		// Identifier
		data.setKey(input.getModelKey());

		// Info
		data.setName(input.getName());
		data.setSummary(input.getSummary());
		data.setTags(input.getTags());
		data.setType(input.getTypeId());

		// Blob
		data.setBlob(ObjectifyKeyUtility.idFromKey(input.getStoredBlob()));

		// Geo Place
		data.setGeoPlace(ObjectifyKeyUtility.idFromKey(input.getGeoPlace()));

		return data;
	}

}
