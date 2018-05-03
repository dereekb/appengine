package com.dereekb.gae.extras.gen.app.config.app.model.local;

import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelCrudsConfiguration;
import com.dereekb.gae.model.crud.services.impl.CrudServiceImpl;

/**
 * {@link CrudServiceImpl} and cruds.xml configuration.
 *
 * @author dereekb
 *
 */
public interface LocalModelCrudsConfiguration
        extends AppModelCrudsConfiguration {

	public boolean hasValidatedCreate();

	public boolean hasCreateAttributeUpdater();

	public Class<?> getModelCreateAttributeUpdaterClass();

	public Class<?> getModelAttributeUpdaterClass();

	public boolean hasRelatedAttributeUtility();

	public Class<?> getModelRelatedModelAttributeUtilityClass();

}
