package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local;

import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.SecuredQueryInitializerConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl.LocalModelEventListenerConfigurer;

/**
 * Custom configuration generator for a local model.
 *
 * @author dereekb
 *
 */
public interface LocalModelContextConfigurer
        extends LocalModelCrudConfigurer, LocalModelEventListenerConfigurer,
        LocalModelIterateControllerConfigurer, LocalModelRoleSetLoaderConfigurer,
        LocalModelChildrenRoleComponentConfigurer, SecuredQueryInitializerConfigurer {

}
