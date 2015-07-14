package com.dereekb.gae.model.stored.image.generator;

import java.util.Set;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractModelGenerator;
import com.dereekb.gae.model.extension.generation.impl.keys.LongModelKeyGenerator;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.StoredImageType;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.keys.generator.ObjectifyKeyGenerator;
import com.googlecode.objectify.Key;

/**
 * {@link Generator} for {@link StoredImage}.
 *
 * @author dereekb
 */
public final class StoredImageGenerator extends AbstractModelGenerator<StoredImage> {

	private static final ObjectifyKeyGenerator<StoredBlob> STORED_BLOB_GENERATOR = ObjectifyKeyGenerator
	        .numberKeyGenerator(StoredBlob.class);
	private static final ObjectifyKeyGenerator<GeoPlace> GEO_PLACE_GENERATOR = ObjectifyKeyGenerator
	        .numberKeyGenerator(GeoPlace.class);
	private static final ObjectifyKeyGenerator<StoredImageSet> IMAGE_SET_GENERATOR = ObjectifyKeyGenerator
	        .numberKeyGenerator(StoredImageSet.class);

	public int setsToGenerate = 1;

	public StoredImageGenerator() {
		super(LongModelKeyGenerator.GENERATOR);
	}

	public int getSetsToGenerate() {
		return this.setsToGenerate;
	}

	public void setSetsToGenerate(int setsToGenerate) {
		this.setsToGenerate = setsToGenerate;
	}

	@Override
	public StoredImage generateModel(ModelKey key,
	                                 GeneratorArg arg) {
		StoredImage image = new StoredImage();

		// Identifier
		image.setModelKey(key);

		// Info
		image.setName("Image");
		image.setSummary("This is a generated image.");

		image.setTags("Tag A, Tag B, Tag C");
		image.setType(StoredImageType.IMAGE);

		// Stored Blob
		Key<StoredBlob> storedBlob = STORED_BLOB_GENERATOR.generate(arg);
		image.setBlob(storedBlob);

		// Geo Place
		Key<GeoPlace> geoPlace = GEO_PLACE_GENERATOR.generate(arg);
		image.setGeoPlace(geoPlace);

		// Image Sets
		Set<Key<StoredImageSet>> imageSets = IMAGE_SET_GENERATOR.generateSet(this.setsToGenerate, arg);
		image.setImageSets(imageSets);

		return image;
	}

}
