package com.dereekb.gae.test.spring.google;

import com.google.appengine.api.datastore.dev.LocalDatastoreService.AutoIdAllocationPolicy;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;

public class LocalDatastoreServiceTestConfigFactory {

	private boolean noStorage = true;
	private boolean noIndexAutoGen = true;

	private boolean highRepJobPolicy = true;

	// Used scattered identifiers
	private AutoIdAllocationPolicy idPolicy = AutoIdAllocationPolicy.SCATTERED;

	public LocalDatastoreServiceTestConfig make() {
		LocalDatastoreServiceTestConfig config = new LocalDatastoreServiceTestConfig();

		if (this.highRepJobPolicy) {
			config = config.setApplyAllHighRepJobPolicy();
		}

		config = config.setNoStorage(true);
		config = config.setNoIndexAutoGen(true);

		config = config.setAutoIdAllocationPolicy(this.idPolicy);

		return config;
	}

	public boolean isNoStorage() {
		return this.noStorage;
	}

	public void setNoStorage(boolean noStorage) {
		this.noStorage = noStorage;
	}

	public boolean isNoIndexAutoGen() {
		return this.noIndexAutoGen;
	}

	public void setNoIndexAutoGen(boolean noIndexAutoGen) {
		this.noIndexAutoGen = noIndexAutoGen;
	}

	public AutoIdAllocationPolicy getIdPolicy() {
		return this.idPolicy;
	}

	public void setIdPolicy(AutoIdAllocationPolicy idPolicy) {
		if (idPolicy == null) {
			throw new IllegalArgumentException("idPolicy cannot be null.");
		}

		this.idPolicy = idPolicy;
	}

}
