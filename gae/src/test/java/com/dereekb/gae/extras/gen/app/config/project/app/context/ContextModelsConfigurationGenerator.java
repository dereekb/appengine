package com.dereekb.gae.extras.gen.app.config.project.app.context;

import java.util.List;
import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.impl.AbstractConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractModelConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractSingleConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.CustomLocalModelContextConfigurer;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanConstructorBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLListBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.model.crud.task.impl.task.ScheduleCreateReviewTask;
import com.dereekb.gae.model.crud.task.impl.task.ScheduleUpdateReviewTask;
import com.dereekb.gae.model.extension.data.conversion.impl.TypedBidirectionalConverterImpl;
import com.dereekb.gae.model.extension.links.service.impl.LinkServiceImpl;
import com.dereekb.gae.model.extension.links.system.modification.utility.LinkModificationSystemBuilder;
import com.dereekb.gae.model.extension.search.query.service.impl.ModelQueryServiceImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.TypeModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.objectify.core.impl.ObjectifyDatabaseEntityDefinitionImpl;
import com.dereekb.gae.server.datastore.objectify.query.impl.TaskedObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.task.impl.IterableSetterTaskImpl;
import com.dereekb.gae.server.datastore.utility.impl.TaskConfiguredUpdaterImpl;
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
		folder.addFolder(this.makeModelsExtensionFolder());

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
		SpringBeansXMLMapBuilder<?> modelKeyTypeMap = builder
		        .bean(this.getAppConfig().getAppBeans().getModelKeyTypeConverterId())
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
	public GenFolderImpl makeModelsExtensionFolder() {
		GenFolderImpl folder = new GenFolderImpl("extension");

		folder.addFile(this.makeModelsLinkExtensionFile());

		return folder;
	}

	public GenFile makeModelsLinkExtensionFile() {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		String linkSystemEntriesId = "linkSystemEntries";
		String linkModificationSystemId = "linkModificationSystem";
		String linkModificationSystemBuilderId = "linkModificationSystemBuilder";

		builder.comment("Service");
		builder.bean(this.getAppConfig().getAppBeans().getLinkServiceId()).beanClass(LinkServiceImpl.class).c()
		        .ref(linkModificationSystemId);

		builder.comment("System");
		builder.bean(linkModificationSystemId).lazy(false).factoryBean(linkModificationSystemBuilderId)
		        .factoryMethod("makeLinkModifictionSystem");

		/*
		 * builder.bean("linkSystem").lazy(false).factoryBean(
		 * linkModificationSystemBuilderId)
		 * .factoryMethod("makeLinkSystem");
		 */

		builder.bean(linkModificationSystemBuilderId).beanClass(LinkModificationSystemBuilder.class).c()
		        .ref(linkSystemEntriesId);

		builder.comment("Entries");
		SpringBeansXMLListBuilder<?> entriesListBuilder = builder.list(linkSystemEntriesId);

		List<AppModelConfiguration> localModelConfigs = this.getAllLocalConfigurations();
		for (AppModelConfiguration modelConfig : localModelConfigs) {
			entriesListBuilder.ref(modelConfig.getModelLinkSystemBuilderEntryBeanId());
		}

		builder.comment("Accessors");
		for (AppModelConfiguration modelConfig : localModelConfigs) {
			builder.bean(modelConfig.getModelLinkModelAccessorId()).factoryBean(linkModificationSystemBuilderId)
			        .factoryMethod("getAccessorForType").c().ref(modelConfig.getModelTypeBeanId());
		}

		return this.makeFileWithXML("link", builder);
	}

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

		// Entry
		builder.bean(modelConfig.getModelObjectifyEntryBeanId()).beanClass(ObjectifyDatabaseEntityDefinitionImpl.class)
		        .c().ref(modelConfig.getModelTypeBeanId()).ref(modelConfig.getModelClassBeanId())
		        .ref(modelConfig.getModelIdTypeBeanId()).ref(modelConfig.getModelQueryInitializerBeanId());

		// Registry
		builder.bean(modelConfig.getModelRegistryId())
		        .factoryBean(getAppConfig().getAppBeans().getObjectifyDatabaseId()).factoryMethod("makeRegistry").c()
		        .ref(modelConfig.getModelClassBeanId());

		builder.alias(modelConfig.getModelRegistryId(), modelConfig.getModelKeyListAccessorFactoryId());

		builder.comment("Configured Setters");
		builder.alias(modelConfig.getModelRegistryId(), modelConfig.getModelStorerBeanId());
		builder.alias(modelConfig.getModelRegistryId(), modelConfig.getModelUpdaterBeanId());
		builder.alias(modelConfig.getModelRegistryId(), modelConfig.getModelDeleterBeanId());

		builder.comment("Configured Create");
		builder.bean(modelConfig.getModelScheduleCreateReviewBeanId()).beanClass(ScheduleCreateReviewTask.class).c()
		        .ref(modelConfig.getModelTypeBeanId()).ref(getAppConfig().getAppBeans().getTaskSchedulerId());

		builder.comment("Configured Updater");
		builder.bean(modelConfig.getModelConfiguredUpdaterBeanId()).beanClass(TaskConfiguredUpdaterImpl.class).c()
		        .ref(modelConfig.getModelScheduleUpdateReviewBeanId()).ref(modelConfig.getModelUpdaterBeanId());
		builder.bean(modelConfig.getModelScheduleUpdateReviewBeanId()).beanClass(ScheduleUpdateReviewTask.class).c()
		        .ref(modelConfig.getModelTypeBeanId()).ref(getAppConfig().getAppBeans().getTaskSchedulerId());

		builder.comment("Setter Task");
		builder.bean(modelConfig.getModelSetterTaskBeanId()).beanClass(IterableSetterTaskImpl.class).c()
		        .ref(modelConfig.getModelRegistryId());

		builder.alias(modelConfig.getModelSetterTaskBeanId(), modelConfig.getModelBeanPrefix() + "StorerTask");
		builder.alias(modelConfig.getModelSetterTaskBeanId(), modelConfig.getModelBeanPrefix() + "UpdaterTask");
		builder.alias(modelConfig.getModelSetterTaskBeanId(), modelConfig.getModelBeanPrefix() + "DeleterTask");

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
			this.getModelConfig().getCustomLocalModelContextConfigurer()
			        .configureCrudServiceComponents(this.getAppConfig(), this.modelConfig, builder);
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
			        .ref(this.modelConfig.getModelTypeBeanId()).ref(this.modelConfig.getModelBeanPrefix() + "Class")
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
			        .ref(this.modelConfig.getModelCrudServiceId()).ref(this.modelConfig.getModelUpdaterBeanId())
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

			SpringBeansXMLBeanConstructorBuilder<?> securedLoginQueryInitializer = builder
			        .bean(securedQueryInitializerId)
			        .beanClass(TaskedObjectifyQueryRequestLimitedBuilderInitializer.class).c().ref(queryInitializerId);

			CustomLocalModelContextConfigurer configuration = this.modelConfig.getCustomLocalModelContextConfigurer();
			configuration.configureSecuredQueryInitializer(this.getAppConfig(), this.modelConfig,
			        securedLoginQueryInitializer);

			builder.bean(queryInitializerId).beanClass(this.modelConfig.getModelQueryInitializerClass());

			builder.comment("Security");
			if (this.modelConfig.getModelOwnedModelQuerySecurityDelegateClass() != null) {
				builder.comment("Owned Model");

				String securityDelegateBeanId = this.modelConfig.getModelBeanPrefix()
				        + "OwnedModelQuerySecurityDelegate";

				builder.bean(securityDelegateBeanId)
				        .beanClass(this.modelConfig.getModelOwnedModelQuerySecurityDelegateClass()).c()
				        .ref(this.getAppConfig().getAppBeans().getAnonymousModelRoleSetContextServiceBeanId());

				builder.bean("optional" + StringUtility.firstLetterUpperCase(securityDelegateBeanId))
				        .beanClass(this.modelConfig.getModelOwnedModelQuerySecurityDelegateClass()).c()
				        .ref(this.getAppConfig().getAppBeans().getAnonymousModelRoleSetContextServiceBeanId()).up()
				        .property("optional").value("true");
			}

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

			String modelRoleSetLoader = this.modelConfig.getModelRoleSetLoaderBeanId();

			CustomLocalModelContextConfigurer customConfigurer = this.getModelConfig()
			        .getCustomLocalModelContextConfigurer();
			customConfigurer.configureModelRoleSetLoaderComponents(this.getAppConfig(), this.modelConfig, builder);

			builder.bean(this.modelConfig.getModelSecurityContextServiceEntryBeanId())
			        .beanClass(this.modelConfig.getModelSecurityContextServiceEntryClass()).c()
			        .ref(this.modelConfig.getModelRegistryId()).ref(modelRoleSetLoader);

			customConfigurer.configureModelChildrenRoleComponents(this.getAppConfig(), this.modelConfig, builder);
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
