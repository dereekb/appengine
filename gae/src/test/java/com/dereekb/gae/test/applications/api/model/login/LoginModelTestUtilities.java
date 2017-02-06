package com.dereekb.gae.test.applications.api.model.login;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.googlecode.objectify.Key;

public class LoginModelTestUtilities {

	public static void setPointerLogins(Login login,
	                                    Collection<LoginPointer> pointers) {
		setPointerLogins(login.getObjectifyKey(), pointers);
	}

	public static void setPointerLogins(Key<Login> login,
	                                    Collection<LoginPointer> pointers) {
		for (LoginPointer pointer : pointers) {
			pointer.setLogin(login);
		}
	}

	public static boolean allLinkedToLogin(Login login,
	                                       Collection<LoginPointer> pointers) {
		return allLinkedToLogin(login.getObjectifyKey(), pointers);
	}

	public static boolean allLinkedToLogin(Key<Login> login,
	                                       Collection<LoginPointer> pointers) {

		for (LoginPointer pointer : pointers) {
			if (pointer.getLogin().equivalent(login) == false) {
				return false;
			}
		}

		return true;
	}

	public static void setPointerTypes(LoginPointerType type,
	                                   List<LoginPointer> pointers) {
		for (LoginPointer pointer : pointers) {
			pointer.setLoginPointerType(type);
		}
	}

}
