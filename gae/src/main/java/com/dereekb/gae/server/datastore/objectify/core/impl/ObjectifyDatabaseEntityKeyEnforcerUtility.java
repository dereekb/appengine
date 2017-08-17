package com.dereekb.gae.server.datastore.objectify.core.impl;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityKeyEnforcement;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityKeyEnforcer;

/**
 * Utility that contains references to default {@link ObjectifyDatabaseEntityKeyEnforcer} implementations.
 *
 * @author dereekb
 *
 */
public class ObjectifyDatabaseEntityKeyEnforcerUtility {

	public static final ObjectifyDatabaseEntityKeyEnforcer DEFAULT_SINGLETON = new DefaultObjectifyDatabaseEntityKeyEnforcerImpl();
	public static final ObjectifyDatabaseEntityKeyEnforcer NULL_SINGLETON = new NullObjectifyDatabaseEntityKeyEnforcerImpl();
	public static final ObjectifyDatabaseEntityKeyEnforcer PROVIDED_SINGLETON = new ProvidedObjectifyDatabaseEntityKeyEnforcerImpl();

	public static ObjectifyDatabaseEntityKeyEnforcer enforcerForType(ObjectifyDatabaseEntityKeyEnforcement enforcement) {
		ObjectifyDatabaseEntityKeyEnforcer enforcer = null;

		switch (enforcement) {
			case DEFAULT:
				enforcer = DEFAULT_SINGLETON;
				break;
			case MUST_BE_NULL:
				enforcer = NULL_SINGLETON;
				break;
			case MUST_BE_PROVIDED:
				enforcer = PROVIDED_SINGLETON;
				break;
			case MUST_BE_PROVIDED_AND_UNIQUE:
			default:
				throw new UnsupportedOperationException();
		}

		return enforcer;
	}

	/**
	 * Default {@link ObjectifyDatabaseEntityKeyEnforcer} implementation.
	 * 
	 * @author dereekb
	 *
	 */
	private static final class DefaultObjectifyDatabaseEntityKeyEnforcerImpl
	        implements ObjectifyDatabaseEntityKeyEnforcer {

		private DefaultObjectifyDatabaseEntityKeyEnforcerImpl() {};

		// MARK: ObjectifyDatabaseEntityKeyEnforcer
		@Override
		public boolean isAllowedForStorage(ModelKey key) {
			if (key == null) {
				return true;
			} else if (key.getType() == ModelKeyType.NAME) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public String toString() {
			return "DefaultObjectifyDatabaseEntityKeyEnforcerImpl []";
		}

	}

	private static final class NullObjectifyDatabaseEntityKeyEnforcerImpl
	        implements ObjectifyDatabaseEntityKeyEnforcer {

		private NullObjectifyDatabaseEntityKeyEnforcerImpl() {};

		// MARK: ObjectifyDatabaseEntityKeyEnforcer
		@Override
		public boolean isAllowedForStorage(ModelKey key) {
			return (key == null);
		}

	}

	private static final class ProvidedObjectifyDatabaseEntityKeyEnforcerImpl
	        implements ObjectifyDatabaseEntityKeyEnforcer {

		private ProvidedObjectifyDatabaseEntityKeyEnforcerImpl() {};

		// MARK: ObjectifyDatabaseEntityKeyEnforcer
		@Override
		public boolean isAllowedForStorage(ModelKey key) {
			return (key != null);
		}

	}

}
