package com.dereekb.gae.test.utility.model.attribute;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.utilities.model.attribute.ModelAttributeUtilityInstance;
import com.dereekb.gae.utilities.model.attribute.impl.ModelDateAttributeHelperUtilityImpl;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * {@link ModelDateAttributeHelperUtilityImpl} tests.
 *
 * @author dereekb
 *
 */
public class ModelDateAttributeHelperUtilityTest extends AbstractModelAttributeHelperUtilityTest {

	@Test
	public void testFuture() {
		ModelDateAttributeHelperUtilityImpl utility = new ModelDateAttributeHelperUtilityImpl();

		utility.future(0L, FAIL_CODE);
		assertTrue(utility.isAssertFuture());
		assertTrue(utility.getFutureCode().equals(FAIL_CODE));

		ModelAttributeUtilityInstance<Date> instance = utility.makeInstance(ATTRIBUTE);

		Date nonEpoch = new Date(1);

		try {
			instance.assertValidDecodedValue(nonEpoch);
			fail("Should have failed on non-future value.");
		} catch (InvalidAttributeException e) {

		}
	}

}
