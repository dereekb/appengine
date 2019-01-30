package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.iterate;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;

// MARK: IterateConfigurerInstance Task Entry
public interface IterateConfigurerInstanceTaskEntry {

	/**
	 * Adds this entry to the reference map.
	 */
	public void configureMapEntry(LocalModelConfiguration modelConfig,
	                              SpringBeansXMLMapBuilder<?> map);

	/**
	 * Configures all components for this task.
	 */
	public void configureTaskComponents(AppConfiguration appConfig,
	                                    LocalModelConfiguration modelConfig,
	                                    SpringBeansXMLBuilder builder);

}