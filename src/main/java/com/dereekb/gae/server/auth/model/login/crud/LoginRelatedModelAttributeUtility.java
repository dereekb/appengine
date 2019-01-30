package com.dereekb.gae.server.auth.model.login.crud;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.shared.LoginRelatedModel;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetContextService;
import com.dereekb.gae.server.auth.security.model.roles.utility.crud.AbstractModelRoleAttributeUtility;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;
import com.googlecode.objectify.Key;

/**
 * {@link AbstractModelRoleAttributeUtility} for {@link LoginRelatedModel}.
 *
 * @author dereekb
 *
 */
public class LoginRelatedModelAttributeUtility extends AbstractModelRoleAttributeUtility<Login> {

	public static final String LOGIN_ATTRIBUTE = "login";

	public LoginRelatedModelAttributeUtility(ModelRoleSetContextService<Login> modelRoleSetContextService) {
		super(modelRoleSetContextService);
	}

	// MARK: Instance
	public <T extends LoginRelatedModel> AttributeInstanceImpl makeInstanceWithTemplate(T template)
	        throws InvalidAttributeException {
		Key<Login> key = template.getLogin();
		return this.makeInstanceWithKey(key);
	}

	public AttributeInstanceImpl makeInstanceWithKey(Key<Login> key)
	        throws InvalidAttributeException {
		return super.makeInstanceWithKey(LOGIN_ATTRIBUTE, key);
	}

	@Override
	public String toString() {
		return "LoginRelatedModelAttributeUtility []";
	}

}
