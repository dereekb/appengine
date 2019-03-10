package com.dereekb.gae.test.app.mock.context;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabase;
import com.dereekb.gae.test.server.datastore.objectify.TestObjectifyInitializerImpl;

/**
 * {@link AbstractGaeTestingContext} extension that loads the application's context only. The API and Taskqueue are not loaded.
 *
 * @author dereekb
 *
 */
@DirtiesContext
@ExtendWith(SpringExtension.class)
@SpringJUnitConfig( name = "context", locations = {
        AbstractAppContextOnlyTestingContext.APP_CONTEXT_XML_PATH, AbstractAppContextOnlyTestingContext.WEB_TESTING_XML_PATH })
public abstract class AbstractAppContextOnlyTestingContext extends AbstractGaeTestingContext {

	public static final String APP_CONTEXT_XML_PATH = AbstractAppContextOnlyTestingContext.BASE_MAIN_PATH + "spring/context/context.xml";

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
