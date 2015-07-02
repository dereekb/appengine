package com.thevisitcompany.gae.test.deprecated.server.storage.gcs.generator;

import com.thevisitcompany.gae.server.storage.StorageAccessor;
import com.thevisitcompany.gae.server.storage.file.Storable;
import com.thevisitcompany.gae.server.storage.file.StorageFile;
import com.thevisitcompany.gae.server.storage.file.StorageFileContent;
import com.thevisitcompany.gae.test.model.extension.generator.data.TestByteDataGenerator;
import com.thevisitcompany.gae.test.model.extension.generator.data.TestImageByteGenerator;

/**
 * Generates blank GCS Data
 *
 * @author dereekb
 *
 */
@Deprecated
public class TestGcsDataGenerator<T> {

	private TestByteDataGenerator byteGenerator = new TestImageByteGenerator();
	private StorageAccessor accessor;
	private StoragePathInfo<T> pathInfo;

	public boolean generateData(T storage,
	                            Storable storable) {
		byte[] data = this.byteGenerator.generateBytes();
		StorageFile file = this.pathInfo.getStorableItemFile(storage, storable);
		StorageFileContent storageFileData = new StorageFileContent(file, data, "example");

		boolean success = this.accessor.saveFile(storageFileData);
		return success;
	}

	public boolean generateData(T storage,
	                            String name) {
		byte[] data = this.byteGenerator.generateBytes();
		StorageFile file = this.pathInfo.getStorableItemFile(storage, name);
		StorageFileContent storageFileData = new StorageFileContent(file, data, "example");
		boolean success = this.accessor.saveFile(storageFileData);
		return success;
	}

	public StorageAccessor getAccessor() {
		return this.accessor;
	}

	public void setAccessor(StorageAccessor accessor) {
		this.accessor = accessor;
	}

	public StoragePathInfo<T> getPathInfo() {
		return this.pathInfo;
	}

	public void setPathInfo(StoragePathInfo<T> pathInfo) {
		this.pathInfo = pathInfo;
	}

	public TestByteDataGenerator getByteGenerator() {
		return this.byteGenerator;
	}

	public void setByteGenerator(TestByteDataGenerator byteGenerator) {
		this.byteGenerator = byteGenerator;
	}

}
