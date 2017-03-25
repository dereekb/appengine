package com.dereekb.gae.test.applications.api.model.tests.database;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntity;
import com.dereekb.gae.server.datastore.objectify.core.impl.ObjectifyDatabaseImpl;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.applications.api.model.tests.database.SetterTestUtility.SetterTestUtilityDelegate;
import com.dereekb.gae.utilities.misc.random.PositiveLongGenerator;

public class ObjectifyDatabaseTest extends ApiApplicationTestContext {

	@Autowired
	@Qualifier("objectifyDatabase")
	private ObjectifyDatabaseImpl database;

	private ObjectifyDatabaseEntity<Login> databaseEntity;

	@Before
	public void setup() {
		this.databaseEntity = this.database.getDatabaseEntity(Login.class);
	}

	// MARK: Setter
	@Test
	public void testSetter() {
		SetterTestUtility<Login> utility = new SetterTestUtility<Login>(this.databaseEntity,
		        new SetterTestUtilityDelegate<Login>() {

			        @Override
			        public Login makeNew() {
				        return new Login();
			        }

			        @Override
			        public ModelKey makeKey() {
				        return new ModelKey(PositiveLongGenerator.randomPositiveLong());
			        }

		        });

		utility.assertTestsPass();
	}

	// MARK: Getter
	@Test
	public void testGetter() {

	}

}
