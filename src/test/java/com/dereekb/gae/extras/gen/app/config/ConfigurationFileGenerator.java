package com.dereekb.gae.extras.gen.app.config;

import com.dereekb.gae.extras.gen.utility.GenFolder;

public interface ConfigurationFileGenerator {

	/**
	 * Returns a folder of generated configurations.
	 *
	 * @return {@link GenFolder}. Never {@code null}, but the folder may be
	 *         empty.
	 */
	public GenFolder generateConfigurations();

}
