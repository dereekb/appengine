package com.dereekb.gae.server.auth.security.token.model;

/**
 * {@link LoginToken} that has been decoded and has access to it's original
 * string form, allowing it to implement {@link EncodedLoginToken}.
 * 
 * @author dereekb
 *
 */
public interface DecodedLoginToken
        extends LoginToken, EncodedLoginToken {

}
