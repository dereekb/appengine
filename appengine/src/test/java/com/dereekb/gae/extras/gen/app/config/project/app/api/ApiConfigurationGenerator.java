package com.dereekb.gae.extras.gen.app.config.project.app.api;

import java.util.Properties;

import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.project.app.api.extensions.ApiDebugConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.project.app.api.extensions.ApiLinkConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.project.app.api.extensions.ApiLoginConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.project.app.api.extensions.ApiModelContextConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.project.app.api.extensions.ApiNotificationConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.project.app.api.extensions.ApiScheduleConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.project.app.api.extensions.ApiSearchConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.project.app.api.extensions.ApiServerConfigurationGenerator;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;

public class ApiConfigurationGenerator extends AbstractConfigurationFileGenerator {

	public static final String API_FILE_NAME = "api";
	public static final String API_FOLDER_NAME = "api";

	private String apiFileName = API_FILE_NAME;
	private String apiFolderName = API_FOLDER_NAME;

	public ApiConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
	}

	public ApiConfigurationGenerator(AppConfiguration appConfig) {
		super(appConfig);
	}

	public ApiConfigurationGenerator(AbstractConfigurationFileGenerator generator) {
		super(generator);
	}

	// MARK: AbstractConfigurationFileGenerator
	@Override
	public GenFolder generateConfigurations() {
		GenFolderImpl folder = new GenFolderImpl(this.apiFolderName);

		folder.addFile(this.makeApiFile());

		// Models
		ApiModelsConfigurationGenerator modelsGen = new ApiModelsConfigurationGenerator(this.getAppConfig(),
		        this.getOutputProperties());
		folder.addFolder(modelsGen.generateConfigurations());

		// Remote
		ApiRemoteConfigurationsGenerator remoteGen = new ApiRemoteConfigurationsGenerator(this.getAppConfig(),
		        this.getOutputProperties());
		folder.addFolder(remoteGen.generateConfigurations());

		// Extensions
		folder.addFolder(this.generateExtensions());

		return folder;
	}

	public GenFile makeApiFile() {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Configuration");
		builder.getRawXMLBuilder().elem("mvc:annotation-driven");
		builder.bean().beanClass(MethodValidationPostProcessor.class);

		builder.comment("Models");
		builder.imp("/model/model.xml");

		builder.comment("Extensions");
		builder.imp("/extension/extensions.xml");

		return this.makeFileWithXML(this.apiFileName, builder);
	}

	public GenFolderImpl generateExtensions() {
		GenFolderImpl extensions = new GenFolderImpl("extension");

		AppConfiguration appConfig = this.getAppConfig();

		// Login
		if (appConfig.isLoginServer()) {
			extensions.merge(new ApiLoginConfigurationGenerator(appConfig, this.getOutputProperties())
			        .generateConfigurations());
		}

		// Model Context / Roles
		extensions.merge(new ApiModelContextConfigurationGenerator(appConfig, this.getOutputProperties())
		        .generateConfigurations());

		// Link
		extensions.merge(new ApiLinkConfigurationGenerator(appConfig, this.getOutputProperties())
		        .generateConfigurations());

		// Schedule
		extensions.merge(new ApiScheduleConfigurationGenerator(appConfig, this.getOutputProperties())
		        .generateConfigurations());

		// Notification
		if (appConfig.hasNotificationServices() && appConfig.isRootServer()) {

			// Only add the notifications API to the root server.
			extensions.merge(new ApiNotificationConfigurationGenerator(appConfig, this.getOutputProperties())
			        .generateConfigurations());
		}

		// Search
		extensions.merge(new ApiSearchConfigurationGenerator(appConfig, this.getOutputProperties())
		        .generateConfigurations());

		// Server
		extensions.merge(new ApiServerConfigurationGenerator(appConfig, this.getOutputProperties())
		        .generateConfigurations());

		// Debug
		extensions.merge(new ApiDebugConfigurationGenerator(appConfig, this.getOutputProperties())
		        .generateConfigurations());

		// Extensions Import File
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();
		builder.importResources(extensions.getFiles());
		extensions.addFile(this.makeFileWithXML("extensions", builder));

		return extensions;
	}

}
