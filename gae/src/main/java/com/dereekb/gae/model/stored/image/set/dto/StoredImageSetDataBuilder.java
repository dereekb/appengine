package com.dereekb.gae.model.stored.image.set.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.server.datastore.objectify.helpers.ObjectifyUtility;


/**
 * {@link DirectionalConverter} for converting a {@link StoredImageSet} instance
 * to {@link StoredImageSetData}.
 *
 * @author dereekb
 *
 */
public class StoredImageSetDataBuilder extends AbstractDirectionalConverter<StoredImageSet, StoredImageSetData> {

	@Override
	public StoredImageSetData convertSingle(StoredImageSet input) throws ConversionFailureException {
		StoredImageSetData data = new StoredImageSetData();

		// Identifier
		data.setModelKey(input.getModelKey());
		data.setSearchIdentifier(input.getSearchIdentifier());

		// Info
		data.setLabel(input.getLabel());
		data.setDetail(input.getDetail());
		data.setTags(input.getTags());

		// Images
		data.setIcon(ObjectifyUtility.readKeyIdentifier(input.getIcon()));
		data.setImages(ObjectifyUtility.readKeyIdentifiers(input.getImages()));

		return data;
	}

}
