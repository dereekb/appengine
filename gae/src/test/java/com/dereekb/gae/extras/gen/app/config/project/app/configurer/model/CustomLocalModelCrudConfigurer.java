package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 *
 * @author dereekb
 *
 */
public interface CustomLocalModelCrudConfigurer {

	public void configureCrudServiceComponents(AppConfiguration appConfig,
	                                           AppModelConfiguration modelConfig,
	                                           SpringBeansXMLBuilder builder);

}
