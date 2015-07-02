package com.thevisitcompany.gae.deprecated.model.users.user;

import com.thevisitcompany.gae.server.datastore.objectify.ObjectifyModelRegistry;

public class UserRegistry extends ObjectifyModelRegistry<User> {

	public static UserRegistry getRegistry() {
		return new UserRegistry();
	}

	public UserRegistry() {
		super(User.class);
	}

}
