package com.dereekb.gae.extras.gen.app.config.project.test;

import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractModelConfigurationGenerator;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.model.extension.generation.impl.DerivedGeneratorImpl;
import com.dereekb.gae.test.model.extension.generator.impl.TestModelGeneratorImpl;
import com.dereekb.gae.test.server.datastore.objectify.ObjectifyTestDatabase;

/**
 * Generator for unit/integration test client components.
 *
 *
 * @author dereekb
 *
 */
public class TestContextModelsConfigurationGenerator extends AbstractConfigurationFileGenerator {

	public static final String MODELS_FOLDER_NAME = "context";
	public static final String SHARED_MODEL_FILE_NAME = MODELS_FOLDER_NAME;

	public TestContextModelsConfigurationGenerator(AbstractConfigurationFileGenerator generator) {
		super(generator);
	}

	// MARK: Generation
	@Override
	public GenFolderImpl generateConfigurations() {
		GenFolderImpl folder = new GenFolderImpl(MODELS_FOLDER_NAME);

		// Model Configurations
		TestModelConfigurationGenerator modelGenerator = new TestModelConfigurationGenerator();
		GenFolder modelFiles = modelGenerator.generateConfigurations();
		folder.merge(modelFiles);

		// Models File
		GenFile modelsfile = this.makeSharedModelXMLFile(folder);
		folder.addFile(modelsfile);

		return folder;
	}

	public GenFile makeSharedModelXMLFile(GenFolder folder) {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		// Model Imports
		builder.getRawXMLBuilder().comment("Imports");
		this.importFilesWithBuilder(builder, folder, true, true);

		// Objectify Override
		builder.comment("Objectify Override");

		builder.bean(this.getAppConfig().getAppBeans().getObjectifyDatabaseId()).beanClass(ObjectifyTestDatabase.class).primary().lazy(false).c()
				.ref("test_" + this.getAppConfig().getAppBeans().getObjectifyInitializerId())
		        .ref("objectifyDatabaseEntities");

		return this.makeFileWithXML(SHARED_MODEL_FILE_NAME, builder);
	}

	// MARK: Internal Generators
	private class TestModelConfigurationGenerator extends AbstractModelConfigurationGenerator {

		// MARK: Generation
		public TestModelConfigurationGenerator() {
			super(TestContextModelsConfigurationGenerator.this.getAppConfig(),
			        TestContextModelsConfigurationGenerator.this.getOutputProperties());
			this.setMakeImportFiles(false);	// Files are imported in models.xml,
			                               	// instead of created here.
			this.setResultsFolderName(SHARED_MODEL_FILE_NAME);
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

}
