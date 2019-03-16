package com.thevisitcompany.gae.deprecated.model.users.login.functions;

import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.model.extension.links.functions.LinksAction;
import com.thevisitcompany.gae.model.extension.links.functions.filters.CanLinkFilterDelegate;

public class LoginLinksFunctionDelegate
        implements CanLinkFilterDelegate<Login> {

	@Override
	public boolean canLink(Login object,
	                       String link,
	                       LinksAction action) {
		// TODO Auto-generated method stub
		return false;
	}

}
