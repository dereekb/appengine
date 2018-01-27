package com.dereekb.gae.test.extras.gen.app;

import com.dereekb.gae.client.api.model.crud.builder.impl.ClientCreateRequestSenderImpl;
import com.dereekb.gae.client.api.model.crud.builder.impl.ClientDeleteRequestSenderImpl;
import com.dereekb.gae.client.api.model.crud.builder.impl.ClientReadRequestSenderImpl;
import com.dereekb.gae.client.api.model.crud.builder.impl.ClientUpdateRequestSenderImpl;
import com.dereekb.gae.model.extension.generation.impl.DerivedGeneratorImpl;
import com.dereekb.gae.test.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.test.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.test.extras.gen.utility.GenFile;
import com.dereekb.gae.test.extras.gen.utility.GenFolder;
import com.dereekb.gae.test.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.test.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.test.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
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
public class TestModelsConfigurationGenerator extends AbstractConfigurationGenerator {

	// MARK: Generation
	public TestModelsConfigurationGenerator(AppConfiguration appConfig) {
		super(appConfig);
	}

	@Override
	public GenFolder generateConfigurations() {

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

	public GenFile makeSharedModelXMLFile(GenFolder modelFiles, GenFolder clientFolder) {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		// Objectify Override
		builder.bean("objectifyDatabase").beanClass(ObjectifyTestDatabase.class).c().ref("objectifyDatabaseEntities");

		// Model Imports
		builder.getRawXMLBuilder().comment("Models");
		builder.importResources(modelFiles.getFiles());

		// Client Import
		builder.getRawXMLBuilder().comment("Client");
		builder.imp(PathUtility.buildPath(clientFolder.getFolderName(), CLIENT_SHARED_FILE_NAME));

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
		public SpringBeansXMLBuilder makeXMLModelClientConfigurationFile(AppModelConfiguration modelConfig)
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

			builder.importResources(folder.getFiles());

			return this.makeFileWithXML(CLIENT_SHARED_FILE_NAME, builder);
		}

		@Override
		public SpringBeansXMLBuilder makeXMLModelClientConfigurationFile(AppModelConfiguration modelConfig)
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

			return builder;
		}

	}

}
