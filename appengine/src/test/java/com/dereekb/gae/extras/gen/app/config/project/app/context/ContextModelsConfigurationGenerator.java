package com.dereekb.gae.extras.gen.app.config.project.app.context;

import java.util.List;
import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.filter.NotInternalModelConfigurationFilter;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractModelConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractSingleConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelContextConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.context.shared.AppModelBeansConfigurationWriterUtility;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLListBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.model.crud.task.impl.delete.ScheduleDeleteTask;
import com.dereekb.gae.model.crud.task.impl.task.ScheduleCreateReviewTask;
import com.dereekb.gae.model.crud.task.impl.task.ScheduleUpdateReviewTask;
import com.dereekb.gae.model.extension.links.service.impl.LinkServiceImpl;
import com.dereekb.gae.model.extension.links.system.modification.utility.LinkModificationSystemBuilder;
import com.dereekb.gae.model.extension.search.query.service.impl.ModelQueryServiceImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.TypeModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityKeyEnforcement;
import com.dereekb.gae.server.datastore.objectify.core.impl.ObjectifyDatabaseEntityDefinitionImpl;
import com.dereekb.gae.server.datastore.objectify.query.impl.TaskedObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.task.impl.IterableSetterTaskImpl;
import com.dereekb.gae.server.datastore.utility.impl.TaskConfiguredUpdaterImpl;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.factory.impl.BasicFactoryImpl;
import com.dereekb.gae.utilities.misc.path.PathUtility;

/**
 * {@link AbstractModelConfigurationGenerator} implementation for local model
 * configurations.
 *
 * @author dereekb
 *
 */
public class ContextModelsConfigurationGenerator extends AbstractModelConfigurationGenerator {

	public static final String MODELS_FOLDER_NAME = "model";
	public static final String MODELS_FILE_NAME = "model";

	public ContextModelsConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setLocalModelResultsFolderName(MODELS_FILE_NAME);
		this.setIgnoreRemote(true);
		this.setSplitByGroup(true);
		this.setSplitByModel(true);
		this.setIgnoreInternalOnly(false);
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

		for (LocalModelConfigurationGroup group : this.getAppConfig().getLocalModelConfigurations()) {
			String groupFolderPath = group.getGroupName().toLowerCase();

			List<LocalModelConfiguration> modelConfigs = group.getModelConfigurations();
			for (LocalModelConfiguration modelConfig : modelConfigs) {
				String modelName = modelConfig.getModelType().toLowerCase();
				String modelPath = PathUtility.buildPath(groupFolderPath, modelName, modelName + ".xml");
				builder.imp(modelPath);
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

			List<? extends AppModelConfiguration> modelConfigs = group.getModelConfigurations();
			for (AppModelConfiguration modelConfig : modelConfigs) {
				modelKeyTypeMap.keyRefValueRefEntry(modelConfig.getModelTypeBeanId(),
				        modelConfig.getModelIdTypeBeanId());
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

		List<LocalModelConfiguration> nonInternalLocalModelConfigs = this
		        .getAllLocalConfigurations(NotInternalModelConfigurationFilter.make());
		for (LocalModelConfiguration modelConfig : nonInternalLocalModelConfigs) {
			entriesListBuilder.ref(modelConfig.getModelLinkSystemBuilderEntryBeanId());
		}

		builder.comment("Accessors");
		for (LocalModelConfiguration modelConfig : nonInternalLocalModelConfigs) {
			builder.bean(modelConfig.getModelLinkModelAccessorId()).factoryBean(linkModificationSystemBuilderId)
			        .factoryMethod("getAccessorForType").c().ref(modelConfig.getModelTypeBeanId());
		}

		return this.makeFileWithXML("link", builder);
	}

	// MARK: Model
	@Override
	public GenFolderImpl makeModelClientConfiguration(LocalModelConfiguration modelConfig) {
		GenFolderImpl folder = super.makeModelClientConfiguration(modelConfig);

		// Crud
		folder.addFile(new CrudConfigurationGenerator(modelConfig).generateConfigurationFile());

		// Extensions
		folder.addFolder(this.makeModelExtensionsConfigurationsFolder(modelConfig));

		return folder;
	}

	@Override
	public SpringBeansXMLBuilder makeXMLModelClientConfigurationFile(LocalModelConfiguration modelConfig)
	        throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		new AppModelBeansConfigurationWriterUtility(modelConfig).insertModelTypeInformation(builder);

		builder.comment("Database");

		// Query Initializer
		builder.bean(modelConfig.getModelQueryInitializerBeanId())
		        .beanClass(modelConfig.getModelQueryInitializerClass());

		// Entry
		SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> entryBeanBuilder = builder
		        .bean(modelConfig.getModelObjectifyEntryBeanId()).beanClass(ObjectifyDatabaseEntityDefinitionImpl.class)
		        .c().ref(modelConfig.getModelTypeBeanId()).ref(modelConfig.getModelClassBeanId())
		        .ref(modelConfig.getModelIdTypeBeanId()).ref(modelConfig.getModelQueryInitializerBeanId()).up();

		ObjectifyDatabaseEntityKeyEnforcement keyEnforcement = modelConfig.getKeyEnforcement();

		if (keyEnforcement != ObjectifyDatabaseEntityKeyEnforcement.DEFAULT) {
			// Set key enforcement if necessary
			entryBeanBuilder.property("keyEnforcement").value(keyEnforcement.toString());
		}

		// Registry
		builder.bean(modelConfig.getModelRegistryId())
		        .factoryBean(getAppConfig().getAppBeans().getObjectifyDatabaseId()).factoryMethod("makeRegistry").c()
		        .ref(modelConfig.getModelClassBeanId());

		builder.alias(modelConfig.getModelRegistryId(), modelConfig.getModelKeyListAccessorFactoryId());

		builder.comment("Configured Aliases");
		builder.alias(modelConfig.getModelRegistryId(), modelConfig.getModelGetterBeanId());

		// Still available if internal model
		builder.alias(modelConfig.getModelRegistryId(), modelConfig.getModelStorerBeanId());
		builder.alias(modelConfig.getModelRegistryId(), modelConfig.getModelUpdaterBeanId());
		builder.alias(modelConfig.getModelRegistryId(), modelConfig.getModelDeleterBeanId());

		if (modelConfig.isInternalModelOnly() == false) {

			// TODO: Update configuration if internal model is not read-only.

			builder.comment("Configured Create");
			builder.bean(modelConfig.getModelScheduleCreateReviewBeanId()).beanClass(ScheduleCreateReviewTask.class).c()
			        .ref(modelConfig.getModelTypeBeanId()).ref(getAppConfig().getAppBeans().getTaskSchedulerId());

			builder.comment("Configured Updater");
			builder.bean(modelConfig.getModelConfiguredUpdaterBeanId()).beanClass(TaskConfiguredUpdaterImpl.class).c()
			        .ref(modelConfig.getModelScheduleUpdateReviewBeanId()).ref(modelConfig.getModelUpdaterBeanId());
			builder.bean(modelConfig.getModelScheduleUpdateReviewBeanId()).beanClass(ScheduleUpdateReviewTask.class).c()
			        .ref(modelConfig.getModelTypeBeanId()).ref(getAppConfig().getAppBeans().getTaskSchedulerId());

			builder.comment("Delete Task");

			// TODO: Add configuration to not add the schedule delete task.

			builder.bean(modelConfig.getModelScheduleDeleteBeanId()).beanClass(ScheduleDeleteTask.class).c()
			        .ref(modelConfig.getModelTypeBeanId())
			        .ref(this.getAppConfig().getAppBeans().getTaskSchedulerId());

			builder.comment("Setter Task");
			builder.bean(modelConfig.getModelSetterTaskBeanId()).beanClass(IterableSetterTaskImpl.class).c()
			        .ref(modelConfig.getModelRegistryId());

			builder.alias(modelConfig.getModelSetterTaskBeanId(), modelConfig.getModelBeanPrefix() + "StorerTask");
			builder.alias(modelConfig.getModelSetterTaskBeanId(), modelConfig.getModelBeanPrefix() + "UpdaterTask");
			builder.alias(modelConfig.getModelSetterTaskBeanId(), modelConfig.getModelBeanPrefix() + "DeleterTask");

			builder.comment("Import");
			builder.imp("/extensions/data.xml");
			builder.imp("/extensions/link.xml");
			builder.imp("/extensions/generation.xml");
			builder.imp("/extensions/search.xml");
		} else {
			builder.comment("Import");
		}

		// Crud and Security always generated
		builder.imp("/crud.xml");
		builder.imp("/extensions/security.xml");

		return builder;
	}

	public class CrudConfigurationGenerator extends AbstractSingleModelConfigurationFileGenerator {

		public CrudConfigurationGenerator(LocalModelConfiguration modelConfig) {
			super(modelConfig);
			this.setFileName("crud");
		}

		@Override
		public void makeXMLConfigurationFile(SpringBeansXMLBuilder builder) {
			this.getModelConfig().getCustomModelContextConfigurer().configureCrudServiceComponents(this.getAppConfig(),
			        this.modelConfig, builder);
		}

	}

	public class DataExtensionConfigurationGenerator extends AbstractSingleModelConfigurationFileGenerator {

		public DataExtensionConfigurationGenerator(LocalModelConfiguration modelConfig) {
			super(modelConfig);
			this.setFileName("data");
		}

		@Override
		public void makeXMLConfigurationFile(SpringBeansXMLBuilder builder) {
			new AppModelBeansConfigurationWriterUtility(this.modelConfig).insertDataConversionBeans(builder);
		}

	}

	public class LinkExtensionConfigurationGenerator extends AbstractSingleModelConfigurationFileGenerator {

		public LinkExtensionConfigurationGenerator(LocalModelConfiguration modelConfig) {
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

		public GenerationExtensionConfigurationGenerator(LocalModelConfiguration modelConfig) {
			super(modelConfig);
			this.setFileName("generation");
		}

		@Override
		public void makeXMLConfigurationFile(SpringBeansXMLBuilder builder) {

			builder.comment("Factory");
			builder.bean(this.modelConfig.getNewModelFactoryBeanId()).beanClass(BasicFactoryImpl.class).c()
			        .ref(this.modelConfig.getModelClassBeanId());

		}

	}

	public class SearchExtensionConfigurationGenerator extends AbstractSingleModelConfigurationFileGenerator {

		public SearchExtensionConfigurationGenerator(LocalModelConfiguration modelConfig) {
			super(modelConfig);
			this.setFileName("search");
		}

		@Override
		public void makeXMLConfigurationFile(SpringBeansXMLBuilder builder) {

			builder.comment("Query Services");

			String queryInitializerId = this.modelConfig.getModelQueryInitializerBeanId();
			String securedQueryInitializerId = this.modelConfig.getSecuredModelQueryInitializerBeanId();
			String securedQueryInitializerDelegateId = this.modelConfig.getSecuredModelQueryInitializerDelegateBeanId();

			builder.bean(this.modelConfig.getModelQueryServiceId()).beanClass(ModelQueryServiceImpl.class).c()
			        .ref(this.modelConfig.getModelRegistryId()).ref(securedQueryInitializerId);

			builder.bean(securedQueryInitializerId)
			        .beanClass(TaskedObjectifyQueryRequestLimitedBuilderInitializer.class).c().ref(queryInitializerId)
			        .ref(securedQueryInitializerDelegateId);

			LocalModelContextConfigurer configuration = this.modelConfig.getCustomModelContextConfigurer();
			configuration.configureSecuredQueryInitializer(this.getAppConfig(), this.modelConfig,
			        securedQueryInitializerDelegateId, builder);

			if (configuration.hasSearchComponents()) {
				builder.comment("Search");
				configuration.configureSearchComponents(this.getAppConfig(), this.modelConfig, builder);
			}

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

		public SecurityExtensionConfigurationGenerator(LocalModelConfiguration modelConfig) {
			super(modelConfig);
			this.setFileName("security");
		}

		@Override
		public void makeXMLConfigurationFile(SpringBeansXMLBuilder builder) {

			builder.comment("Role Builder Components");

			String modelRoleSetLoader = this.modelConfig.getModelRoleSetLoaderBeanId();

			LocalModelContextConfigurer customConfigurer = this.getModelConfig().getCustomModelContextConfigurer();
			customConfigurer.configureModelRoleSetLoaderComponents(this.getAppConfig(), this.modelConfig, builder);

			builder.bean(this.modelConfig.getModelSecurityContextServiceEntryBeanId())
			        .beanClass(this.modelConfig.getModelSecurityContextServiceEntryClass()).c()
			        .ref(this.modelConfig.getModelRegistryId()).ref(modelRoleSetLoader);

			customConfigurer.configureModelChildrenRoleComponents(this.getAppConfig(), this.modelConfig, builder);
		}

	}

	// MARK: Model Extensions
	public GenFolderImpl makeModelExtensionsConfigurationsFolder(LocalModelConfiguration modelConfig) {
		GenFolderImpl folder = new GenFolderImpl("extensions");

		if (modelConfig.isInternalModelOnly() == false) {
			folder.addFile(new DataExtensionConfigurationGenerator(modelConfig).generateConfigurationFile());
			folder.addFile(new GenerationExtensionConfigurationGenerator(modelConfig).generateConfigurationFile());
			folder.addFile(new LinkExtensionConfigurationGenerator(modelConfig).generateConfigurationFile());
			folder.addFile(new SearchExtensionConfigurationGenerator(modelConfig).generateConfigurationFile());
		}

		// Always add security
		folder.addFile(new SecurityExtensionConfigurationGenerator(modelConfig).generateConfigurationFile());

		return folder;
	}

	public abstract class AbstractSingleModelConfigurationFileGenerator extends AbstractSingleConfigurationFileGenerator {

		protected final LocalModelConfiguration modelConfig;

		public LocalModelConfiguration getModelConfig() {
			return this.modelConfig;
		}

		public AbstractSingleModelConfigurationFileGenerator(LocalModelConfiguration modelConfig) {
			this(modelConfig, ContextModelsConfigurationGenerator.this);
		}

		public AbstractSingleModelConfigurationFileGenerator(LocalModelConfiguration modelConfig,
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
