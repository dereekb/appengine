package com.dereekb.gae.test.app.mock.client.extension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

	private String testLinkName;
	private String nonExistantLinkName = "NON_EXISTANT_LINK_NAME";

	private final TestModelGenerator<T> testModelGenerator;
	private final ClientLinkServiceRequestSender linkRequestSender;

	public ModelClientLinkRequestSenderTestUtility(String testLinkName,
	        TestModelGenerator<T> testModelGenerator,
	        ClientLinkServiceRequestSender linkRequestSender) {

		if (testModelGenerator == null) {
			throw new IllegalArgumentException("Test Model Generator cannot be null.");
		}

		if (linkRequestSender == null) {
			throw new IllegalArgumentException("List Request Sender cannot be null.");
		}

		this.setTestLinkName(testLinkName);

		this.linkRequestSender = linkRequestSender;
		this.testModelGenerator = testModelGenerator;
	}

	public String getTestLinkName() {
		return this.testLinkName;
	}

	public void setTestLinkName(String testLinkName) {
		if (testLinkName == null) {
			throw new IllegalArgumentException("testLinkName cannot be null.");
		}

		this.testLinkName = testLinkName;
	}

	public String getNonExistantLinkName() {
		return this.nonExistantLinkName;
	}

	public void setNonExistantLinkName(String nonExistantLinkName) {
		if (nonExistantLinkName == null) {
			throw new IllegalArgumentException("nonExistantLinkName cannot be null.");
		}

		this.nonExistantLinkName = nonExistantLinkName;
	}

	// MARK: Tests
	public void testSystemClientLinkToNonExistantLink(ClientRequestSecurity security) throws Exception {
		String type = this.testModelGenerator.getModelType();

		T model = this.testModelGenerator.generate();

		List<ApiLinkChange> changes = new ArrayList<>();

		ClientApiLinkChange linkChange = new ClientApiLinkChange();
		linkChange.setPrimary(model.getModelKey());
		linkChange.setChangeAction(MutableLinkChangeType.CLEAR);
		linkChange.setLinkName(this.nonExistantLinkName);

		changes.add(linkChange);

		ClientLinkServiceRequest request = new ClientLinkServiceRequestImpl(type, changes);

		try {
			this.linkRequestSender.updateLinks(request, security);
			fail("Unavailable link was apparently available...");
		} catch (ClientLinkServiceChangeException e) {
			ClientLinkSystemChangeErrorSet set = e.getErrorSet();
			List<ClientLinkSystemChangeError> errors = set.getErrors();

			assertFalse(errors.isEmpty());

			ClientLinkSystemChangeError error = errors.get(0);
			assertTrue(error.getReason() == LinkExceptionReason.LINK_UNAVAILABLE);
		}
	}

	public void testSystemClientLinkAtomicRequest(ClientRequestSecurity security) throws Exception {
		String type = this.testModelGenerator.getModelType();
		ModelKey primaryKey = this.testModelGenerator.generateKey();

		List<ApiLinkChange> changes = new ArrayList<>();

		ClientApiLinkChange linkChange = new ClientApiLinkChange();
		linkChange.setPrimary(primaryKey);
		linkChange.setChangeAction(MutableLinkChangeType.CLEAR);
		linkChange.setLinkName(this.testLinkName);

		changes.add(linkChange);

		boolean atomic = true;
		ClientLinkServiceRequest request = new ClientLinkServiceRequestImpl(type, changes, atomic);

		try {
			this.linkRequestSender.updateLinks(request, security);
			fail("Model was apparently available..?");
		} catch (ClientAtomicOperationException e) {
			List<ModelKey> keys = e.getMissingKeys();
			assertTrue(keys.contains(primaryKey));
		}
	}

	public void testSystemClientLinkNonAtomicRequest(ClientRequestSecurity security) throws Exception {
		String type = this.testModelGenerator.getModelType();
		ModelKey primaryKey = this.testModelGenerator.generateKey();

		List<ApiLinkChange> changes = new ArrayList<>();

		ClientApiLinkChange linkChange = new ClientApiLinkChange();
		linkChange.setPrimary(primaryKey);
		linkChange.setChangeAction(MutableLinkChangeType.CLEAR);
		linkChange.setLinkName(this.testLinkName);

		changes.add(linkChange);

		boolean atomic = false;
		ClientLinkServiceRequest request = new ClientLinkServiceRequestImpl(type, changes, atomic);

		try {
			ClientLinkServiceResponse response = this.linkRequestSender.updateLinks(request, security);
			List<ModelKey> keys = response.getMissing();
			assertTrue(keys.contains(primaryKey));
		} catch (ClientAtomicOperationException e) {
			fail("Atomic operation exception raised on non-atomic request.");
		}
	}

}
