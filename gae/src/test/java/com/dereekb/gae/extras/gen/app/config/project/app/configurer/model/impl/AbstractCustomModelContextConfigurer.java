package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.CustomLocalModelContextConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.CustomModelContextConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.CustomRemoteModelContextConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanConstructorBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Implements both {@link CustomLocalModelContextConfigurer} and
 * {@link CustomRemoteModelContextConfigurer}.
 *
 * @author dereekb
 *
 */
public class AbstractCustomModelContextConfigurer
        implements CustomModelContextConfigurer {

	// MARK: CustomLocalModelContextConfigurer
	@Override
	public void configureCrudServiceComponents(AppConfiguration appConfig,
	                                           LocalModelConfiguration modelConfig,
	                                           SpringBeansXMLBuilder builder) {
		// Override in sub-class
	}

	@Override
	public boolean hasIterateControllerEntry() {
		return false;
	}

	@Override
	public void configureIterateControllerTasks(AppConfiguration appConfig,
	                                            LocalModelConfiguration modelConfig,
	                                            SpringBeansXMLBuilder builder) {
		// Override in sub-class
	}

	@Override
	public void configureModelRoleSetLoaderComponents(AppConfiguration appConfig,
	                                                  LocalModelConfiguration modelConfig,
	                                                  SpringBeansXMLBuilder builder) {
		// Override in sub-class
	}

	@Override
	public void configureModelChildrenRoleComponents(AppConfiguration appConfig,
	                                                 LocalModelConfiguration modelConfig,
	                                                 SpringBeansXMLBuilder builder) {
		// Override in sub-class
	}

	@Override
	public void configureSecuredQueryInitializer(AppConfiguration appConfig,
	                                             LocalModelConfiguration modelConfig,
	                                             SpringBeansXMLBeanConstructorBuilder<?> beanConstructor) {
		// Override in sub-class
	}

	// MARK: CustomRemoteModelContextConfigurer
	@Override
	public void configureRemoteModelSharedContextComponents(AppConfiguration appConfig,
	                                                        LocalModelConfiguration modelConfig,
	                                                        SpringBeansXMLBuilder builder) {
		// Override in sub-class
	}

	@Override
	public void configureRemoteModelTaskQueueContextComponents(AppConfiguration appConfig,
	                                                           LocalModelConfiguration modelConfig,
	                                                           SpringBeansXMLBuilder builder) {
		// Override in sub-class
	}

}
