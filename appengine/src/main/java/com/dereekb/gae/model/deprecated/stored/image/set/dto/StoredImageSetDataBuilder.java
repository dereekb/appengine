package com.dereekb.gae.model.stored.image.set.dto;

import com.dereekb.gae.model.deprecated.stored.image.set.StoredImageSet;
import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.deprecated.search.document.dto.SearchableModelDataBuilder;
import com.dereekb.gae.server.datastore.objectify.helpers.ObjectifyUtility;

/**
 * {@link DirectionalConverter} for converting a {@link StoredImageSet} instance
 * to {@link StoredImageSetData}.
 *
 * @author dereekb
 *
 */
public class StoredImageSetDataBuilder extends SearchableModelDataBuilder<StoredImageSet, StoredImageSetData> {

	public StoredImageSetDataBuilder() throws IllegalArgumentException {
		super(StoredImageSetData.class);
	}

	@Override
	public StoredImageSetData convertSingle(StoredImageSet input) throws ConversionFailureException {
		StoredImageSetData data = super.convertSingle(input);

		// Data
		data.setLabel(input.getLabel());
		data.setDetail(input.getDetail());
		data.setTags(input.getTags());

		// Links
		data.setIcon(ObjectifyUtility.readKeyIdentifier(input.getIcon()));
		data.setImages(ObjectifyUtility.readKeyIdentifiers(input.getImages()));

		return data;
	}

}
