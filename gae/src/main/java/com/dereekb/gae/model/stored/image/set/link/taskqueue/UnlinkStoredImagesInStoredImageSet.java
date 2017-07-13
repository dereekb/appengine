package com.dereekb.gae.model.stored.image.set.link.taskqueue;

import com.dereekb.gae.model.extension.links.task.impl.ModelKeySetQueryFieldLinkChanger;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.model.stored.image.set.link.StoredImageSetLinkSystemBuilderEntry;
import com.dereekb.gae.model.stored.image.set.search.query.StoredImageSetQuery;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * {@link ModelKeySetQueryFieldLinkChanger} configuration for unlinking
 * {@link StoredImage} values referenced by {@link StoredImageSet}.
 * 
 * @author dereekb
 *
 */
public class UnlinkStoredImagesInStoredImageSet extends ModelKeySetQueryFieldLinkChanger {

	private static final String LINK_NAME = StoredImageSetLinkSystemBuilderEntry.STORED_IMAGE_SET_IMAGES_LINK_NAME;
	private static final String QUERY_PARAMETER = StoredImageSetQuery.IMAGES_FIELD;
	private static final ModelKeyType KEY_TYPE = ModelKeyType.NUMBER;

	public UnlinkStoredImagesInStoredImageSet() {
		super(LINK_NAME, QUERY_PARAMETER, KEY_TYPE);
	}

}
