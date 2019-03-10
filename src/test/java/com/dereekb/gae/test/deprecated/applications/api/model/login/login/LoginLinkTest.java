package com.dereekb.gae.test.applications.api.model.login.login.t;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.deprecated.applications.api.model.extension.links.AbstractLinkServiceTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

@Deprecated
@Disabled
public class LoginLinkTest extends AbstractLinkServiceTest {

	@Autowired
	@Qualifier("loginType")
	private String loginLinkType;

	@Autowired
	@Qualifier("loginLoginPointerLinkName")
	private String loginLoginPointerLinkName;

	@Autowired
	@Qualifier("loginTestModelGenerator")
	private TestModelGenerator<Login> loginGenerator;

	@Autowired
	@Qualifier("loginPointerTestModelGenerator")
	private TestModelGenerator<LoginPointer> loginPointerGenerator;

	@Autowired
	@Qualifier("loginRegistry")
	private ObjectifyRegistry<Login> loginRegistry;

	@Autowired
	@Qualifier("loginPointerRegistry")
	private ObjectifyRegistry<LoginPointer> loginPointerRegistry;

}
