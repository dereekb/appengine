package com.dereekb.gae.test.applications.api.model.tests.database;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.datastore.objectify.core.impl.ObjectifyDatabaseImpl;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;


public class ObjectifyDatabaseTest extends ApiApplicationTestContext {

	@Autowired
	@Qualifier("objectifyDatabase")
	private ObjectifyDatabaseImpl database;

	@Test
	public void testDatabase() {
		Assert.assertFalse(this.database.getDefinitionTypes().isEmpty());
	}

}
