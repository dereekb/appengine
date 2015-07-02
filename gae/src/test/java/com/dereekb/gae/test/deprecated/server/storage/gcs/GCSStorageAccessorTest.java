package com.thevisitcompany.gae.test.deprecated.server.storage.gcs;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import com.thevisitcompany.gae.server.storage.file.StorableData;
import com.thevisitcompany.gae.server.storage.file.StorageFileContent;
import com.thevisitcompany.gae.server.storage.gcs.GcsStorageAccessor;
import com.thevisitcompany.gae.server.storage.gcs.GcsStorageFileSaveRequest;
import com.thevisitcompany.gae.server.storage.gcs.files.GcsStorageFileData;

@Deprecated
public class GCSStorageAccessorTest extends AbstractGcsTests {

	private static final Integer LOOP_ITERATIONS = 25000;

	@Test
	public void testPathBuilder() {

		final String storableItemName = "item";
		final String storageName = "storage";

		GCSTestStorable storableItem = new GCSTestStorable(storableItemName);
		GCSTestStorage storage = new GCSTestStorage(storageName);

		GCSTestStoragePathResolver resolver = new GCSTestStoragePathResolver();
		StoragePathInfo<GCSTestStorage> pathInfo = new StoragePathInfo<GCSTestStorage>(resolver);

		String storagePath = pathInfo.getStoragePath(storage);
		String path = pathInfo.getStorableItemPath(storage, storableItem);

		assertNotNull(storagePath);
		assertTrue(storagePath.equalsIgnoreCase(storageName));

		assertNotNull(path);
		assertTrue(path.equals(String.format("%s/%s", storagePath,
		        String.format(STORABLE_FORMAT, storableItem.getFilename()))));
	}

	@Test
	public void testStorageAccessorSimpleLoadSave() throws IOException {

		final String bucket = "bucket";
		GcsStorageAccessor accessor = new GcsStorageAccessor(bucket);

		byte[] data = new byte[1024 * 1024 * 5];
		GcsStorageFile storageFile = new GcsStorageFile("path/test", "test");
		StorageFileContent content = new StorageFileContent(storageFile, data, "application/json");

		boolean saveSuccess = accessor.saveFile(content);
		assertTrue(saveSuccess);

		StorableData loadedData = accessor.loadFile(storageFile);
		assertNotNull(loadedData);
	}

	@Test
	public void testStorageAccessorRequestLoadSave() throws IOException {

		final String bucket = "bucket";
		GcsStorageAccessor accessor = new GcsStorageAccessor(bucket);

		byte[] data = new byte[1024 * 1024 * 5];
		GcsStorageFile storageFile = new GcsStorageFile("path/test", "test");
		StorageFileContent content = new StorageFileContent(storageFile, data, "application/json");
		GcsStorageFileSaveRequest request = new GcsStorageFileSaveRequest(bucket, content);

		assertNotNull(request.getGcsFilename());
		assertNotNull(request.getOptions());

		boolean saveSuccess = accessor.saveFile(request);
		assertTrue(saveSuccess);

		GcsStorageFileData loadedData = accessor.loadFile(request);
		assertNotNull(loadedData);
	}

	@Ignore
	@Test
	public void testPathBuilderTimes() {

		final String storableItemName = "Item";
		final String storageName = "Storage";

		GCSTestStorable storableItem = new GCSTestStorable(storableItemName);
		GCSTestStorage storage = new GCSTestStorage(storageName);

		GCSTestStoragePathResolver resolver = new GCSTestStoragePathResolver();
		StoragePathInfo<GCSTestStorage> pathInfo = new StoragePathInfo<GCSTestStorage>(resolver);

		for (int i = 0; i < LOOP_ITERATIONS; i += 1) {
			pathInfo.getStoragePath(storage);
			pathInfo.getStorableItemPath(storage, storableItem);
		}
	}

	@Ignore
	@Test
	public void testPathBuilderTimesNoCache() {

		final String storableItemName = "Item";
		final String storageName = "Storage";

		GCSTestStorable storableItem = new GCSTestStorable(storableItemName);
		GCSTestStorage storage = new GCSTestStorage(storageName);
		GCSTestStoragePathResolver resolver = new GCSTestStoragePathResolver();

		for (int i = 0; i < LOOP_ITERATIONS; i += 1) {
			StoragePathInfo<GCSTestStorage> pathInfo = new StoragePathInfo<GCSTestStorage>(resolver);

			pathInfo.getStoragePath(storage);
			pathInfo.getStorableItemPath(storage, storableItem);
		}
	}

	@Ignore
	@Test
	public void testStorageAccessorSimpleLoadSaveTime() throws IOException {

		final String bucket = "bucket";
		GcsStorageAccessor accessor = new GcsStorageAccessor(bucket);

		byte[] data = new byte[1024 * 1024 * 5];
		GcsStorageFile storageFile = new GcsStorageFile("path/test", "test");
		StorageFileContent content = new StorageFileContent(storageFile, data, "application/json");

		for (int i = 0; i < 50; i += 1) {
			boolean saveSuccess = accessor.saveFile(content);
			assertTrue(saveSuccess);

			StorableData loadedData = accessor.loadFile(storageFile);
			assertNotNull(loadedData);
		}
	}

	@Ignore
	@Test
	public void testStorageAccessorRequestLoadSaveTime() throws IOException {

		final String bucket = "bucket";
		GcsStorageAccessor accessor = new GcsStorageAccessor(bucket);

		byte[] data = new byte[1024 * 1024 * 5];
		GcsStorageFile storageFile = new GcsStorageFile("path/test", "test");
		StorageFileContent content = new StorageFileContent(storageFile, data, "application/json");
		GcsStorageFileSaveRequest request = new GcsStorageFileSaveRequest(bucket, content);

		for (int i = 0; i < 50; i += 1) {
			boolean saveSuccess = accessor.saveFile(request);
			assertTrue(saveSuccess);

			StorableData loadedData = accessor.loadFile(request);
			assertNotNull(loadedData);
		}
	}

}
