package com.dereekb.gae.model.stored.image.dto;

import com.dereekb.gae.model.deprecated.stored.blob.StoredBlob;
import com.dereekb.gae.model.deprecated.stored.image.StoredImage;
import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.deprecated.search.document.dto.SearchableModelDataReader;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;

/**
 * {@link DirectionalConverter} for converting a {@link StoredImageData}
 * instance to {@link StoredImage}.
 *
 * @author dereekb
 *
 */
public class StoredImageDataReader extends SearchableModelDataReader<StoredImage, StoredImageData> {

	public StoredImageDataReader() {
		super(StoredImage.class);
	}

	private static final ObjectifyKeyUtility<StoredBlob> STORED_BLOB_KEY_UTIL = ObjectifyKeyUtility
	        .make(StoredBlob.class);

	@Override
	public StoredImage convertSingle(StoredImageData input) throws ConversionFailureException {
		StoredImage image = super.convertSingle(input);

		// Data
		image.setName(input.getName());
		image.setSummary(input.getSummary());
		image.setTags(input.getTags());
		image.setTypeId(input.getType());

		// Links
		image.setStoredBlob(STORED_BLOB_KEY_UTIL.keyFromId(input.getBlob()));

		return image;
	}

}
