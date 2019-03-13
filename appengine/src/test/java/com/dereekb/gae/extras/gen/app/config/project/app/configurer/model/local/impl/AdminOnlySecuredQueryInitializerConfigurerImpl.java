package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.SecuredQueryInitializerConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanConstructorBuilder;

/**
 * {@link SecuredQueryInitializerConfigurer} implementation that injects
 * the AdminOnlySecurityModelQueryTask bean.
 *
 * @author dereekb
 *
 */
public class AdminOnlySecuredQueryInitializerConfigurerImpl
        implements SecuredQueryInitializerConfigurer {

	// MARK: SecuredQueryInitializerConfigurer
	@Override
	public void configureSecuredQueryInitializer(AppConfiguration appConfig,
	                                             LocalModelConfiguration modelConfig,
	                                             SpringBeansXMLBeanConstructorBuilder<?> beanConstructor) {
		beanConstructor.ref(appConfig.getAppBeans().getUtilityBeans().getAdminOnlySecurityModelQueryTaskBeanId());
	}

	@Override
	public String toString() {
		return "AdminOnlySecuredQueryInitializerConfigurerImpl []";
	}

}
