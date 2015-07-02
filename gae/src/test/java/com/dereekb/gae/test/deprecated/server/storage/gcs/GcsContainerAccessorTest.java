package com.thevisitcompany.gae.test.deprecated.server.storage.gcs;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

@Deprecated
public class GcsContainerAccessorTest extends AbstractGcsTests {

	final String storableItemName = "item";
	final String storageName = "storage";

	@Test
	public void testAccessingAllStorages() {
		StorageHolderReader reader = new StorageHolderReader();
		GCSTestStorage storage = new GCSTestStorage(this.storageName);

		String[] filenamesArray = { "File A", "File B" };
		Set<String> filenames = new HashSet<String>(Arrays.asList(filenamesArray));
		storage.getFilesA().setFilenames(filenames);

		Collection<? extends StorageFileReferenceCollection> collections = reader.getAllStorageCollections(storage);
		assertTrue(collections.size() == 2);

		System.out.println(collections);
	}

	@Test
	public void testAccessingTargetStorages() {
		StorageHolderReader reader = new StorageHolderReader();
		GCSTestStorage storage = new GCSTestStorage(this.storageName);

		StorageFileReferenceCollection collection = reader.getCollectionWithName(storage, STORAGE_NAME);
		assertNotNull(collection);

		try {
			reader.getCollectionWithName(storage, "doesnt exist");
			fail();
		} catch (RuntimeException e) {

		}
	}

	private static final Integer iterations = 1000000;

	@Ignore
	@Test
	public void testAccessingAllStoragesTime() {
		StorageHolderReader reader = new StorageHolderReader();
		GCSTestStorage storage = new GCSTestStorage(this.storageName);

		String[] filenamesArray = { "File A", "File B" };
		Set<String> filenames = new HashSet<String>(Arrays.asList(filenamesArray));
		storage.getFilesA().setFilenames(filenames);

		for (int i = 0; i < iterations; i += 1) {
			reader.getAllStorageCollections(storage);
		}
	}
}
