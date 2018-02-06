package com.dereekb.gae.extras.gen.app.config.project.app.api.extensions;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.impl.AbstractSingleConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.web.api.server.schedule.ApiScheduleTaskController;

public class ApiScheduleConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

	public static final String SCHEDULE_FILE_NAME = "schedule";

	public ApiScheduleConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setFileName(SCHEDULE_FILE_NAME);
	}

	// MARK: AbstractConfigurationFileGenerator
	@Override
	public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Schedule Controller");
		builder.bean("apiScheduleTaskController").beanClass(ApiScheduleTaskController.class).c()
		        .ref(this.getAppConfig().getAppBeans().getTaskSchedulerId()).ref("apiScheduleTaskControllerEntries");

		builder.map("apiScheduleTaskControllerEntries").getRawXMLBuilder().c("Add Entries");

		// TODO: Add initialization entries

		return builder;
	}

}
