package com.dereekb.gae.extras.gen.app.config.project.test;

import com.dereekb.gae.client.api.auth.model.impl.ClientModelRolesContextServiceRequestSenderImpl;
import com.dereekb.gae.client.api.auth.system.impl.ClientSystemLoginTokenServiceRequestSenderImpl;
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
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractModelConfigurationGenerator;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.test.app.mock.client.crud.MockClientRequestSender;

/**
 * Generator for unit/integration test client components.
 *
 *
 * @author dereekb
 *
 */
public class TestApiModelsConfigurationGenerator extends AbstractModelConfigurationGenerator {

	public static final String CLIENT_FOLDER_NAME = "api";

	public TestApiModelsConfigurationGenerator(AbstractConfigurationFileGenerator generator) {
		super(generator);
		this.setResultsFolderName(CLIENT_FOLDER_NAME);
	}

	// MARK: Client Configurations
	@Override
	public SpringBeansXMLBuilder makePrimaryFolderImportFileBuilder(GenFolder primary) {
		SpringBeansXMLBuilder builder = super.makePrimaryFolderImportFileBuilder(primary);

		// Base Client-Request Sender
		builder.comment("Base Client Sender");

		builder.bean("mockClientRequestSender").beanClass(MockClientRequestSender.class);

		builder.bean("clientApiRequestSender").beanClass(ClientApiRequestSenderImpl.class).c()
		        .ref("mockClientRequestSender");

		builder.bean("securedClientRequestSender").beanClass(SecuredClientApiRequestSenderImpl.class).c()
		        .ref("clientApiRequestSender").ref("testDefaultClientRequestSecurity").ref("systemLoginTokenFactory");

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

			builder.bean("clientSystemLoginTokenServiceRequestSender")
			        .beanClass(ClientSystemLoginTokenServiceRequestSenderImpl.class).c()
			        .ref("securedClientRequestSender");
		}

		return builder;
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
		        .ref("securedClientRequestSender"); // .ref(modelConfig.getModelRegistryId());

		return builder;
	}

}
