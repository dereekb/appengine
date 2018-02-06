package com.dereekb.gae.extras.gen.app.config.project.app.context;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.impl.AbstractConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.web.api.exception.handler.ApiExceptionHandler;
import com.dereekb.gae.web.api.model.exception.handler.ModelRequestExceptionHandler;

public class ContextConfigurationGenerator extends AbstractConfigurationFileGenerator {

	public static final String CONTEXT_FILE_NAME = "context";
	public static final String CONTEXT_FOLDER_NAME = "context";

	private String contextFileName = CONTEXT_FILE_NAME;
	private String contextFolderName = CONTEXT_FOLDER_NAME;

	public ContextConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
	}

	public ContextConfigurationGenerator(AppConfiguration appConfig) {
		super(appConfig);
	}

	// MARK: AbstractConfigurationFileGenerator
	@Override
	public GenFolder generateConfigurations() {
		GenFolderImpl folder = new GenFolderImpl(this.contextFolderName);

		folder.addFile(this.makeContextFile());

		// Models
		ContextModelsConfigurationGenerator modelsGen = new ContextModelsConfigurationGenerator(this.getAppConfig(),
		        this.getOutputProperties());
		folder.addFolder(modelsGen.generateConfigurations());

		// Server
		ContextServerConfigurationsGenerator serverGen = new ContextServerConfigurationsGenerator(this.getAppConfig(),
		        this.getOutputProperties());
		folder.addFolder(serverGen.generateConfigurations());

		return folder;
	}

	public GenFile makeContextFile() {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("API Exception Handlers");
		builder.getRawXMLBuilder().e("mvc:annotation-driven");

		builder.bean("apiExceptionHandler").beanClass(ApiExceptionHandler.class);
		builder.bean("modelRequestExceptionHandler").beanClass(ModelRequestExceptionHandler.class);

		builder.comment("Import");
		builder.imp("/model/model.xml");
		builder.imp("/server/server.xml");

		return this.makeFileWithXML(this.contextFileName, builder);
	}

}
