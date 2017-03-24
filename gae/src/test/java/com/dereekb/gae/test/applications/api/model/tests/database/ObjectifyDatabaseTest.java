package com.dereekb.gae.test.applications.api.model.tests.database;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.exception.UpdateUnkeyedEntityException;
import com.dereekb.gae.server.datastore.objectify.components.ObjectifyKeyedSetter;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntity;
import com.dereekb.gae.server.datastore.objectify.core.impl.ObjectifyDatabaseImpl;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;

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
	public void testUpdateNewItemFails() {
		ObjectifyKeyedSetter<Login> setter = this.databaseEntity.setter();

		Login test = new Login();

		try {
			setter.update(test);
			Assert.fail("Update should have failed.");
		} catch (UpdateUnkeyedEntityException e) {

		}
	}

	// MARK: Getter
	@Test
	public void testGetter() {

	}

}
