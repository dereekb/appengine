package com.dereekb.gae.model.stored.image.set.generator;

import java.util.Set;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractModelGenerator;
import com.dereekb.gae.model.extension.generation.impl.keys.LongModelKeyGenerator;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.keys.generator.ObjectifyKeyGenerator;
import com.googlecode.objectify.Key;

/**
 * {@link Generator} for {@link StoredImageSet}.
 *
 * @author dereekb
 *
 */
public class StoredImageSetGenerator extends AbstractModelGenerator<StoredImageSet> {

	private static final ObjectifyKeyGenerator<StoredImage> IMAGE_GENERATOR = ObjectifyKeyGenerator
	        .numberKeyGenerator(StoredImage.class);

	private Integer imagesToGenerate = 5;

	public StoredImageSetGenerator() {
		super(StoredImageSet.class, LongModelKeyGenerator.GENERATOR);
	}

	public Integer getImagesToGenerate() {
		return this.imagesToGenerate;
	}

	public void setImagesToGenerate(Integer imagesToGenerate) {
		this.imagesToGenerate = imagesToGenerate;
	}

	@Override
	protected StoredImageSet generateModel(ModelKey key,
	                                       GeneratorArg arg) {
		StoredImageSet set = new StoredImageSet();

		// Identifier
		set.setModelKey(key);

		// Info
		set.setLabel("Image Set Label");
		set.setDetail("Image Set Detail");
		set.setTags("Tag A, Tag B, Tag C");

		// Icon
		set.setIcon(IMAGE_GENERATOR.generate(arg));

		// Images
		Set<Key<StoredImage>> images = IMAGE_GENERATOR.generateSet(this.imagesToGenerate, arg);
		set.setImages(images);

		return set;
	}

	@Override
	public String toString() {
		return "StoredImageSetGenerator [imagesToGenerate=" + this.imagesToGenerate + "]";
	}

}
