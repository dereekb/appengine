package com.dereekb.gae.test.extras.gen.app;

import java.util.Properties;

import org.junit.Test;

import com.dereekb.gae.client.api.model.crud.builder.impl.ClientCreateRequestSenderImpl;
import com.dereekb.gae.client.api.model.crud.builder.impl.ClientDeleteRequestSenderImpl;
import com.dereekb.gae.client.api.model.crud.builder.impl.ClientReadRequestSenderImpl;
import com.dereekb.gae.client.api.model.crud.builder.impl.ClientUpdateRequestSenderImpl;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.dto.LoginData;
import com.dereekb.gae.test.extras.gen.app.model.AppConfiguration;
import com.dereekb.gae.test.extras.gen.app.model.AppModelConfiguration;
import com.dereekb.gae.test.extras.gen.app.model.impl.AppModelConfigurationImpl;
import com.dereekb.gae.test.extras.gen.utility.GenFile;
import com.dereekb.gae.test.extras.gen.utility.GenFolder;
import com.dereekb.gae.test.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.test.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.test.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;

/**
 * Generator for client components.
 *
 *
 * @author dereekb
 *
 */
public class TestClientConfigurationGenerator {

	private AppConfiguration appConfig;

	public AppConfiguration getAppConfig() {
		return this.appConfig;
	}

	public void setAppConfig(AppConfiguration appConfig) {
		if (appConfig == null) {
			throw new IllegalArgumentException("appConfig cannot be null.");
		}

		this.appConfig = appConfig;
	}

	// MARK: Generation
	public GenFolder generateModelConfigurations() {

		GenFolder modelClientConfigurations = this.makeModelClientConfigurations();

		// GenFolder modelTestConfigurations =
		// this.makeTestClientConfigurations();

		return modelClientConfigurations;
	}

	// MARK: Client Configurations
	private GenFolder makeModelClientConfigurations() {
		GenFolderImpl folder = new GenFolderImpl("client");

		for (AppModelConfiguration modelConfig : this.appConfig.getModelConfigurations()) {
			GenFile file = this.makeModelClientConfiguration(modelConfig);
			folder.addFile(file);
		}

		return folder;
	}

	@Test
	public void test() {
		AppModelConfiguration config = new AppModelConfigurationImpl(Login.class, LoginData.class);

		this.makeModelClientConfiguration(config);
	}

	private GenFile makeModelClientConfiguration(AppModelConfiguration modelConfig) {

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

		// TODO: Complete
		Properties outputProperties = new Properties();

		// Explicitly identify the output as an XML document
		outputProperties.put(javax.xml.transform.OutputKeys.METHOD, "xml");

		// Pretty-print the XML output (doesn't work in all cases)
		outputProperties.put(javax.xml.transform.OutputKeys.INDENT, "yes");

		// Get 2-space indenting when using the Apache transformer
		outputProperties.put("{http://xml.apache.org/xslt}indent-amount", "2");

		// Omit the XML declaration header
		outputProperties.put(
		    javax.xml.transform.OutputKeys.OMIT_XML_DECLARATION, "yes");


		String content = builder.getRawXMLBuilder().asString(outputProperties);

		return null;
	}

}
