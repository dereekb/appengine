package com.dereekb.gae.extras.gen.app.config.project.test;

import java.util.Properties;

import com.dereekb.gae.client.api.auth.model.impl.ClientModelRolesContextServiceRequestSenderImpl;
import com.dereekb.gae.client.api.auth.token.impl.ClientLoginTokenValidationServiceRequestSenderImpl;
import com.dereekb.gae.client.api.model.crud.builder.impl.ClientCreateRequestSenderImpl;
import com.dereekb.gae.client.api.model.crud.builder.impl.ClientDeleteRequestSenderImpl;
import com.dereekb.gae.client.api.model.crud.builder.impl.ClientReadRequestSenderImpl;
import com.dereekb.gae.client.api.model.crud.builder.impl.ClientUpdateRequestSenderImpl;
import com.dereekb.gae.client.api.model.extension.link.impl.ClientLinkRequestSenderImpl;
import com.dereekb.gae.client.api.model.extension.search.query.builder.impl.ClientQueryRequestSenderImpl;
import com.dereekb.gae.client.api.service.sender.impl.ClientApiRequestSenderImpl;
import com.dereekb.gae.client.api.service.sender.security.impl.ClientRequestSecurityImpl;
import com.dereekb.gae.client.api.service.sender.security.impl.SecuredClientApiRequestSenderImpl;
import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractModelConfigurationGenerator;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.model.extension.generation.impl.DerivedGeneratorImpl;
import com.dereekb.gae.test.mock.client.crud.MockClientRequestSender;
import com.dereekb.gae.test.model.extension.generator.impl.TestModelGeneratorImpl;
import com.dereekb.gae.test.server.datastore.objectify.ObjectifyTestDatabase;
import com.dereekb.gae.utilities.misc.path.PathUtility;

/**
 * Generator for unit/integration test client components.
 *
 *
 * @author dereekb
 *
 */
public class TestModelsConfigurationGenerator extends AbstractConfigurationFileGenerator {

	public static final String TEST_FOLDER_NAME = "test";

	public TestModelsConfigurationGenerator(AbstractConfigurationFileGenerator generator) {
		super(generator);
	}

	public TestModelsConfigurationGenerator(AppConfiguration appConfig) {
		super(appConfig);
	}

	public TestModelsConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
	}

	// MARK: Generation
	@Override
	public GenFolderImpl generateConfigurations() {

		GenFolderImpl folder = new GenFolderImpl("models");

		// Model Configurations
		TestModelConfigurationGenerator modelGenerator = new TestModelConfigurationGenerator();
		GenFolder modelFiles = modelGenerator.generateConfigurations();
		folder.merge(modelFiles);

		// Model Client Configurations Folder
		TestClientConfigurationGenerator clientGenerator = new TestClientConfigurationGenerator();
		clientGenerator.setResultsFolderName("client");

		GenFolder clientFolder = clientGenerator.generateConfigurations();
		folder.addFolder(clientFolder);

		// Models File
		GenFile modelsfile = this.makeSharedModelXMLFile(modelFiles, clientFolder);
		folder.addFile(modelsfile);

		return folder;
	}

	public static final String SHARED_MODEL_FILE_NAME = "models";

	public GenFile makeSharedModelXMLFile(GenFolder modelFiles,
	                                      GenFolder clientFolder) {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		// Model Imports
		builder.getRawXMLBuilder().comment("Models");
		builder.importResources(modelFiles.getFiles());

		// Client Import
		builder.getRawXMLBuilder().comment("Client");
		builder.imp(PathUtility.buildPath(clientFolder.getFolderName(), CLIENT_SHARED_FILE_FULL_NAME));

		// Objectify Override
		builder.comment("Objectify Override");
		builder.bean("objectifyDatabase").beanClass(ObjectifyTestDatabase.class).primary().lazy(false).c()
		        .ref("objectifyDatabaseEntities");

		return this.makeFileWithXML(SHARED_MODEL_FILE_NAME, builder);
	}

	// MARK: Internal Generators
	private class TestModelConfigurationGenerator extends AbstractModelConfigurationGenerator {

		// MARK: Generation
		public TestModelConfigurationGenerator() {
			super(TestModelsConfigurationGenerator.this.getAppConfig(),
			        TestModelsConfigurationGenerator.this.getOutputProperties());
			this.setSplitByGroup(false);
		}

		// MARK: Client Configurations
		@Override
		public SpringBeansXMLBuilder makeXMLModelClientConfigurationFile(LocalModelConfiguration modelConfig)
		        throws UnsupportedOperationException {

			SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

			String modelPrefix = modelConfig.getModelBeanPrefix();

			String modelGeneratorId = modelPrefix + "Generator";

			builder.comment("Generator");

			// Generator
			builder.bean(modelGeneratorId).beanClass(modelConfig.getModelGeneratorClass());

			// Test Model Generator
			builder.bean(modelPrefix + "TestModelGenerator").beanClass(TestModelGeneratorImpl.class).c()
			        .ref(modelConfig.getModelRegistryId()).ref(modelGeneratorId);

			builder.comment("Data Generator");

			// Test Model Data Generator
			builder.bean(modelPrefix + "TestDataGenerator").beanClass(DerivedGeneratorImpl.class).c()
			        .ref(modelGeneratorId).ref(modelPrefix + "DataBuilder");

			return builder;
		}

	}

	public static final String CLIENT_SHARED_FILE_NAME = "client";
	public static final String CLIENT_SHARED_FILE_FULL_NAME = CLIENT_SHARED_FILE_NAME + ".xml";

	private class TestClientConfigurationGenerator extends AbstractModelConfigurationGenerator {

		// MARK: Generation
		public TestClientConfigurationGenerator() {
			super(TestModelsConfigurationGenerator.this.getAppConfig(),
			        TestModelsConfigurationGenerator.this.getOutputProperties());
		}

		// MARK: Client Configurations
		@Override
		public GenFolderImpl generateConfigurations() {
			GenFolderImpl folder = super.generateConfigurations();

			folder.addFile(this.makeSharedClientXMLFile(folder));

			return folder;
		}

		public GenFile makeSharedClientXMLFile(GenFolderImpl folder) {
			SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

			// Models Import
			builder.comment("Models");

			builder.importResources(folder.getFiles());

			// Base Client-Request Sender
			builder.comment("Base Client Sender");

			builder.bean("mockClientRequestSender").beanClass(MockClientRequestSender.class);

			builder.bean("clientApiRequestSender").beanClass(ClientApiRequestSenderImpl.class).c()
			        .ref("mockClientRequestSender");

			builder.bean("securedClientRequestSender").beanClass(SecuredClientApiRequestSenderImpl.class).c()
			        .ref("clientApiRequestSender").ref("testDefaultClientRequestSecurity")
			        .ref("systemLoginTokenFactory");

			builder.bean("testDefaultClientRequestSecurity").beanClass(ClientRequestSecurityImpl.class)
			        .property("securityContextType").value("SYSTEM");

			// Shared
			builder.comment("Shared");

			builder.bean("clientLinkRequestSender").beanClass(ClientLinkRequestSenderImpl.class).c()
			        .ref("modelKeyTypeConverter").ref("securedClientRequestSender");

			builder.bean("clientModelRolesContextServiceRequestSender")
			        .beanClass(ClientModelRolesContextServiceRequestSenderImpl.class).c().ref("modelKeyTypeConverter")
			        .ref("securedClientRequestSender");

			if (this.getAppConfig().isLoginServer()) {
				builder.bean("clientLoginTokenValidationServiceRequestSender")
				        .beanClass(ClientLoginTokenValidationServiceRequestSenderImpl.class).c()
				        .ref("securedClientRequestSender");
			}

			return this.makeFileWithXML(CLIENT_SHARED_FILE_NAME, builder);
		}

		@Override
		public SpringBeansXMLBuilder makeXMLModelClientConfigurationFile(LocalModelConfiguration modelConfig)
		        throws UnsupportedOperationException {

			SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

			String modelPrefix = modelConfig.getModelBeanPrefix();

			builder.bean(modelPrefix + "ClientCreateRequestSender").beanClass(ClientCreateRequestSenderImpl.class).c()
			        .ref(modelConfig.getModelDataConverterBeanId()).ref("modelKeyTypeConverter")
			        .ref("securedClientRequestSender");

			builder.bean(modelPrefix + "ClientReadRequestSender").beanClass(ClientReadRequestSenderImpl.class).c()
			        .ref(modelConfig.getModelDataConverterBeanId()).ref("modelKeyTypeConverter")
			        .ref("securedClientRequestSender");

			builder.bean(modelPrefix + "ClientUpdateRequestSender").beanClass(ClientUpdateRequestSenderImpl.class).c()
			        .ref(modelConfig.getModelDataConverterBeanId()).ref("modelKeyTypeConverter")
			        .ref("securedClientRequestSender");

			builder.bean(modelPrefix + "ClientDeleteRequestSender").beanClass(ClientDeleteRequestSenderImpl.class).c()
			        .ref(modelConfig.getModelDataConverterBeanId()).ref("modelKeyTypeConverter")
			        .ref("securedClientRequestSender");

			builder.bean(modelPrefix + "ClientQueryRequestSender").beanClass(ClientQueryRequestSenderImpl.class).c()
			        .ref(modelConfig.getModelDataConverterBeanId()).ref("modelKeyTypeConverter")
			        .ref("securedClientRequestSender").ref(modelConfig.getModelRegistryId());

			return builder;
		}

	}

}
