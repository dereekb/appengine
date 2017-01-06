package com.dereekb.gae.server.auth.security.login.key;

/**
 * Service that combines both {@link KeyLoginStatusService} and {@link KeyLoginAuthenticationService}.
 * 
 * @author dereekb
 *
 */
public interface KeyLoginService extends KeyLoginStatusService, KeyLoginAuthenticationService {

}
