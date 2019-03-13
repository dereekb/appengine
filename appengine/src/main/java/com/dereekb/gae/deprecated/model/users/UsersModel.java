package com.thevisitcompany.gae.deprecated.model.users;

import com.thevisitcompany.gae.server.datastore.objectify.ObjectifyModel;

public abstract class UsersModel<T> extends ObjectifyModel<T> {

	private static final long serialVersionUID = 1L;

	public UsersModel() {
		super();
	}

	public UsersModel(Long identifier) {
		super(identifier);
	}

}
