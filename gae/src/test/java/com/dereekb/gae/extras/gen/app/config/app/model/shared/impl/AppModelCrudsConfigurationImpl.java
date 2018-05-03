package com.dereekb.gae.extras.gen.app.config.app.model.shared.impl;

import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelCrudsConfiguration;

/**
 * {@link AppModelCrudsConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public class AppModelCrudsConfigurationImpl
        implements AppModelCrudsConfiguration {

	private boolean crudService = true;
	private boolean createService = true;
	private boolean updateService = true;
	private boolean deleteService = true;

	@Override
	public boolean hasCrudService() {
		return this.crudService;
	}

	public void setCrudService(boolean crudService) {
		this.crudService = crudService;
	}

	@Override
	public boolean hasCreateService() {
		return this.createService;
	}

	public void setHasCreateService(boolean createService) {
		this.createService = createService;
	}

	@Override
	public boolean hasUpdateService() {
		return this.updateService;
	}

	public void setHasUpdateService(boolean updateService) {
		this.updateService = updateService;
	}

	@Override
	public boolean hasDeleteService() {
		return this.deleteService;
	}

	public void setHasDeleteService(boolean deleteService) {
		this.deleteService = deleteService;
	}

}
