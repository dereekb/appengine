package com.dereekb.gae.extras.gen.app.config.project.service;

import java.io.IOException;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.web.WebInfConfigurationGenerator;
import com.dereekb.gae.extras.gen.utility.GenFilesWriter;
import com.dereekb.gae.extras.gen.utility.GenFolder;

public abstract class AbstractWebServiceAppConfigurationGen extends AbstractServiceAppConfigurationGen {

	@Override
	protected void writeWebInfConfigurations(AppConfiguration appConfig, GenFilesWriter writer) throws IOException {
		super.writeWebInfConfigurations(appConfig, writer);
		this.writeAppWebConfigurations(appConfig, writer);
	}

	public void writeAppWebConfigurations(AppConfiguration appConfig, GenFilesWriter writer) throws IOException {
		WebInfConfigurationGenerator generator = new WebInfConfigurationGenerator(appConfig);
		GenFolder configurations = generator.generateConfigurations();

		writer.writeFilesInFolder(configurations);
	}

}
