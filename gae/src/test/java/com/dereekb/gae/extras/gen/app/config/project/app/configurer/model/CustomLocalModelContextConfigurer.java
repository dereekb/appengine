package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model;

import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl.CustomLocalModelEventListenerConfigurer;

/**
 * Custom configuration generator for a local model.
 *
 * @author dereekb
 *
 */
public interface CustomLocalModelContextConfigurer
        extends CustomLocalModelCrudConfigurer, CustomLocalModelEventListenerConfigurer,
        CustomLocalModelIterateControllerConfigurer, CustomLocalModelRoleSetLoaderConfigurer,
        CustomLocalModelChildrenRoleComponentConfigurer, SecuredQueryInitializerConfigurer {

}
