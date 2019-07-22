package com.dereekb.gae.test.server.event.webhook;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.model.shared.event.impl.CommonModelEventType;
import com.dereekb.gae.server.event.model.shared.webhook.impl.ModelKeyWebHookEventDataImpl;
import com.dereekb.gae.server.event.webhook.WebHookEventData;
import com.dereekb.gae.server.event.webhook.impl.JsonWebHookEventImpl;
import com.dereekb.gae.server.event.webhook.impl.WebHookEventImpl;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebHookEventSerializationTest {

	/**
	 * Test serializing a {@link WebHookEventImpl} to JSON, then to a
	 * {@link JsonWebHookEventImpl}.
	 *
	 * @throws IOException
	 */
	@Test
	public void testSerializingBasicWebHookEvent() throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		EventType eventType = CommonModelEventType.CREATED;
		List<String> testData = ListUtility.toList("a", "b", "c");

		WebHookEventImpl event = new WebHookEventImpl("scope", eventType, testData);

		// Convert to JSON
		JsonNode node = event.getJsonNode(); // mapper.valueToTree(event);
		String json = mapper.writeValueAsString(node);

		JsonNode readNode = mapper.readTree(json);

		JsonWebHookEventImpl jsonWebHook = new JsonWebHookEventImpl(readNode);
		assertTrue(EventType.isEquivalent(eventType, jsonWebHook.getEventType()));
	}

	@Test
	public void testSerializingModelWebHookEvent() throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		EventType eventType = CommonModelEventType.CREATED;
		WebHookEventImpl event = new WebHookEventImpl(eventType);

		ModelKeyWebHookEventDataImpl eventData = new ModelKeyWebHookEventDataImpl();
		eventData.setKeys(ListUtility.toList("F_10157213088039532"));
		eventData.setModelType("LoginPointer");

		event.setData(eventData);

		// Convert to JSON
		JsonNode node = event.getJsonNode(); // mapper.valueToTree(event);
		String json = mapper.writeValueAsString(node);

		JsonNode readNode = mapper.readTree(json);

		JsonWebHookEventImpl jsonWebHook = new JsonWebHookEventImpl(readNode);
		assertTrue(EventType.isEquivalent(eventType, jsonWebHook.getEventType()));

		WebHookEventData deserializedEventData = jsonWebHook.getEventData();
		assertTrue(deserializedEventData.getEventDataType().equals(eventData.getEventDataType()));

	}

	// TODO: Test serializing event with missing group/type throws an exception.

}
