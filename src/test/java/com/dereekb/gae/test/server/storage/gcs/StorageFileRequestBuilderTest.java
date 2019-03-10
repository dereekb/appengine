package com.dereekb.gae.test.server.storage.gcs;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.server.storage.object.file.StorableFile;
import com.dereekb.gae.server.storage.object.file.impl.StorableContentImpl;
import com.dereekb.gae.server.storage.object.file.impl.StorableFileImpl;
import com.dereekb.gae.server.storage.object.file.options.StorableFileVisibility;
import com.dereekb.gae.server.storage.object.file.options.impl.StorableFileCacheOptionsImpl;
import com.dereekb.gae.server.storage.object.file.options.impl.StorableFileOptionsImpl;
import com.dereekb.gae.server.storage.services.gcs.impl.GcsStorableFileSaveRequestBuilderImpl;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;

/**
 *
 * @author dereekb
 */
public class StorageFileRequestBuilderTest {

	@Test
	public void testDefaults() {
		GcsStorableFileSaveRequestBuilderImpl builder = new GcsStorableFileSaveRequestBuilderImpl();
		StorableFileOptionsImpl options = new StorableFileOptionsImpl();
		options.setVisibility(StorableFileVisibility.PUBLIC);

		StorableFileCacheOptionsImpl cacheOptions = new StorableFileCacheOptionsImpl();

		cacheOptions.setCacheable(true);
		cacheOptions.setMaxCacheTime(1L);

		options.setCacheOptions(cacheOptions);
		builder.setDefaultOptions(options);

		byte[] bytes = new byte[] { 1, 1, 1, 1 };
		StorableFile file = new StorableFileImpl("x", "y");
		StorableContentImpl content = new StorableContentImpl(file, bytes, "test/test");
		GcsFileOptions builtOptions = builder.buildOptions(content);

		String acl = builtOptions.getAcl();
		assertNotNull(acl);

		String cacheControl = builtOptions.getCacheControl();
		assertNotNull(cacheControl);

		String mimetype = builtOptions.getMimeType();
		assertNotNull(mimetype);
	}

}
