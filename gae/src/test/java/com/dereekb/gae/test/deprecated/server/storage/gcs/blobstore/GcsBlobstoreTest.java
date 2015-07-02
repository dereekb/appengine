package com.thevisitcompany.gae.test.deprecated.server.storage.gcs.blobstore;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.appengine.api.blobstore.BlobKey;
import com.thevisitcompany.gae.server.storage.file.StorageFile;
import com.thevisitcompany.gae.test.deprecated.server.storage.gcs.AbstractGcsTests;

@Deprecated
public class GcsBlobstoreTest extends AbstractGcsTests {

	@Test
	public void blobstoreKeyFactoryTest() {

	}

	@Test
	public void blobstoreStoragePathInfoTest() {

		GCSTestStorage storage = new GCSTestStorage();

		String[] filenamesArray = { "File A", "File B" };
		Set<String> filenames = new HashSet<String>(Arrays.asList(filenamesArray));
		StorageFileReferenceSet referenceSet = storage.getFilesA();
		referenceSet.setFilenames(filenames);

		GCSTestStoragePathResolver pathResolver = new GCSTestStoragePathResolver();
		BlobstoreStoragePathInfo<GCSTestStorage> storagePathInfo = new BlobstoreStoragePathInfo<GCSTestStorage>();
		storagePathInfo.setBucket("Bucket");
		storagePathInfo.setResolver(pathResolver);

		List<BlobKey> keys = storagePathInfo.getBlobkeysForAllStoredFiles(storage);
		Assert.assertTrue(filenamesArray.length == keys.size());

		List<StorageFile> storageFiles = storagePathInfo.getAllStoredItemFiles(storage);
		Assert.assertTrue(filenamesArray.length == storageFiles.size());

		/*
		 * Test to see differences between service URLS.
		 * withGoogleStorageFileName just creates a blobkey.
		 * BlobKey fileAKey = keys.get(0);
		 * // StorageFile testFile = storageFiles.get(0);
		 * ImagesService imageService = ImagesServiceFactory.getImagesService();
		 * // GcsFilename filename = new GcsFilename("Bucket", testFile.getFullPath());
		 * // ServingUrlOptions sA = ServingUrlOptions.Builder.withGoogleStorageFileName(filename.getObjectName());
		 * ServingUrlOptions sB = ServingUrlOptions.Builder.withBlobKey(fileAKey);
		 * ServingUrlOptions sC = ServingUrlOptions.Builder.withBlobKey(fileAKey).crop(true).imageSize(1024);
		 * // String a = imageService.getServingUrl(sA);
		 * String b = imageService.getServingUrl(sB);
		 * System.out.println(b);
		 */
	}
}
