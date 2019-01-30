package com.thevisitcompany.gae.deprecated.authentication.login.support;

/**
 * A model that is dependent on a login delegate.
 * @author dereekb
 */
public interface LoginSourceDependent {

	public void setLoginSource(LoginSource delegate);

}
