package com.dereekb.gae.extras.gen.test.app.gae.local;

import com.dereekb.gae.extras.gen.app.config.app.model.local.impl.LocalModelConfigurationGroupImpl;
import com.dereekb.gae.extras.gen.app.config.app.model.local.impl.LocalModelConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl.AllowAllSecuredQueryInitializerConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl.LocalModelContextConfigurerImpl;
import com.dereekb.gae.extras.gen.test.model.Boo;
import com.dereekb.gae.extras.gen.test.model.foo.Foo;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * Local generation gen for the test {@link Foo} and {@link Boo} models.
 *
 * @author dereekb
 *
 */
public class TestModelGroupConfigurationGen {

	public static LocalModelConfigurationGroupImpl makeLocalTestGroupConfig() {

		// Foo
		LocalModelConfigurationImpl fooModel = makeFooModelConfig();

		LocalModelConfigurationGroupImpl testGroup = new LocalModelConfigurationGroupImpl("foo",
		        ListUtility.toList(fooModel));

		return testGroup;
	}

	public static LocalModelConfigurationImpl makeFooModelConfig() {
		LocalModelConfigurationImpl fooModel = new LocalModelConfigurationImpl(Foo.class);

		LocalModelContextConfigurerImpl customLocalModelContextConfigurer = new LocalModelContextConfigurerImpl();
		customLocalModelContextConfigurer
		        .setSecuredQueryInitializerConfigurer(new AllowAllSecuredQueryInitializerConfigurerImpl());

		fooModel.setCustomModelContextConfigurer(customLocalModelContextConfigurer);

		return fooModel;
	}

}
