package com.dereekb.gae.model.stored.image.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.server.datastore.models.dto.OwnedDatabaseModelDataBuilder;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;

/**
 * {@link DirectionalConverter} for converting a {@link StoredImage} to
 * {@link StoredImageData}.
 *
 * @author dereekb
 */
public class StoredImageDataBuilder extends OwnedDatabaseModelDataBuilder<StoredImage, StoredImageData> {

	public StoredImageDataBuilder() throws IllegalArgumentException {
		super(StoredImageData.class);
	}

	@Override
	public StoredImageData convertSingle(StoredImage input) throws ConversionFailureException {
		StoredImageData data = super.convertSingle(input);

		// Data
		data.setName(input.getName());
		data.setSummary(input.getSummary());
		data.setTags(input.getTags());
		data.setType(input.getTypeId());

		// Links
		data.setBlob(ObjectifyKeyUtility.idFromKey(input.getStoredBlob()));

		return data;
	}

}
