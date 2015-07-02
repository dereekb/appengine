package com.thevisitcompany.gae.test.deprecated.server.storage.gcs;

import com.thevisitcompany.gae.server.storage.file.Storable;
import com.thevisitcompany.gae.server.storage.file.StorageFilenameFormat;
import com.thevisitcompany.gae.test.spring.CoreServiceTestingContext;

@Deprecated
public abstract class AbstractGcsTests extends CoreServiceTestingContext {

	public static final String STORABLE_FORMAT = "Storable-%s";

	@StorageFilenameFormat(STORABLE_FORMAT)
	public static class GCSTestStorable
	        implements Storable {

		private String name = "default";

		public GCSTestStorable() {}

		public GCSTestStorable(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String getFilename() {
			return this.name;
		}

	}

	public static final String STORAGE_NAME = "storage";
	public static final String STORAGE_B_NAME = "storage b";

	/**
	 * An object that stores Storable objects.
	 *
	 * @author dereekb
	 *
	 */
	public static class GCSTestStorage
	        implements StorageHolder {

		private static final long serialVersionUID = 1L;

		private String name = "default";
		private StorageFileReferenceSet filesA = new StorageFileReferenceSet();
		private StorageFileReferenceSet filesB = new StorageFileReferenceSet();

		public GCSTestStorage() {}

		public GCSTestStorage(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Storage(STORAGE_NAME)
		public StorageFileReferenceSet getFilesA() {
			return this.filesA;
		}

		public void setFilesA(StorageFileReferenceSet filesA) {
			this.filesA = filesA;
		}

		@Storage(STORAGE_B_NAME)
		public StorageFileReferenceSet getFilesB() {
			return this.filesB;
		}

		public void setFilesB(StorageFileReferenceSet filesB) {
			this.filesB = filesB;
		}

	}

	public static class GCSTestStoragePathResolver
	        implements StoragePathResolver<GCSTestStorage> {

		@Override
		public String pathForObjectFolder(GCSTestStorage object) {
			return object.getName();
		}

	}

	public static class GCSStringTestPathResolver
	        implements StoragePathResolver<String> {

		@Override
		public String pathForObjectFolder(String object) {
			return object;
		}

	}

}
