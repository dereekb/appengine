package com.dereekb.gae.extras.gen.app.config.project.app.taskqueue;

import java.util.List;
import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.AppSpringContextType;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractRemoteServiceConfigurationGenerator;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * Used for configuring remote models that are required in the API context only.
 * <p>
 * This configuration is not used much, as using the
 * {@link AppSpringContextType#SHARED} context is preferred for these cases.
 *
 * @author dereekb
 *
 */
public class TaskQueueRemoteConfigurationsGenerator extends AbstractRemoteServiceConfigurationGenerator {

	public TaskQueueRemoteConfigurationsGenerator(AppConfiguration appConfig) {
		this(appConfig, null);
	}

	public TaskQueueRemoteConfigurationsGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setResultsFolderName("remote");
		this.setMakeServiceSubFolders(true);
	}

	// MARK: AbstractRemoteModelConfigurationGenerator
	@Override
	public SpringBeansXMLBuilder makeXMLServiceConfigurationFile(RemoteServiceConfiguration service)
	        throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Remote Service TaskQueue Components");
		service.getRemoteServiceContextConfigurer().configureRemoteServiceContextComponents(AppSpringContextType.TASKQUEUE,
		        this.getAppConfig(), service, builder);

		return builder;
	}

	@Override
	protected List<GenFolder> makeSubFoldersForService(RemoteServiceConfiguration service) {
		return ListUtility.wrap(this.makeModelConfigurationsForService(service));
	}

	// MARK: Models
	protected GenFolder makeModelConfigurationsForService(RemoteServiceConfiguration service) {
		return new GenFolderImpl("models", service.getRemoteServiceContextConfigurer()
		        .configureRemoteServiceModelComponents(AppSpringContextType.TASKQUEUE, this.getAppConfig(), service));
	}

	@Override
	public String toString() {
		return "ApiRemoteConfigurationsGenerator []";
	}

}
