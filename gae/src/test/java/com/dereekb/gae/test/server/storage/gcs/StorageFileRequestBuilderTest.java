package com.dereekb.gae.test.server.storage.gcs;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.server.storage.object.file.impl.StorableContentImpl;
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

		StorableContentImpl content = new StorableContentImpl(null, null, "test/test");
		GcsFileOptions builtOptions = builder.buildOptions(content);

		String acl = builtOptions.getAcl();
		Assert.assertNotNull(acl);

		String cacheControl = builtOptions.getCacheControl();
		Assert.assertNotNull(cacheControl);

		String mimetype = builtOptions.getMimeType();
		Assert.assertNotNull(mimetype);
	}

}
