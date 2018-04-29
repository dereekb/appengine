package com.dereekb.gae.extras.gen.app.config.project.app.context.model.impl.iterate;

import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;

// MARK: IterateConfigurerInstance Task Enttry
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