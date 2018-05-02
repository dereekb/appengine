package com.dereekb.gae.extras.gen.app.config.project.app.api.extensions;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractSingleConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.web.api.auth.controller.model.LoginTokenModelContextController;
import com.dereekb.gae.web.api.auth.controller.model.impl.LimitedLoginTokenModelContextControllerDelegateImpl;
import com.dereekb.gae.web.api.auth.controller.model.impl.LoginTokenModelContextControllerDelegateImpl;

public class ApiModelContextConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

	public static final String ROLES_FILE_NAME = "roles";

	public ApiModelContextConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setFileName(ROLES_FILE_NAME);
	}

	// MARK: AbstractConfigurationFileGenerator
	@Override
	public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Model Roles Service");
		String loginTokenModelContextControllerDelegateBeanId = "loginTokenModelContextControllerDelegate";

		builder.bean("loginTokenModelContextController").beanClass(LoginTokenModelContextController.class).c()
		        .ref(loginTokenModelContextControllerDelegateBeanId);

		SpringBeansXMLBeanBuilder<?> delegateBuilder = builder.bean(loginTokenModelContextControllerDelegateBeanId);

		if (this.getAppConfig().isLoginServer()) {
			delegateBuilder.beanClass(LoginTokenModelContextControllerDelegateImpl.class).c()
			        .ref("loginTokenModelContextService").ref("loginTokenModelContextSetDencoder")
			        .ref("loginTokenEncoderDecoder");
		} else {
			delegateBuilder.beanClass(LimitedLoginTokenModelContextControllerDelegateImpl.class).c()
			        .ref("loginTokenModelContextService").ref("loginTokenModelContextSetDencoder");
		}

		return builder;
	}

}
