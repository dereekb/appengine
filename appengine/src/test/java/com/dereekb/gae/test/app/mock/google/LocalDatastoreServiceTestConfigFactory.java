package com.dereekb.gae.test.app.mock.google;

import com.dereekb.gae.utilities.factory.Factory;
import com.google.appengine.api.datastore.dev.LocalDatastoreService.AutoIdAllocationPolicy;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;

/**
 * {@link Factory} for {@link LocalDatastoreServiceTestConfigFactory}.
 *
 * @author dereekb
 *
 */
public class LocalDatastoreServiceTestConfigFactory
        implements Factory<LocalDatastoreServiceTestConfig> {

	private boolean applyAllHighRepJobPolicy = true;

	private boolean noStorage = true;
	private boolean noIndexAutoGen = true;

	private boolean highRepJobPolicy = true;

	// Used scattered identifiers
	private AutoIdAllocationPolicy idPolicy = AutoIdAllocationPolicy.SCATTERED;

	@Override
	public LocalDatastoreServiceTestConfig make() {
		LocalDatastoreServiceTestConfig config = new LocalDatastoreServiceTestConfig();

		if (this.highRepJobPolicy) {
			config = config.setApplyAllHighRepJobPolicy();
		}

		config = config.setNoStorage(true);
		config = config.setNoIndexAutoGen(true);

		config = config.setAutoIdAllocationPolicy(this.idPolicy);

		if (this.applyAllHighRepJobPolicy) {
			config = config.setApplyAllHighRepJobPolicy();
		}

		return config;
	}

	public boolean isApplyAllHighRepJobPolicy() {
		return this.applyAllHighRepJobPolicy;
	}

	public void setApplyAllHighRepJobPolicy(boolean applyAllHighRepJobPolicy) {
		this.applyAllHighRepJobPolicy = applyAllHighRepJobPolicy;
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

	public boolean isHighRepJobPolicy() {
		return this.highRepJobPolicy;
	}

	public void setHighRepJobPolicy(boolean highRepJobPolicy) {
		this.highRepJobPolicy = highRepJobPolicy;
	}

	@Override
	public String toString() {
		return "LocalDatastoreServiceTestConfigFactory [applyAllHighRepJobPolicy=" + this.applyAllHighRepJobPolicy
		        + ", noStorage=" + this.noStorage + ", noIndexAutoGen=" + this.noIndexAutoGen + ", highRepJobPolicy="
		        + this.highRepJobPolicy + ", idPolicy=" + this.idPolicy + "]";
	}

}
