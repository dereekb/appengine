package com.dereekb.gae.test.applications.api.model.tests.database;

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityDefinition;
import com.dereekb.gae.server.datastore.objectify.core.impl.ObjectifyDatabaseImpl;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;


public class ObjectifyDatabaseTest extends ApiApplicationTestContext {

	@Autowired
	@Qualifier("objectifyDatabase")
	private ObjectifyDatabaseImpl database;

	@Autowired
	@Qualifier("objectifyDatabaseEntries")
	private List<? extends ObjectifyDatabaseEntityDefinition> definitions;

	public ObjectifyDatabaseImpl getDatabase() {
		return this.database;
	}

	public void setDatabase(ObjectifyDatabaseImpl database) {
		this.database = database;
	}

	public List<? extends ObjectifyDatabaseEntityDefinition> getDefinitions() {
		return this.definitions;
	}

	public void setDefinitions(List<? extends ObjectifyDatabaseEntityDefinition> definitions) {
		this.definitions = definitions;
	}

	@Test
	public void testDatabaseEntitiesSize() {
		Set<Class<?>> types = this.database.getDefinitionTypes();
		Assert.assertTrue(types.size() == this.definitions.size());
	}

	@Test
	public void testDatabaseDefinitions() {

	}

}
