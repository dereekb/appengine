package com.dereekb.gae.extras.gen.app.config.app.model;

import com.dereekb.gae.model.crud.services.impl.CrudServiceImpl;

/**
 * {@link CrudServiceImpl} and cruds.xml configuration.
 *
 * @author dereekb
 *
 */
public interface AppModelCrudsConfiguration {

	public boolean hasCrudService();

	public boolean hasCreateService();

	public boolean hasUpdateService();

	public boolean hasDeleteService();

	public boolean hasValidatedCreate();

	public boolean hasCreateAttributeUpdater();

	public Class<?> getModelCreateAttributeUpdaterClass();

	public Class<?> getModelAttributeUpdaterClass();

	public boolean hasRelatedAttributeUtility();

	public Class<?> getModelRelatedModelAttributeUtilityClass();

}
