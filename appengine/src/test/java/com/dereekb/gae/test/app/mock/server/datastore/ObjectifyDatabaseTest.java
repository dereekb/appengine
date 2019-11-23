package com.dereekb.gae.test.app.mock.server.datastore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.extras.gen.test.model.foo.Foo;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntity;
import com.dereekb.gae.server.datastore.objectify.core.impl.ObjectifyDatabaseImpl;
import com.dereekb.gae.test.app.mock.context.AbstractAppTestingContext;
import com.dereekb.gae.test.app.server.datastore.objectify.SetterTestUtility;
import com.dereekb.gae.test.app.server.datastore.objectify.SetterTestUtility.SetterTestUtilityDelegate;
import com.dereekb.gae.utilities.misc.random.PositiveLongGenerator;

public class ObjectifyDatabaseTest extends AbstractAppTestingContext {

	@Autowired
	@Qualifier("objectifyDatabase")
	private ObjectifyDatabaseImpl database;

	private ObjectifyDatabaseEntity<Foo> databaseEntity;

	@BeforeEach
	public void setup() {
		this.databaseEntity = this.database.getDatabaseEntity(Foo.class);
	}

	// MARK: Setter
	@Test
	public void testSetter() {
		SetterTestUtility<Foo> utility = new SetterTestUtility<Foo>(this.databaseEntity,
		        new SetterTestUtilityDelegate<Foo>() {

			        @Override
			        public Foo makeNew() {
				        return new Foo();
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
