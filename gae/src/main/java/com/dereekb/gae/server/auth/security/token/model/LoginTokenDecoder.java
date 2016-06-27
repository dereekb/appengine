package com.dereekb.gae.server.auth.security.token.model;


public interface LoginTokenDecoder {

	public LoginToken decodeLoginToken(String token);

}
