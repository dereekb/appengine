package com.thevisitcompany.gae.deprecated.model.users.user.functions;

import com.thevisitcompany.gae.deprecated.model.users.user.User;
import com.thevisitcompany.gae.model.crud.function.delegate.CreateFunctionDelegate;
import com.thevisitcompany.gae.model.crud.function.delegate.UpdateFunctionDelegate;

public class UserFunctionsDelegate
        implements CreateFunctionDelegate<User>, UpdateFunctionDelegate<User> {

	@Override
	public boolean update(User source,
	                      User context) {

		String name = source.getName();
		context.setName(name);

		return true;
	}

	@Override
	public User create(User source) {
		User user = new User();

		user.setName(source.getName());
		user.setCompany(source.getCompany());
		user.setPhonenumber(source.getPhonenumber());

		return user;
	}

}
