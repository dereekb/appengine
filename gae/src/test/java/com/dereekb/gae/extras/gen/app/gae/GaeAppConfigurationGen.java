package com.dereekb.gae.extras.gen.app.gae;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.model.impl.AppConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.model.impl.AppModelConfigurationGroupImpl;
import com.dereekb.gae.extras.gen.app.config.model.impl.AppModelConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.project.test.TestModelsConfigurationGenerator;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFilesWriterImpl;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.utilities.collections.list.ListUtility;

public class GaeAppConfigurationGen {

	public static AppConfiguration makeGaeAppConfiguration() {

		// Login
		AppModelConfigurationImpl loginModel = new AppModelConfigurationImpl(Login.class);
		AppModelConfigurationImpl loginPointerModel = new AppModelConfigurationImpl(LoginPointer.class);
		AppModelConfigurationImpl loginKeyModel = new AppModelConfigurationImpl(LoginKey.class);

		AppModelConfigurationGroupImpl loginGroup = new AppModelConfigurationGroupImpl("login",
		        ListUtility.toList(loginModel, loginPointerModel, loginKeyModel));

		// App
		AppModelConfigurationImpl appModel = new AppModelConfigurationImpl(App.class);

		AppModelConfigurationGroupImpl appGroup = new AppModelConfigurationGroupImpl("app",
		        ListUtility.toList(appModel));

		List<AppModelConfigurationGroup> modelConfigurations = ListUtility.toList(loginGroup, appGroup);

		AppConfigurationImpl configuration = new AppConfigurationImpl(modelConfigurations);

		return configuration;
	}

	public static final AppConfiguration APP_CONFIG = makeGaeAppConfiguration();

	@Test
	public void testMakeTestClentConfiguration() throws IOException {

		TestModelsConfigurationGenerator generator = new TestModelsConfigurationGenerator(APP_CONFIG);
		GenFolder folder = generator.generateConfigurations();
		GenFilesWriterImpl writer = new GenFilesWriterImpl();
		// String path = writer.getRootFolder().getRoot().getAbsolutePath();
		writer.writeFiles(folder);


		// TODO: Print to files.

	}

}
