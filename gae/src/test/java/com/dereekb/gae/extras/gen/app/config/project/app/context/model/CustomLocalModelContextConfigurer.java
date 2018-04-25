package com.dereekb.gae.extras.gen.app.config.project.app.context.model;

/**
 * Builds custom configurations for each model.
 *
 * @author dereekb
 *
 */
public interface CustomLocalModelContextConfigurer
        extends CustomLocalModelIterateControllerConfigurer, CustomLocalModelRoleSetLoaderConfigurer,
        CustomLocalModelChildrenRoleComponentConfigurer, SecuredQueryInitializerConfigurer {

}
