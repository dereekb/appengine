package com.dereekb.gae.test.server.event;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.impl.EventImpl;
import com.dereekb.gae.server.event.event.impl.EventTypeImpl;
import com.dereekb.gae.server.event.event.service.EventServiceListener;
import com.dereekb.gae.server.event.event.service.impl.EventServiceImpl;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.collections.pairs.impl.SuccessResultsPair;

public class EventServiceTest {

	@Test
	public void testEventServiceSubmitEvent() {

		EventServiceImpl service = new EventServiceImpl();

		SuccessResultsPair<Object> resultPair = new SuccessResultsPair<Object>(null);

		EventServiceListener listener = new EventServiceListener() {

			@Override
			public void handleEvent(Event event) {
				resultPair.setResult(true);
			}

		};

		service.setListeners(ListUtility.toList(listener));

		Event event = new EventImpl(new EventTypeImpl("test", "test"));

		service.submitEvent(event);

		assertTrue(resultPair.isSuccessful());
	}

}
