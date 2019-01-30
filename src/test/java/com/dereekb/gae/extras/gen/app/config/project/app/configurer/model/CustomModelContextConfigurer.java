package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model;

import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelContextConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.remote.RemoteModelContextConfigurer;

/**
 * Custom model bean context configurer.
 *
 * @author dereekb
 *
 * @deprecated Split configurer into local and remote.
 */
@Deprecated
public interface CustomModelContextConfigurer
        extends LocalModelContextConfigurer, RemoteModelContextConfigurer {

}
