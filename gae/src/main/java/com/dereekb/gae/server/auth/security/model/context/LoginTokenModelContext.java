package com.dereekb.gae.server.auth.security.model.context;

import com.dereekb.gae.server.auth.security.model.roles.loader.AnonymousModelRoleSetContext;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;

/**
 * {@link LoginToken} context that also represents a specific model.
 *
 * @author dereekb
 *
 */
public interface LoginTokenModelContext
        extends AnonymousModelRoleSetContext {}
