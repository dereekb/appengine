package com.dereekb.gae.server.auth.security.token.model;

/**
 * {@link LoginTokenEncoder} and {@link LoginTokenDecoder} interface.
 * 
 * @author dereekb
 *
 */
public interface LoginTokenEncoderDecoder<T extends LoginToken>
        extends LoginTokenEncoder<T>, LoginTokenDecoder<T> {}
