package com.dereekb.gae.extras.gen.app.config.project.app.api.extensions;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractSingleConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.web.api.auth.controller.model.roles.ModelRolesController;
import com.dereekb.gae.web.api.auth.controller.model.roles.impl.ModelRolesControllerDelegateImpl;

/**
 * Sets up the API model context security.
 *
 * @author dereekb
 *
 */
public class ApiModelContextConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

	public static final String ROLES_FILE_NAME = "roles";

	public ApiModelContextConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setFileName(ROLES_FILE_NAME);
	}

	// MARK: AbstractConfigurationFileGenerator
	@Override
	public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
		AppConfiguration appConfig = this.getAppConfig();

		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Model Roles Controller");
		builder.bean("modelRolesController").beanClass(ModelRolesController.class).c()
			.bean().beanClass(ModelRolesControllerDelegateImpl.class)
			.c()
			.ref(appConfig.getAppBeans().getLoginTokenModelContextServiceBeanId())
			.ref(appConfig.getAppBeans().getLoginTokenModelContextSetDencoderBeanId());

		/*
		// DEPRECATED
		if (this.getAppConfig().isLoginServer()) {
			builder.bean("loginTokenModelContextController").beanClass(LoginTokenModelContextController.class).c()
			        .ref(loginTokenModelContextControllerDelegateBeanId);

			SpringBeansXMLBeanBuilder<?> delegateBuilder = builder.bean(loginTokenModelContextControllerDelegateBeanId);

			delegateBuilder.beanClass(LoginTokenModelContextControllerDelegateImpl.class).c()
			        .ref("loginTokenModelContextService").ref("loginTokenModelContextSetDencoder")
			        .ref("loginTokenEncoderDecoder");
		} else {
			delegateBuilder.beanClass(LimitedLoginTokenModelContextControllerDelegateImpl.class).c()
			        .ref("loginTokenModelContextService").ref("loginTokenModelContextSetDencoder");
		}
		*/

		return builder;
	}

}
