package com.dereekb.gae.model.stored.image.generator;

import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.StoredImageType;
import com.googlecode.objectify.Key;

/**
 * {@link Generator} for {@link StoredImage}.
 *
 * @author dereekb
 */
public final class StoredImageGenerator extends AbstractGenerator<StoredImage> {

	@Override
	public StoredImage generate() {
		StoredImage image = new StoredImage();

		image.setIdentifier(this.randomPositiveLong());

		Key<StoredBlob> storedBlob = Key.create(StoredBlob.class, this.randomPositiveLong());
		image.setBlob(storedBlob);

		image.setName("Image");
		image.setSummary("This is a generated image.");
		image.setSearchIdentifier("THE-SEARCH-IDENTIFIER");

		image.setTags("Tag A, Tag B, Tag C");
		image.setType(StoredImageType.IMAGE);

		return image;
	}

}
