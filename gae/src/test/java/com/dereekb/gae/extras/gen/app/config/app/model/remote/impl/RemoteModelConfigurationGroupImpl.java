package com.dereekb.gae.extras.gen.app.config.app.model.remote.impl;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.impl.AppModelConfigurationGroupImpl;

/**
 * {@link AppConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public class RemoteModelConfigurationGroupImpl extends AppModelConfigurationGroupImpl<RemoteModelConfiguration>
        implements RemoteModelConfigurationGroup {

	public RemoteModelConfigurationGroupImpl(List<RemoteModelConfiguration> modelConfigurations) {
		super(modelConfigurations);
	}

	public RemoteModelConfigurationGroupImpl(String groupName, List<RemoteModelConfiguration> modelConfigurations) {
		super(groupName, modelConfigurations);
	}

	// MARK: AppModelConfigurationGroup
	@Override
	public List<RemoteModelConfiguration> getModelConfigurations() {
		return this.getModelConfigurations();
	}

}
