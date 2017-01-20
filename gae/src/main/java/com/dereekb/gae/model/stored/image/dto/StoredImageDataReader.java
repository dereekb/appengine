package com.dereekb.gae.model.stored.image.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.StringLongModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;

/**
 * {@link DirectionalConverter} for converting a {@link StoredImageData}
 * instance to {@link StoredImage}.
 *
 * @author dereekb
 *
 */
public class StoredImageDataReader extends AbstractDirectionalConverter<StoredImageData, StoredImage> {

	private static final StringModelKeyConverter KEY_CONVERTER = StringLongModelKeyConverterImpl.CONVERTER;

	private static final ObjectifyKeyUtility<GeoPlace> GEO_PLACE_KEY_UTIL = ObjectifyKeyUtility.make(GeoPlace.class);
	private static final ObjectifyKeyUtility<StoredBlob> STORED_BLOB_KEY_UTIL = ObjectifyKeyUtility
	        .make(StoredBlob.class);

	@Override
	public StoredImage convertSingle(StoredImageData input) throws ConversionFailureException {
		StoredImage image = new StoredImage();

		// Identifier
		String stringIdentifier = input.getKey();
		image.setModelKey(KEY_CONVERTER.safeConvert(stringIdentifier));
		image.setSearchIdentifier(input.getSearchIdentifier());

		// Info
		image.setName(input.getName());
		image.setSummary(input.getSummary());
		image.setTags(input.getTags());
		image.setTypeId(input.getType());

		// Links
		image.setStoredBlob(STORED_BLOB_KEY_UTIL.keyFromId(input.getBlob()));
		image.setGeoPlace(GEO_PLACE_KEY_UTIL.keyFromId(input.getGeoPlace()));

		return image;
	}

}
