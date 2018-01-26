package com.dereekb.gae.test.extras.gen.app;

import com.dereekb.gae.test.extras.gen.utility.GenFolder;

public interface ConfigurationFileGenerator {

	/**
	 * Returns a folder of generated configurations.
	 *
	 * @return {@link GenFolder}. Never {@code null}, but the folder may be
	 *         empty.
	 */
	public GenFolder generateConfigurations();

}
