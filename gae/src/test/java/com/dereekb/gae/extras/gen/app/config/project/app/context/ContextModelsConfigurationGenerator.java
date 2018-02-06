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
import com.dereekb.gae.model.extension.data.conversion.impl.TypedBidirectionalConverterImpl;
import com.dereekb.gae.model.extension.search.query.service.impl.ModelQueryServiceImpl;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.ModelRoleSetLoaderImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.TypeModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.objectify.query.impl.TaskedObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.factory.impl.BasicFactory;
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

	public class DataExtensionConfigurationGenerator extends AbstractSingleModelConfigurationFileGenerator {

		public DataExtensionConfigurationGenerator(AppModelConfiguration modelConfig) {
			super(modelConfig);
			this.setFileName("data");
		}

		@Override
		public void makeXMLConfigurationFile(SpringBeansXMLBuilder builder) {

			builder.comment("Dto");

			String dataBuilderId = this.modelConfig.getModelBeanPrefix() + "DataBuilder";
			String dataReaderId = this.modelConfig.getModelBeanPrefix() + "DataReader";

			builder.bean(dataBuilderId).beanClass(this.modelConfig.getModelDataBuilderClass());
			builder.bean(dataReaderId).beanClass(this.modelConfig.getModelDataReaderClass());
			builder.bean(this.modelConfig.getModelBeanPrefix() + "DataConverter")
			        .beanClass(TypedBidirectionalConverterImpl.class).c().ref(dataBuilderId).ref(dataReaderId)
			        .ref(this.modelConfig.getModelIdTypeBeanId()).ref(this.modelConfig.getModelBeanPrefix() + "Class")
			        .ref(this.modelConfig.getModelBeanPrefix() + "DtoClass");

		}

	}

	public class LinkExtensionConfigurationGenerator extends AbstractSingleModelConfigurationFileGenerator {

		public LinkExtensionConfigurationGenerator(AppModelConfiguration modelConfig) {
			super(modelConfig);
			this.setFileName("link");
		}

		@Override
		public void makeXMLConfigurationFile(SpringBeansXMLBuilder builder) {

			builder.bean(this.modelConfig.getModelLinkSystemBuilderEntryBeanId())
			        .beanClass(this.modelConfig.getModelLinkSystemBuilderEntryClass()).c()
			        .ref(this.modelConfig.getModelCrudServiceId())
			        .ref(this.modelConfig.getModelUpdaterBeanId())
			        .ref(this.modelConfig.getModelScheduleUpdateReviewBeanId());

		}

	}

	public class GenerationExtensionConfigurationGenerator extends AbstractSingleModelConfigurationFileGenerator {

		public GenerationExtensionConfigurationGenerator(AppModelConfiguration modelConfig) {
			super(modelConfig);
			this.setFileName("generation");
		}

		@Override
		public void makeXMLConfigurationFile(SpringBeansXMLBuilder builder) {

			builder.comment("Factory");
			builder.bean(this.modelConfig.getNewModelFactoryBeanId()).beanClass(BasicFactory.class).c()
			        .ref(this.modelConfig.getModelClassBeanId());

		}

	}

	public class SearchExtensionConfigurationGenerator extends AbstractSingleModelConfigurationFileGenerator {

		public SearchExtensionConfigurationGenerator(AppModelConfiguration modelConfig) {
			super(modelConfig);
			this.setFileName("search");
		}

		@Override
		public void makeXMLConfigurationFile(SpringBeansXMLBuilder builder) {

			builder.comment("Query Services");

			String queryInitializerId = this.modelConfig.getModelBeanPrefix() + "QueryInitializer";
			String securedQueryInitializerId = "secured" + StringUtility.firstLetterUpperCase(queryInitializerId);

			builder.bean(this.modelConfig.getModelQueryServiceId()).beanClass(ModelQueryServiceImpl.class).c()
			        .ref(this.modelConfig.getModelRegistryId()).ref(securedQueryInitializerId);

			builder.bean(securedQueryInitializerId)
			        .beanClass(TaskedObjectifyQueryRequestLimitedBuilderInitializer.class).c().ref(queryInitializerId)
			        .nextArgBuilder().comment("TODO: Complete Configuration");

			builder.bean(queryInitializerId).beanClass(this.modelConfig.getModelQueryInitializerClass());

			builder.comment("Security");

		}

	}

	public class SecurityExtensionConfigurationGenerator extends AbstractSingleModelConfigurationFileGenerator {

		public SecurityExtensionConfigurationGenerator(AppModelConfiguration modelConfig) {
			super(modelConfig);
			this.setFileName("security");
		}

		@Override
		public void makeXMLConfigurationFile(SpringBeansXMLBuilder builder) {

			builder.comment("Role Builder Components");
			builder.comment("TODO: Complete Adding Role Builders");

			String modelRoleSetLoader = this.modelConfig.getModelRoleSetLoaderBeanId();

			builder.bean(modelRoleSetLoader).beanClass(ModelRoleSetLoaderImpl.class).c().getRawXMLBuilder().e("array")
			        .comment("TODO: Add Component Refs Here");

			builder.bean(this.modelConfig.getModelSecurityContextServiceEntryBeanId())
			        .beanClass(this.modelConfig.getModelSecurityContextServiceEntryClass()).c()
			        .ref(this.modelConfig.getModelRegistryId()).ref(modelRoleSetLoader);

			builder.comment("Children");
			builder.comment("TODO: Complete if applicable.");

		}

	}

	// MARK: Model Extensions
	public GenFolderImpl makeModelExtensionsConfigurationsFolder(AppModelConfiguration modelConfig) {
		GenFolderImpl folder = new GenFolderImpl("extensions");

		folder.addFile(new DataExtensionConfigurationGenerator(modelConfig).generateConfigurationFile());
		folder.addFile(new GenerationExtensionConfigurationGenerator(modelConfig).generateConfigurationFile());
		folder.addFile(new LinkExtensionConfigurationGenerator(modelConfig).generateConfigurationFile());
		folder.addFile(new SearchExtensionConfigurationGenerator(modelConfig).generateConfigurationFile());
		folder.addFile(new SecurityExtensionConfigurationGenerator(modelConfig).generateConfigurationFile());

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
