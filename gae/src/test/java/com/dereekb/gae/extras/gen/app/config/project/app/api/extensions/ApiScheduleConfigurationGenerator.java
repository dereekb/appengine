package com.dereekb.gae.extras.gen.app.config.project.app.api.extensions;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.impl.AbstractSingleConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.web.api.server.hook.HookApiScheduleTaskControllerEntry;
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

		builder.comment("Scheduler");
		builder.bean("apiScheduleTaskController").beanClass(ApiScheduleTaskController.class).c()
		        .ref(this.getAppConfig().getAppBeans().getTaskSchedulerId()).ref("apiScheduleTaskControllerEntries");

		builder.comment("Entries");
		SpringBeansXMLMapBuilder<?> map = builder.map("apiScheduleTaskControllerEntries");

		builder.comment("Hooks/Events");
		map.entry("webhooks").valueRef("scheduleWebHookEvent");
		builder.bean("scheduleWebHookEvent").beanClass(HookApiScheduleTaskControllerEntry.class);

		builder.comment("Patches");

		return builder;
	}

}
