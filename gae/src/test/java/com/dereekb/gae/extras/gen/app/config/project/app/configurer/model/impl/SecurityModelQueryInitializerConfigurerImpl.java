package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppSecurityBeansConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.SecuredQueryInitializerConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanConstructorBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLListBuilder;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * {@link SecuredQueryInitializerConfigurer} implementation that uses the app's
 * security configuration to generate an override task.
 *
 * @author dereekb
 *
 */
public class SecurityModelQueryInitializerConfigurerImpl
        implements SecuredQueryInitializerConfigurer {

	private List<String> delegateBeanRefs;

	public SecurityModelQueryInitializerConfigurerImpl(String... delegateBeanRefs) {
		this(ListUtility.toList(delegateBeanRefs));
	}

	public SecurityModelQueryInitializerConfigurerImpl(List<String> delegateBeanRefs) {
		this.setDelegateBeanRefs(delegateBeanRefs);
	}

	public List<String> getDelegateBeanRefs() {
		return this.delegateBeanRefs;
	}

	public void setDelegateBeanRefs(List<String> delegateBeanRefs) {
		if (delegateBeanRefs == null) {
			throw new IllegalArgumentException("delegateBeanRefs cannot be null.");
		}

		this.delegateBeanRefs = delegateBeanRefs;
	}

	// MARK: SecuredQueryInitializerConfigurer
	@Override
	public void configureSecuredQueryInitializer(AppConfiguration appConfig,
	                                             LocalModelConfiguration modelConfig,
	                                             SpringBeansXMLBeanConstructorBuilder<?> beanConstructor) {
		AppSecurityBeansConfigurer securityConfigurer = appConfig.getAppSecurityBeansConfigurer();

		Class<?> taskOverrideClass = securityConfigurer.getLoginSecurityModelQueryTaskOverrideClass();

		SpringBeansXMLListBuilder<?> listBuilder = beanConstructor.bean().beanClass(taskOverrideClass).c().list();

		for (String ref : this.delegateBeanRefs) {
			listBuilder.ref(ref);
		}
	}

	@Override
	public String toString() {
		return "SecurityModelQueryInitializerConfigurerImpl [delegateBeanRefs=" + this.delegateBeanRefs + "]";
	}

}
