package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Used for configuring the Search API services for the local model.
 *
 * @author dereekb
 *
 */
public interface LocalModelSearchConfigurer {

	/**
	 * Whether or not there are any search components within this model.
	 *
	 * @return {@code true} if can search via the search API.
	 */
	public boolean hasSearchComponents();

	public void configureSearchComponents(AppConfiguration appConfig,
	                                      LocalModelConfiguration modelConfig,
	                                      SpringBeansXMLBuilder builder);

}
