package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.SecuredQueryInitializerConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanConstructorBuilder;

/**
 * {@link SecuredQueryInitializerConfigurer} implementation that injects
 * securityOverrideAdminOnlyModelQueryTask.
 *
 * @author dereekb
 *
 */
public class AdminOnlySecuredQueryInitializerConfigurerImpl
        implements SecuredQueryInitializerConfigurer {

	private String securityOverrideTaskBean = "securityOverrideAdminOnlyModelQueryTask";

	public String getSecurityOverrideTaskBean() {
		return this.securityOverrideTaskBean;
	}

	public void setSecurityOverrideTaskBean(String securityOverrideTaskBean) {
		if (securityOverrideTaskBean == null) {
			throw new IllegalArgumentException("securityOverrideTaskBean cannot be null.");
		}

		this.securityOverrideTaskBean = securityOverrideTaskBean;
	}

	// MARK: SecuredQueryInitializerConfigurer
	@Override
	public void configureSecuredQueryInitializer(AppConfiguration appConfig,
	                                             LocalModelConfiguration modelConfig,
	                                             SpringBeansXMLBeanConstructorBuilder<?> beanConstructor) {
		beanConstructor.ref(this.securityOverrideTaskBean);
	}

	@Override
	public String toString() {
		return "AdminOnlySecuredQueryInitializerConfigurerImpl [securityOverrideTaskBean="
		        + this.securityOverrideTaskBean + "]";
	}

}
