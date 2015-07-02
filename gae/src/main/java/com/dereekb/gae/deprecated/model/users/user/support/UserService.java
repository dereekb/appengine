package com.thevisitcompany.gae.deprecated.model.users.user.support;

import com.thevisitcompany.gae.deprecated.model.users.user.User;

/**
 * Basic service for retrieving and saving an user.
 * 
 * @author dereekb
 * 
 */
public interface UserService
        extends UserSource {

	public User getUser();

	public void saveUser(User user);

}
