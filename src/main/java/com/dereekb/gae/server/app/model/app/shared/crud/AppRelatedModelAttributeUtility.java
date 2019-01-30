package com.dereekb.gae.server.app.model.app.shared.crud;

import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetContextService;
import com.dereekb.gae.server.auth.security.model.roles.utility.crud.AbstractModelRoleAttributeUtility;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;
import com.googlecode.objectify.Key;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.app.shared.AppRelatedModel;

public class AppRelatedModelAttributeUtility extends AbstractModelRoleAttributeUtility<App> {

	public static final String APP_ATTRIBUTE = "app";

	public AppRelatedModelAttributeUtility(ModelRoleSetContextService<App> modelRoleSetContextService) {
		super(modelRoleSetContextService);
	}

	// MARK: Instance
	public <T extends AppRelatedModel> AttributeInstanceImpl makeInstanceWithTemplate(T template) throws InvalidAttributeException {
		Key<App> key = template.getApp();
		return super.makeInstanceWithKey(APP_ATTRIBUTE, key);
	}

}
