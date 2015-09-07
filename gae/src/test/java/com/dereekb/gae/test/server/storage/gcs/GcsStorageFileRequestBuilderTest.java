package com.dereekb.gae.test.server.storage.gcs;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.dereekb.gae.server.storage.gcs.files.GcsStorageFileRequestBuilder;
import com.dereekb.gae.server.storage.object.file.impl.StorableContentImpl;
import com.dereekb.gae.server.storage.object.file.options.StorableFileOptionsImpl;
import com.dereekb.gae.server.storage.object.file.options.StorableFileOptionsImpl.StorableFileVisibility;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;

/**
 *
 * @author dereekb
 * @deprecated Not sure what this tests.
 */
@Ignore
@Deprecated
public class GcsStorageFileRequestBuilderTest {

	@Test
	public void testDefaults() {
		GcsStorageFileRequestBuilder builder = new GcsStorageFileRequestBuilder();
		StorableFileOptionsImpl options = new StorableFileOptionsImpl();
		options.setVisibility(StorableFileVisibility.PUBLIC);

		options.setCache(true);
		options.setCacheTime(1);
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
