package com.dereekb.gae.server.app.model.app.security.parent;

import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.app.misc.owned.AppOwnedModel;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetContextGetter;
import com.dereekb.gae.server.auth.security.model.roles.parent.impl.AbstractParentModelRoleSetContextReader;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link AbstractParentModelRoleSetContextReader} implementation for
 * {@link AppOwnedModel} types.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class AppParentModelRoleSetContextReaderImpl<T extends AppOwnedModel> extends AbstractParentModelRoleSetContextReader<T, App> {

	public AppParentModelRoleSetContextReaderImpl(ModelRoleSetContextGetter<App> parentGetter) {
		super(parentGetter);
	}

	// MARK: AbstractParentModelRoleSetContextReader
	@Override
	public ModelKey getParentModelKey(AppOwnedModel child) {
		return child.getAppOwnerKey();
	}

}
