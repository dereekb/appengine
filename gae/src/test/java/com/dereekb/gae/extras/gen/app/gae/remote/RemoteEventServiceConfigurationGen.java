package com.dereekb.gae.extras.gen.app.gae.remote;

import com.dereekb.gae.extras.gen.app.config.app.impl.AppServiceConfigurationInfoImpl;

/**
 * {@link RemoteServiceConfigurationGen} for the event service.
 *
 * @author dereekb
 *
 */
public class RemoteEventServiceConfigurationGen extends AbstractRemoteServiceConfigurationGen {

	public static final String DEFAULT_SERVICE_NAME = "event";

	public RemoteEventServiceConfigurationGen(String projectAppId) {
		super(new AppServiceConfigurationInfoImpl(projectAppId, DEFAULT_SERVICE_NAME));
	}

	public RemoteEventServiceConfigurationGen(String projectAppId, String appVersion) {
		super(new AppServiceConfigurationInfoImpl(projectAppId, DEFAULT_SERVICE_NAME, appVersion));
	}

}
