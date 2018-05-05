package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.CustomLocalModelChildrenRoleComponentConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Default {@link CustomLocalModelChildrenRoleComponentConfigurer}
 * implementation.
 * <p>
 * Attempts to find the default parent context reader class. If not found, will
 * leave a "todo" by default.
 *
 * @author dereekb
 *
 */
public class CustomLocalModelChildrenRoleComponentConfigurerImpl
        implements CustomLocalModelChildrenRoleComponentConfigurer {

	private boolean leaveTodo = true;

	public boolean isLeaveTodo() {
		return this.leaveTodo;
	}

	public void setLeaveTodo(boolean leaveTodo) {
		this.leaveTodo = leaveTodo;
	}

	// MARK: CustomLocalModelChildrenRoleComponentConfigurer
	@Override
	public void configureModelChildrenRoleComponents(AppConfiguration appConfig,
	                                                 LocalModelConfiguration modelConfig,
	                                                 SpringBeansXMLBuilder builder) {
		builder.comment("Children");

		try {
			String defaultParentContextClassName = modelConfig.getBaseClassPath() + ".security.parent."
			        + modelConfig.getBaseClassSimpleName() + "ParentModelRoleSetContextReaderImpl";

			Class<?> defaultParentContextClass = Class.forName(defaultParentContextClassName);

			String defaultParentContextComponentName = modelConfig.getModelBeanPrefix()
			        + "ParentModelRoleSetContextReader";
			builder.bean(defaultParentContextComponentName).beanClass(defaultParentContextClass).c()
			        .ref(modelConfig.getModelSecurityContextServiceEntryBeanId());
		} catch (ClassNotFoundException e) {
			if (this.leaveTodo) {
				builder.comment("TODO: Complete if applicable.");
			}
		}

	}

}
