package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model;

/**
 * Builds custom configurations for each model.
 *
 * @author dereekb
 *
 */
public interface CustomLocalModelContextConfigurer
        extends CustomLocalModelCrudConfigurer, CustomLocalModelIterateControllerConfigurer,
        CustomLocalModelRoleSetLoaderConfigurer, CustomLocalModelChildrenRoleComponentConfigurer,
        SecuredQueryInitializerConfigurer {

}
