package com.dereekb.gae.extras.gen.app.config.app.services.remote.impl.event;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppEventServiceListenersConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * {@link AppEventServiceListenersConfigurer} implementation that adds nothing to the list.
 *
 * @author dereekb
 *
 */
public class NoopAppEventListenerConfigurer
        implements AppEventServiceListenersConfigurer {

	// MARK: AppEventServiceListenersConfigurer
	@Override
	public List<String> configureEventServiceListeners(AppConfiguration appConfiguration,
	                                                   SpringBeansXMLBuilder builder) {
		return new ArrayList<String>();
	}

}
