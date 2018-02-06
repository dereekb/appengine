package com.dereekb.gae.extras.gen.app.config.project.app.context;

import java.util.List;
import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.impl.AbstractConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractModelConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractSingleConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.TypeModelKeyConverterImpl;
import com.dereekb.gae.utilities.misc.path.PathUtility;

public class ContextModelsConfigurationGenerator extends AbstractModelConfigurationGenerator {

	public static final String MODELS_FOLDER_NAME = "model";
	public static final String MODELS_FILE_NAME = "model";

	public ContextModelsConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setResultsFolderName(MODELS_FILE_NAME);
		this.setIgnoreRemote(true);
		this.setSplitByGroup(true);
		this.setSplitByModel(true);
	}

	// MARK: AbstractModelConfigurationGenerator
	@Override
	public GenFolderImpl generateConfigurations() {
		GenFolderImpl folder = super.generateConfigurations();

		// Model
		folder.addFile(this.makeModelsXmlFile(folder));

		// Extensions

		return folder;
	}

	public GenFile makeModelsXmlFile(GenFolderImpl folder) {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Imports");
		builder.comment("Models");

		for (AppModelConfigurationGroup group : this.getAppConfig().getModelConfigurations()) {
			String groupFolderPath = group.getGroupName().toLowerCase();

			List<AppModelConfiguration> modelConfigs = group.getModelConfigurations();
			for (AppModelConfiguration modelConfig : modelConfigs) {
				if (modelConfig.isLocalModel()) {
					String modelName = modelConfig.getModelType().toLowerCase();
					String modelPath = PathUtility.buildPath(groupFolderPath, modelName, modelName + ".xml");
					builder.imp(modelPath);
				}
			}
		}

		builder.comment("Extensions");
		builder.imp("/extension/link.xml");

		builder.comment("Shared");
		SpringBeansXMLMapBuilder<?> modelKeyTypeMap = builder.bean("modelKeyTypeConverter")
		        .beanClass(TypeModelKeyConverterImpl.class).c().map().valueType(ModelKeyType.class);

		for (AppModelConfigurationGroup group : this.getAppConfig().getModelConfigurations()) {
			modelKeyTypeMap.getRawXMLBuilder().comment(group.getGroupName().toUpperCase());

			List<AppModelConfiguration> modelConfigs = group.getModelConfigurations();
			for (AppModelConfiguration modelConfig : modelConfigs) {
				if (modelConfig.isLocalModel()) {
					modelKeyTypeMap.keyRefValueRefEntry(modelConfig.getModelTypeBeanId(),
					        modelConfig.getModelIdTypeBeanId());
				}
			}
		}

		return this.makeFileWithXML(MODELS_FILE_NAME, builder);
	}

	// MARK: Extensions

	// MARK: Model
	@Override
	public GenFolderImpl makeModelClientConfiguration(AppModelConfiguration modelConfig) {
		GenFolderImpl folder = super.makeModelClientConfiguration(modelConfig);

		// Crud
		folder.addFile(new CrudConfigurationGenerator(modelConfig).generateConfigurationFile());

		// Extensions
		folder.addFolder(this.makeModelExtensionsConfigurationsFolder(modelConfig));

		return folder;
	}

	@Override
	public SpringBeansXMLBuilder makeXMLModelClientConfigurationFile(AppModelConfiguration modelConfig)
	        throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Type");
		builder.stringBean(modelConfig.getModelTypeBeanId(), modelConfig.getModelType());
		builder.bean(modelConfig.getModelClassBeanId()).beanClass(Class.class).factoryMethod("forName").c()
		        .value(modelConfig.getModelClass().getCanonicalName());
		builder.bean(modelConfig.getModelDtoClassBeanId()).beanClass(Class.class).factoryMethod("forName").c()
		        .value(modelConfig.getModelDataClass().getCanonicalName());
		builder.bean(modelConfig.getModelIdTypeBeanId()).beanClass(ModelKeyType.class).factoryMethod("valueOf").c()
		        .value(modelConfig.getModelKeyType().name());

		builder.comment("Database");
		// TODO

		builder.comment("Import");
		builder.imp("/crud.xml");
		builder.imp("/extensions/data.xml");
		builder.imp("/extensions/link.xml");
		builder.imp("/extensions/generation.xml");
		builder.imp("/extensions/search.xml");
		builder.imp("/extensions/security.xml");

		return builder;
	}

	public class CrudConfigurationGenerator extends AbstractSingleModelConfigurationFileGenerator {

		public CrudConfigurationGenerator(AppModelConfiguration modelConfig) {
			super(modelConfig);
			this.setFileName("crud");
		}

		@Override
		public void makeXMLConfigurationFile(SpringBeansXMLBuilder builder) {

		}

	}

	// MARK: Model Extensions
	public GenFolderImpl makeModelExtensionsConfigurationsFolder(AppModelConfiguration modelConfig) {
		GenFolderImpl folder = new GenFolderImpl("extensions");

		// TODO: Data, Generation, Link, Search, Security

		return folder;
	}

	public abstract class AbstractSingleModelConfigurationFileGenerator extends AbstractSingleConfigurationFileGenerator {

		protected final AppModelConfiguration modelConfig;

		public AppModelConfiguration getModelConfig() {
			return this.modelConfig;
		}

		public AbstractSingleModelConfigurationFileGenerator(AppModelConfiguration modelConfig) {
			this(modelConfig, ContextModelsConfigurationGenerator.this);
		}

		public AbstractSingleModelConfigurationFileGenerator(AppModelConfiguration modelConfig,
		        AbstractConfigurationFileGenerator generator) {
			super(generator);
			this.modelConfig = modelConfig;
		}

		// MARK: Override
		@Override
		public final SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
			SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();
			this.makeXMLConfigurationFile(builder);
			return builder;
		}

		public abstract void makeXMLConfigurationFile(SpringBeansXMLBuilder builder);

	}

}
