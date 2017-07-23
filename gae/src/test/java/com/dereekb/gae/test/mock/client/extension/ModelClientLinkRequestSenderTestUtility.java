package com.dereekb.gae.test.mock.client.extension;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.model.extension.link.ClientLinkServiceRequest;
import com.dereekb.gae.client.api.model.extension.link.ClientLinkServiceRequestSender;
import com.dereekb.gae.client.api.model.extension.link.ClientLinkServiceResponse;
import com.dereekb.gae.client.api.model.extension.link.exception.ClientLinkServiceChangeException;
import com.dereekb.gae.client.api.model.extension.link.exception.ClientLinkSystemChangeError;
import com.dereekb.gae.client.api.model.extension.link.exception.ClientLinkSystemChangeErrorSet;
import com.dereekb.gae.client.api.model.extension.link.exception.LinkExceptionReason;
import com.dereekb.gae.client.api.model.extension.link.impl.ClientApiLinkChange;
import com.dereekb.gae.client.api.model.extension.link.impl.ClientLinkServiceRequestImpl;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;

import com.dereekb.gae.server.datastore.models.MutableUniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.api.model.extension.link.ApiLinkChange;

/**
 * Test utility for {@link ClientLinkServiceRequestSender}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelClientLinkRequestSenderTestUtility<T extends MutableUniqueModel> {

	private final TestModelGenerator<T> testModelGenerator;
	private final ClientLinkServiceRequestSender linkRequestSender;

	public ModelClientLinkRequestSenderTestUtility(ClientLinkServiceRequestSender linkRequestSender,
	        TestModelGenerator<T> testModelGenerator) {
		this.linkRequestSender = linkRequestSender;
		this.testModelGenerator = testModelGenerator;
	}

	// MARK: Tests
	public void testSystemClientLinkToNonExistantLink(ClientRequestSecurity security) throws Exception {
		String type = this.testModelGenerator.getTypeName();

		T model = this.testModelGenerator.generate();

		List<ApiLinkChange> changes = new ArrayList<>();

		ClientApiLinkChange linkChange = new ClientApiLinkChange();
		linkChange.setPrimary(model.getModelKey());
		linkChange.setChangeAction(MutableLinkChangeType.CLEAR);
		linkChange.setLinkName("NON_EXISTANT_LINK_NAME");

		changes.add(linkChange);

		ClientLinkServiceRequest request = new ClientLinkServiceRequestImpl(type, changes);

		try {
			this.linkRequestSender.updateLinks(request, security);
			Assert.fail("Unavailable link was apparently available...");
		} catch (ClientLinkServiceChangeException e) {
			ClientLinkSystemChangeErrorSet set = e.getErrorSet();
			List<ClientLinkSystemChangeError> errors = set.getErrors();

			Assert.assertFalse(errors.isEmpty());

			ClientLinkSystemChangeError error = errors.get(0);
			Assert.assertTrue(error.getReason() == LinkExceptionReason.LINK_UNAVAILABLE);
		}
	}

	public void testSystemClientLinkAtomicRequest(ClientRequestSecurity security) throws Exception {
		String type = this.testModelGenerator.getTypeName();
		ModelKey primaryKey = this.testModelGenerator.generateKey();

		List<ApiLinkChange> changes = new ArrayList<>();

		ClientApiLinkChange linkChange = new ClientApiLinkChange();
		linkChange.setPrimary(primaryKey);
		linkChange.setChangeAction(MutableLinkChangeType.CLEAR);
		linkChange.setLinkName("LINK_NAME");	// Should fail before this is a
		                                    	// problem.

		changes.add(linkChange);

		boolean atomic = true;
		ClientLinkServiceRequest request = new ClientLinkServiceRequestImpl(type, changes, atomic);

		try {
			this.linkRequestSender.updateLinks(request, security);
			Assert.fail("Model was apparently available..?");
		} catch (ClientAtomicOperationException e) {
			List<ModelKey> keys = e.getMissingKeys();
			Assert.assertTrue(keys.contains(primaryKey));
		}
	}

	public void testSystemClientLinkNonAtomicRequest(ClientRequestSecurity security) throws Exception {
		String type = this.testModelGenerator.getTypeName();
		ModelKey primaryKey = this.testModelGenerator.generateKey();

		List<ApiLinkChange> changes = new ArrayList<>();

		ClientApiLinkChange linkChange = new ClientApiLinkChange();
		linkChange.setPrimary(primaryKey);
		linkChange.setChangeAction(MutableLinkChangeType.CLEAR);
		linkChange.setLinkName("LINK_NAME");	// Should fail before this is a
		                                    	// problem.

		changes.add(linkChange);

		boolean atomic = false;
		ClientLinkServiceRequest request = new ClientLinkServiceRequestImpl(type, changes, atomic);

		try {
			ClientLinkServiceResponse response = this.linkRequestSender.updateLinks(request, security);
			List<ModelKey> keys = response.getMissing();
			Assert.assertTrue(keys.contains(primaryKey));
		} catch (ClientAtomicOperationException e) {
			Assert.fail("Atomic operation exception raised on non-atomic request.");
		}
	}

}
