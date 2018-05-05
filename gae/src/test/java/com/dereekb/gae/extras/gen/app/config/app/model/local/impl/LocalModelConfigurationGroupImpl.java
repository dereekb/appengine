package com.dereekb.gae.extras.gen.app.config.app.model.local.impl;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.impl.AppModelConfigurationGroupImpl;

/**
 * {@link AppConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public class LocalModelConfigurationGroupImpl extends AppModelConfigurationGroupImpl<LocalModelConfiguration>
        implements LocalModelConfigurationGroup {

	public LocalModelConfigurationGroupImpl(List<LocalModelConfiguration> modelConfigurations) {
		super(modelConfigurations);
	}

	public LocalModelConfigurationGroupImpl(String groupName, List<LocalModelConfiguration> modelConfigurations) {
		super(groupName, modelConfigurations);
	}

	// MARK: AppModelConfigurationGroup
	@Override
	public List<LocalModelConfiguration> getModelConfigurations() {
		return super.getModelConfigurations();
	}

}
