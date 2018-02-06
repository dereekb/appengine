package com.dereekb.gae.extras.gen.app.config.project.app.context;

import java.util.List;
import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.impl.AbstractConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractSingleConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLListBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.server.app.model.app.info.impl.AppInfoImpl;
import com.dereekb.gae.server.auth.security.model.roles.impl.CrudModelRole;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.LongModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.StringLongModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.StringModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabase;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskSchedulerAuthenticatorImpl;
import com.dereekb.gae.server.taskqueue.scheduler.impl.TaskSchedulerImpl;
import com.dereekb.gae.utilities.data.StringUtility;

public class ContextServerConfigurationsGenerator extends AbstractConfigurationFileGenerator {

	public static final String SERVER_FILE_NAME = "server";
	public static final String SERVER_FOLDER_NAME = "server";

	private String serverFileName = SERVER_FILE_NAME;
	private String serverFolderName = SERVER_FOLDER_NAME;

	public ContextServerConfigurationsGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
	}

	public ContextServerConfigurationsGenerator(AppConfiguration appConfig) {
		super(appConfig);
	}

	// MARK: AbstractConfigurationFileGenerator
	@Override
	public GenFolder generateConfigurations() {
		GenFolderImpl folder = new GenFolderImpl(this.serverFolderName);

		// Server Files
		folder.addFile(new DatabaseConfigurationGenerator().generateConfigurationFile());
		folder.addFile(new KeysConfigurationGenerator().generateConfigurationFile());
		folder.addFile(new LoginConfigurationGenerator().generateConfigurationFile());
		folder.addFile(new RefConfigurationGenerator().generateConfigurationFile());
		folder.addFile(new TaskQueueConfigurationGenerator().generateConfigurationFile());

		// Main Server File
		folder.addFile(this.makeServerFile(folder));

		return folder;
	}

	public GenFile makeServerFile(GenFolderImpl folder) {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("App Info");
		builder.bean("serverAppInfo").beanClass(AppInfoImpl.class).c()
		        .ref(this.getAppConfig().getAppBeans().getAppKeyBeanId())
		        .ref(this.getAppConfig().getAppBeans().getAppNameBeanId());
		builder.bean("serverAppKey").beanClass(ModelKey.class)
		        .property(this.getAppConfig().getAppBeans().getAppIdBeanId());
		builder.longBean("serverAppId", this.getAppConfig().getAppId());
		builder.stringBean("serverAppName", this.getAppConfig().getAppName());

		builder.comment("Import");
		builder.imp("/model/model.xml");
		builder.imp("/server/server.xml");

		return this.makeFileWithXML(this.serverFileName, builder);
	}

	// MARK: Server Files
	public static final String OBJECTIFY_DATABASE_ENTITIES_KEY = "objectifyDatabaseEntities";

	public class DatabaseConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

		public DatabaseConfigurationGenerator() {
			super(ContextServerConfigurationsGenerator.this);
			this.setFileName("database");
		}

		@Override
		public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
			SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

			builder.bean("objectifyDatabase").beanClass(ObjectifyDatabase.class).primary().lazy(false).c()
			        .ref(OBJECTIFY_DATABASE_ENTITIES_KEY);

			SpringBeansXMLListBuilder<?> entitiesList = builder.list(OBJECTIFY_DATABASE_ENTITIES_KEY);

			for (AppModelConfigurationGroup group : this.getAppConfig().getModelConfigurations()) {
				String groupName = group.getGroupName();
				entitiesList.getRawXMLBuilder().c(groupName);
				List<AppModelConfiguration> modelConfigs = group.getModelConfigurations();

				for (AppModelConfiguration modelConfig : modelConfigs) {
					if (modelConfig.isLocalModel()) {
						entitiesList.ref(modelConfig.getModelObjectifyEntryBeanId());
					}
				}
			}

			return builder;
		}

	}

	public class KeysConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

		public KeysConfigurationGenerator() {
			super(ContextServerConfigurationsGenerator.this);
			this.setFileName("key");
		}

		@Override
		public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
			SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

			builder.comment("Key Converters");
			// ?

			builder.comment("Model Key Converters");
			builder.bean("longModelKeyConverter").beanClass(LongModelKeyConverterImpl.class);
			builder.bean("stringLongModelKeyConverter").beanClass(StringLongModelKeyConverterImpl.class);
			builder.bean("stringModelKeyConverter").beanClass(StringModelKeyConverterImpl.class);

			return builder;
		}

	}

	public class LoginConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

		public LoginConfigurationGenerator() {
			super(ContextServerConfigurationsGenerator.this);
			this.setFileName("login");
		}

		@Override
		public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
			SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

			// TODO: Microservices will eventually need to not be married to the
			// login token secret here. This change can always be made later
			// though...
			if (true) {
				this.addLoginServerLoginTokenBeansToXMLConfigurationFile(builder);
			}

			// Common Components
			this.addCommonLoginTokenBeansToXMLConfigurationFile(builder);

			return builder;
		}

		public void addLoginServerLoginTokenBeansToXMLConfigurationFile(SpringBeansXMLBuilder builder) {

			builder.comment("Login Service");
			builder.stringBean("loginTokenSecret", "");

			// TODO: All internal OAuth services, etc.

		}

		public void addCommonLoginTokenBeansToXMLConfigurationFile(SpringBeansXMLBuilder builder) {

			builder.comment("Login Models Service");

			// TODO

			// Service Entries
			SpringBeansXMLListBuilder<?> entitiesList = builder.list("loginTokenModelContextServiceEntries");

			for (AppModelConfigurationGroup group : this.getAppConfig().getModelConfigurations()) {
				String groupName = group.getGroupName();
				entitiesList.getRawXMLBuilder().c(groupName);

				List<AppModelConfiguration> modelConfigs = group.getModelConfigurations();
				for (AppModelConfiguration modelConfig : modelConfigs) {

					if (modelConfig.isLocalModel()) {
						entitiesList.ref(modelConfig.getModelSecurityContextServiceEntryBeanId());
					}
				}
			}

			builder.alias("loginTokenModelContextServiceEntries", "loginTokenModelContextServiceDencoderEntries");

		}

	}

	public class RefConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

		public RefConfigurationGenerator() {
			super(ContextServerConfigurationsGenerator.this);
			this.setFileName("ref");
		}

		@Override
		public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
			SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

			builder.comment("Security Refs");
			for (CrudModelRole role : CrudModelRole.values()) {
				String roleString = StringUtility.firstLetterUpperCase(role.getRole().toLowerCase());
				builder.bean("crud" + roleString + "ModelRole").beanClass(CrudModelRole.class).factoryMethod("valueOf")
				        .c().value(role.getRole());
			}

			return builder;
		}

	}

	public class TaskQueueConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

		public TaskQueueConfigurationGenerator() {
			super(ContextServerConfigurationsGenerator.this);
			this.setFileName("taskqueue");
		}

		@Override
		public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
			SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

			builder.comment("Task Queue");
			builder.stringBean(this.getAppConfig().getAppBeans().getTaskQueueNameId(),
			        this.getAppConfig().getAppTaskQueueName());

			builder.bean("taskScheduler").beanClass(TaskSchedulerImpl.class).c()
			        .ref(this.getAppConfig().getAppBeans().getTaskQueueNameId()).up().property("authenticator")
			        .ref("taskSchedulerAuthenticator");

			builder.bean("taskSchedulerAuthentication").beanClass(TaskSchedulerAuthenticatorImpl.class).c()
			        .ref(this.getAppConfig().getAppBeans().getSystemLoginTokenFactoryBeanId());

			return builder;
		}

	}

}
