package com.dereekb.gae.extras.gen.app.config.app.services.remote.impl.event;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppEventServiceListenersConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.event.webhook.listener.impl.WebHookEventSubmitterImpl;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * Simple {@link AppEventServiceListenersConfigurer} implementation that adds
 * the {@link WebHookEventSubmitterImpl} bean to the events list.
 *
 * @author dereekb
 *
 */
public class WebHookEventSubmitterImplEventListenerConfigurer
        implements AppEventServiceListenersConfigurer {

	// MARK: AppEventServiceListenersConfigurer
	@Override
	public List<String> configureEventServiceListeners(AppConfiguration appConfiguration,
	                                                   SpringBeansXMLBuilder builder) {
		String webHookEventSubmitterBeanId = appConfiguration.getAppBeans().getWebHookEventSubmitterBeanId();
		return ListUtility.wrap(webHookEventSubmitterBeanId);
	}

}
