package com.dereekb.gae.test.server.auth.impl.utility;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.exception.UninitializedModelException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.server.auth.impl.TestRemoteLoginSystemLoginTokenContextImpl;

/**
 * {@link Getter} implementation for {@link TestRemoteLoginSystemLoginTokenContextImpl}.
 *
 * @author dereekb
 *
 */
public class TestRemoteLoginSystemLoginTokenContextLoginGetterImpl
        implements Getter<Login> {

	@Override
	public Login get(Login model) throws UninitializedModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Login> get(Iterable<Login> models) throws UninitializedModelException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Login get(ModelKey key) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Login> getWithKeys(Iterable<ModelKey> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getModelType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(Login model) throws UninitializedModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exists(ModelKey key) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean allExist(Iterable<ModelKey> keys) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<ModelKey> getExisting(Iterable<ModelKey> keys) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

}
