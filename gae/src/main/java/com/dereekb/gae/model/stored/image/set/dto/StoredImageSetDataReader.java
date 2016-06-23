package com.dereekb.gae.model.stored.image.set.dto;

import java.util.Set;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.StringLongModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.googlecode.objectify.Key;


/**
 * {@link DirectionalConverter} for converting a {@link StoredImageSetData}
 * instance to {@link StoredImageSet}.
 *
 * @author dereekb
 *
 */
public class StoredImageSetDataReader extends AbstractDirectionalConverter<StoredImageSetData, StoredImageSet> {

	private static final StringModelKeyConverter KEY_CONVERTER = StringLongModelKeyConverterImpl.CONVERTER;

	private static final ObjectifyKeyUtility<StoredImage> IMAGE_KEY_UTIL = ObjectifyKeyUtility.make(StoredImage.class);

	@Override
	public StoredImageSet convertSingle(StoredImageSetData input) throws ConversionFailureException {
		StoredImageSet set = new StoredImageSet();

		// Identifier
		String stringIdentifier = input.getKey();
		set.setModelKey(KEY_CONVERTER.safeConvert(stringIdentifier));
		set.setSearchIdentifier(input.getSearchIdentifier());

		// Info
		set.setLabel(input.getLabel());
		set.setDetail(input.getDetail());
		set.setTags(input.getTags());

		// Images
		Key<StoredImage> icon = IMAGE_KEY_UTIL.keyFromId(input.getIcon());
		set.setIcon(icon);

		Set<Key<StoredImage>> images = IMAGE_KEY_UTIL.setFromIds(input.getImages());
		set.setImages(images);

		return set;
	}

}
