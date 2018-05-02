package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model;

/**
 * Custom configuration generator for a local model.
 *
 * @author dereekb
 *
 */
public interface CustomLocalModelContextConfigurer
        extends CustomLocalModelCrudConfigurer, CustomLocalModelIterateControllerConfigurer,
        CustomLocalModelRoleSetLoaderConfigurer, CustomLocalModelChildrenRoleComponentConfigurer,
        SecuredQueryInitializerConfigurer {

}
