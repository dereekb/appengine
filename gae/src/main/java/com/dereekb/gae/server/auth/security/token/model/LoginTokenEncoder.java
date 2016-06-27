package com.dereekb.gae.server.auth.security.token.model;


public interface LoginTokenEncoder {

	public String encodeLoginToken(LoginToken loginToken);

}
