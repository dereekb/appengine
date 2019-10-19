package com.dereekb.gae.server.auth.security.model.context;

import com.dereekb.gae.server.auth.security.model.roles.loader.AnonymousModelRoleSetContext;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;

/**
 * {@link AnonymousModelRoleSetContext} extension used by the auth system for
 * efficiently packaging models.
 * <p>
 * Does not rely on {@link LoginToken} components, but is designed to fit inside
 * one compactly.
 *
 * @author dereekb
 *
 */
public interface LoginTokenModelContext
        extends AnonymousModelRoleSetContext {}
