package com.dereekb.gae.extras.gen.app.config.app.model.shared;

/**
 * Basic CRUD configuration.
 *
 * @author dereekb
 *
 */
public interface AppModelCrudsConfiguration {

	public boolean hasCrudService();

	public boolean hasCreateService();

	public boolean hasUpdateService();

	public boolean hasDeleteService();

}
