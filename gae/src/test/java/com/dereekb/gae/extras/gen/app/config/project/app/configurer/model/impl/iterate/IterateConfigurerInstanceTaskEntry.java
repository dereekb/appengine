package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl.iterate;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;

// MARK: IterateConfigurerInstance Task Entry
public interface IterateConfigurerInstanceTaskEntry {

	/**
	 * Adds this entry to the reference map.
	 */
	public void configureMapEntry(AppModelConfiguration modelConfig,
	                              SpringBeansXMLMapBuilder<?> map);

	/**
	 * Configures all components for this task.
	 */
	public void configureTaskComponents(AppConfiguration appConfig,
	                                    AppModelConfiguration modelConfig,
	                                    SpringBeansXMLBuilder builder);

}