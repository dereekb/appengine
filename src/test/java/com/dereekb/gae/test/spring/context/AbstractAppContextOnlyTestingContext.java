package com.dereekb.gae.test.spring.context;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabase;
import com.dereekb.gae.test.server.datastore.objectify.TestObjectifyInitializerImpl;

@ExtendWith(SpringExtension.class)
@SpringJUnitConfig( name = "api", locations = {
        AbstractAppContextOnlyTestingContext.API_APPLICATION_XML_PATH, AbstractAppContextOnlyTestingContext.WEB_TESTING_XML_PATH })
public abstract class AbstractAppContextOnlyTestingContext extends AbstractGaeTestingContext {

	public static final String API_APPLICATION_XML_PATH = AbstractAppContextOnlyTestingContext.BASE_MAIN_PATH + "spring/app.xml";

	public static final String WEB_TESTING_XML_PATH = BASE_TESTING_PATH + "testing-web.xml";

	@Autowired
	protected ObjectifyDatabase testObjectifyDatabase;

	@Autowired
	protected TestObjectifyInitializerImpl testObjectifyInitializer;

	@Override
	@BeforeEach
	public void setUpAppServices() {
		super.setUpAppServices();
		this.initializeObjectify();
	}

	@Override
	@AfterEach
	public void tearDownAppServices() {
		super.tearDownAppServices();
		this.resetObjectify();
	}

	protected void initializeObjectify() {
		this.testObjectifyInitializer.begin();
	}

	protected void resetObjectify() {
		this.testObjectifyInitializer.reset();
	}

}
