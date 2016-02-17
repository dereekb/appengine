package com.dereekb.gae.server.auth.security.registration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.dereekb.gae.server.auth.security.filter.exception.NewLoginAuthenticationException;
import com.dereekb.gae.utilities.collections.map.catchmap.CatchMap;

/**
 * Implementation of {@link NewLoginHandler} that contains a map of
 * {@link NewLoginHandler} instances for each different login system.
 *
 * @author dereekb
 *
 */
public class NewLoginHandlerSystemMap
        implements NewLoginHandler {

	private CatchMap<String, NewLoginHandler> map;

	public NewLoginHandlerSystemMap(CatchMap<String, NewLoginHandler> map) {
		this.map = map;
	}

	public CatchMap<String, NewLoginHandler> getMap() {
		return this.map;
	}

	public void setMap(CatchMap<String, NewLoginHandler> map) {
		this.map = map;
	}

	@Override
	public Authentication handleNewLogin(NewLoginAuthenticationException e,
	                                     HttpServletRequest request,
	                                     HttpServletResponse response) throws AuthenticationException {

		String system = e.getLoginSystem();
		NewLoginHandler handler = this.map.get(system);

		if (handler == null) {
			throw new AuthenticationServiceException("No indexService registered to handle login for system '" + system
			        + "'.");
		}

		return handler.handleNewLogin(e, request, response);
	}

	@Override
	public String toString() {
		return "NewLoginHandlerSystemMap [map=" + this.map + "]";
	}

}
