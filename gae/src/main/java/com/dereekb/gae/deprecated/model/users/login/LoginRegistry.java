package com.thevisitcompany.gae.deprecated.model.users.login;

import java.util.List;

import com.thevisitcompany.gae.server.datastore.objectify.ObjectifyModelRegistry;
import com.thevisitcompany.gae.server.datastore.objectify.query.ObjectifyConditionQueryFilter;
import com.thevisitcompany.gae.server.datastore.objectify.query.ObjectifyQuery;
import com.thevisitcompany.gae.server.datastore.objectify.query.ObjectifyQueryFilter;
import com.thevisitcompany.gae.server.datastore.objectify.query.ObjectifyQueryFilterOperator;

public class LoginRegistry extends ObjectifyModelRegistry<Login> {

	public static LoginRegistry getRegistry() {
		return new LoginRegistry();
	}

	public LoginRegistry() {
		super(Login.class);
	}

	public Login findLoginWithEmail(String email) {

		Login login = null;

		ObjectifyQuery<Login> objectifyQuery = new ObjectifyQuery<Login>(Login.class);
		ObjectifyConditionQueryFilter emailFilter = new ObjectifyConditionQueryFilter("email",
		        ObjectifyQueryFilterOperator.Equal, email);
		List<ObjectifyQueryFilter> filters = objectifyQuery.getQueryFilters();
		filters.add(emailFilter);

		objectifyQuery.setLimit(1);
		List<Login> results = this.query.queryEntities(objectifyQuery);

		if (results.isEmpty() == false) {
			login = results.get(0);
		}

		return login;
	}

}
