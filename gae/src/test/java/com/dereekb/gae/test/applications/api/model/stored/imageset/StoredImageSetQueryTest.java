package com.dereekb.gae.test.applications.api.model.stored.imageset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.model.stored.image.set.search.query.StoredImageSetQueryInitializer.ObjectifyStoredImageSetQuery;
import com.dereekb.gae.model.stored.image.set.taskqueue.StoredImageSetImagesQueryTaskRequestBuilder;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.taskqueue.scheduler.MutableTaskRequest;
import com.dereekb.gae.test.applications.api.model.tests.extension.ModelQueryTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.googlecode.objectify.Key;

public class StoredImageSetQueryTest extends ModelQueryTest<StoredImageSet> {

	@Autowired
	@Qualifier("loginTestModelGenerator")
	private TestModelGenerator<Login> testLoginGenerator;

	@Override
	@Autowired
	@Qualifier("storedImageSetRegistry")
	public void setRegistry(ObjectifyRegistry<StoredImageSet> registry) {
		super.setRegistry(registry);
	}

	@Autowired
	@Qualifier("storedImageTestModelGenerator")
	private TestModelGenerator<StoredImage> storedImageGenerator;

	@Autowired
	@Qualifier("scheduleRemoveImagesFromStoredImageSetsTaskBuilder")
	private StoredImageSetImagesQueryTaskRequestBuilder imageQueryTaskRequestBuilder;

	@Override
	@Autowired
	@Qualifier("storedImageSetTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<StoredImageSet> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

	@Override
	@Autowired
	@Qualifier("storedImageSetType")
	public void setQueryType(String queryType) {
		super.setQueryType(queryType);
	}

	@Test
	public void queryImageTest() throws Exception {

		Integer count = 10;

		StoredImage imageA = this.storedImageGenerator.generate();
		StoredImage imageB = this.storedImageGenerator.generate();
		StoredImage imageC = this.storedImageGenerator.generate();

		Key<StoredImage> imageAKey = imageA.getObjectifyKey();
		Key<StoredImage> imageBKey = imageB.getObjectifyKey();
		Key<StoredImage> imageCKey = imageC.getObjectifyKey();

		List<StoredImageSet> imageSets = this.getModelGenerator().generate(count);
		List<StoredImageSet> imageASets = new ArrayList<>();
		List<StoredImageSet> imageBSets = new ArrayList<>();

		// Clear Images
		for (int i = 0; i < count; i += 1) {
			StoredImageSet set = imageSets.get(i);
			set.setImages(null);

			Set<Key<StoredImage>> imageSet = new HashSet<>();

			imageSet.add((i % 2 == 1) ? imageAKey : imageBKey);
			imageSet.add(imageCKey);

			((i % 2 == 1) ? imageASets : imageBSets).add(set);

			set.setImages(imageSet);
		}

		this.getRegistry().update(imageSets);

		ObjectifyStoredImageSetQuery queryConfig = new ObjectifyStoredImageSetQuery();
		queryConfig.setImages(imageA);

		// No limit, should only return the 5.
		ModelQueryUnitTest<ObjectifyStoredImageSetQuery> test = new ModelQueryUnitTest<ObjectifyStoredImageSetQuery>(
		        queryConfig);
		ModelQueryUnitTest<ObjectifyStoredImageSetQuery>.Results results = test.performQuery();

		results.assertResultsMatch();
		List<StoredImageSet> resultModels = results.getQuery().queryModels();
		Assert.assertTrue(imageASets.containsAll(resultModels));
		Assert.assertTrue(imageASets.size() == resultModels.size());
	}

	@Test
	public void queryImageAndTest() throws Exception {

		Integer count = 10;

		StoredImage imageA = this.storedImageGenerator.generate();
		StoredImage imageB = this.storedImageGenerator.generate();
		StoredImage imageC = this.storedImageGenerator.generate();

		Key<StoredImage> imageAKey = imageA.getObjectifyKey();
		Key<StoredImage> imageBKey = imageB.getObjectifyKey();
		Key<StoredImage> imageCKey = imageC.getObjectifyKey();

		List<StoredImageSet> imageSets = this.getModelGenerator().generate(count);
		List<StoredImageSet> imageASets = new ArrayList<>();
		List<StoredImageSet> imageBSets = new ArrayList<>();

		// Clear Images
		for (int i = 0; i < count; i += 1) {
			StoredImageSet set = imageSets.get(i);
			set.setImages(null);

			Set<Key<StoredImage>> imageSet = new HashSet<>();

			imageSet.add((i % 2 == 1) ? imageAKey : imageBKey);
			imageSet.add(imageCKey);

			((i % 2 == 1) ? imageASets : imageBSets).add(set);

			set.setImages(imageSet);
		}

		this.getRegistry().update(imageSets);

		ObjectifyStoredImageSetQuery queryConfig = new ObjectifyStoredImageSetQuery();

		List<StoredImage> images = new ArrayList<StoredImage>();
		images.add(imageA);
		images.add(imageC);

		queryConfig.setImages(ModelKey.readModelKeys(images));

		// No limit, should only return the 5.
		ModelQueryUnitTest<ObjectifyStoredImageSetQuery> test = new ModelQueryUnitTest<ObjectifyStoredImageSetQuery>(
		        queryConfig);
		ModelQueryUnitTest<ObjectifyStoredImageSetQuery>.Results results = test.performQuery();

		results.assertResultsMatch();
		List<StoredImageSet> resultModels = results.getQuery().queryModels();

		// Results should return everything, since they all reference image C.
		Assert.assertTrue(imageSets.containsAll(resultModels));
		Assert.assertTrue(imageSets.size() == resultModels.size());

		Assert.assertTrue(resultModels.containsAll(imageASets));
		Assert.assertTrue(resultModels.containsAll(imageBSets));

		Assert.assertFalse(imageASets.containsAll(resultModels));
		Assert.assertFalse(imageBSets.containsAll(resultModels));
	}

	@Test
	public void imagesQueryTaskRequestBuilderTest() {
		List<StoredImage> images = this.storedImageGenerator.generate(20);
		List<MutableTaskRequest> requests = this.imageQueryTaskRequestBuilder.buildRequests(images);

		Assert.assertNotNull(requests);
	}

}
