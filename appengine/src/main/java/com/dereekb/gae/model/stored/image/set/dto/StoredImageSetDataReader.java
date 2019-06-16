package com.dereekb.gae.model.stored.image.set.dto;

import java.util.Set;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.server.datastore.models.dto.OwnedDatabaseModelDataReader;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.googlecode.objectify.Key;

/**
 * {@link DirectionalConverter} for converting a {@link StoredImageSetData}
 * instance to {@link StoredImageSet}.
 *
 * @author dereekb
 *
 */
public class StoredImageSetDataReader extends OwnedDatabaseModelDataReader<StoredImageSet, StoredImageSetData> {

	private static final ObjectifyKeyUtility<StoredImage> IMAGE_KEY_UTIL = ObjectifyKeyUtility.make(StoredImage.class);

	public StoredImageSetDataReader() {
		super(StoredImageSet.class);
	}

	@Override
	public StoredImageSet convertSingle(StoredImageSetData input) throws ConversionFailureException {
		StoredImageSet set = super.convertSingle(input);

		// Data
		set.setLabel(input.getLabel());
		set.setDetail(input.getDetail());
		set.setTags(input.getTags());

		// Links
		Key<StoredImage> icon = IMAGE_KEY_UTIL.keyFromId(input.getIcon());
		set.setIcon(icon);

		Set<Key<StoredImage>> images = IMAGE_KEY_UTIL.setFromIds(input.getImages());
		set.setImages(images);

		return set;
	}

}
