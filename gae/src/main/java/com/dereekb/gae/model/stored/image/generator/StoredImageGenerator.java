package com.dereekb.gae.model.stored.image.generator;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractModelGenerator;
import com.dereekb.gae.model.extension.generation.impl.keys.LongModelKeyGenerator;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.StoredImageType;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.misc.random.PositiveLongGenerator;
import com.dereekb.gae.utilities.misc.random.StringLongGenerator;
import com.googlecode.objectify.Key;

/**
 * {@link Generator} for {@link StoredImage}.
 *
 * @author dereekb
 */
public final class StoredImageGenerator extends AbstractModelGenerator<StoredImage> {

	public StoredImageGenerator() {
		super(LongModelKeyGenerator.GENERATOR);
	}

	@Override
	public StoredImage generateModel(ModelKey key,
	                                 GeneratorArg arg) {
		StoredImage image = new StoredImage();
		image.setIdentifier(PositiveLongGenerator.GENERATOR.generate(arg));

		Key<StoredBlob> storedBlob = Key.create(StoredBlob.class, PositiveLongGenerator.GENERATOR.generate(arg));
		image.setBlob(storedBlob);

		image.setName("Image");
		image.setSummary("This is a generated image.");
		image.setSearchIdentifier(StringLongGenerator.GENERATOR.generate(arg));

		image.setTags("Tag A, Tag B, Tag C");
		image.setType(StoredImageType.IMAGE);

		return image;
	}

}
