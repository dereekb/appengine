package com.dereekb.gae.test.applications.api.model.login;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.crud.services.CrudService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.request.impl.DeleteRequestImpl;
import com.dereekb.gae.model.crud.services.response.DeleteResponse;
import com.dereekb.gae.model.crud.task.impl.delete.ScheduleDeleteTask;
import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginAuthenticationService;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginStatusService;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginStatusServiceManager;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.test.server.auth.TestLoginTokenPair;

/**
 * Special test that tests all related models for a login are properly deleted
 * when the "parent" is deleted. (I.E. when a LoginPointer for LoginKeys is
 * deleted.)
 * 
 * @author dereekb
 *
 */
public class RelatedLoginModelDeleteTest extends ApiApplicationTestContext {

	@Autowired
	private KeyLoginStatusServiceManager serviceManager;

	@Autowired
	private KeyLoginAuthenticationService authenticationService;

	@Autowired
	@Qualifier("loginScheduleDeleteTask")
	private ScheduleDeleteTask<Login> scheduleLoginDeleteTask;

	@Autowired
	@Qualifier("loginRegistry")
	private ObjectifyRegistry<Login> loginRegistry;

	@Autowired
	@Qualifier("loginCrudService")
	private CrudService<Login> loginCrudService;

	@Autowired
	@Qualifier("loginPointerRegistry")
	private ObjectifyRegistry<LoginPointer> loginPointerRegistry;

	@Autowired
	@Qualifier("loginPointerCrudService")
	private CrudService<LoginPointer> loginPointerCrudService;

	@Autowired
	@Qualifier("loginPointerTestModelGenerator")
	private TestModelGenerator<LoginPointer> loginPointerGenerator;

	@Autowired
	@Qualifier("loginKeyTestModelGenerator")
	private TestModelGenerator<LoginKey> loginKeyGenerator;

	@Autowired
	@Qualifier("loginKeyRegistry")
	private ObjectifyRegistry<LoginKey> loginKeyRegistry;

	// MARK: LoginKey
	@Test
	public void testLoginKeysDeleteWhenLoginPointerIsDeleted() {

		TestLoginTokenPair pair = this.testLoginTokenContext.generateLogin("loginKeysDeleteTest");
		Login login = pair.getLogin();

		KeyLoginStatusService statusService = this.serviceManager.getService(login);
		LoginPointer pointer = statusService.enable();
		LoginPointer pointerB = this.loginPointerGenerator.generate();

		LoginKey keyA = this.loginKeyGenerator.generate();
		keyA.setLoginPointer(pointer.getObjectifyKey());

		List<LoginKey> otherKeys = this.loginKeyGenerator.generate(5);

		this.loginKeyRegistry.save(keyA, true);

		// Delete Login Pointer
		DeleteRequest request = new DeleteRequestImpl(pointer);
		this.loginPointerCrudService.delete(request);

		waitUntilTaskQueueCompletes();

		Assert.assertFalse(this.loginPointerRegistry.exists(pointer));
		Assert.assertFalse(this.loginKeyRegistry.exists(keyA));

		Assert.assertTrue(this.loginPointerRegistry.exists(pointerB));
		Assert.assertTrue(this.loginKeyRegistry.allExist(ModelKey.readModelKeys(otherKeys)));
	}

	// MARK: LoginPointer
	@Test
	public void testLoginPointersDeleteWhenLoginIsDeleted() {

		TestLoginTokenPair pair = this.testLoginTokenContext.generateLogin("loginPointerDeleteTest");
		Login login = pair.getLogin();

		// Create/Get Login Pointers
		LoginPointer passwordPointer = pair.getLoginPointer();

		KeyLoginStatusService statusService = this.serviceManager.getService(login);
		LoginPointer apiPointer = statusService.enable();

		// Delete Login

		try {
			DeleteRequest request = new DeleteRequestImpl(login);
			DeleteResponse<Login> response = this.loginCrudService.delete(request);

			Assert.assertFalse(response.getDeletedModels().isEmpty());
		} catch (AtomicOperationException e) {

			// Directly schedule it since filters may have inhibited removal.
			this.scheduleLoginDeleteTask.sendTask(login);
		}

		waitUntilTaskQueueCompletes();

		Assert.assertFalse(this.loginRegistry.exists(login));
		Assert.assertFalse(this.loginPointerRegistry.exists(passwordPointer));
		Assert.assertFalse(this.loginPointerRegistry.exists(apiPointer));
	}

}
