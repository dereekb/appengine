package com.dereekb.gae.test.utility.gae;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.dereekb.gae.utilities.gae.GoogleAppEngineUtility;

/**
 *
 * @author dereekb
 *
 */
public class GoogleAppEngineUtilityTest {

	@Test
	public void testSanitizeAppId() {

		String normalAppId = "mytest";

		String usPrefixAppId = "s~mytest";

		String euPrefixAppId = "e~mytest";

		String customPrefixAppId = "edfgsdfgsfdg~mytest";

		assertTrue(GoogleAppEngineUtility.sanitizeAppId(normalAppId).equals(normalAppId));
		assertTrue(GoogleAppEngineUtility.sanitizeAppId(usPrefixAppId).equals(normalAppId));
		assertTrue(GoogleAppEngineUtility.sanitizeAppId(euPrefixAppId).equals(normalAppId));
		assertTrue(GoogleAppEngineUtility.sanitizeAppId(customPrefixAppId).equals(normalAppId));
	}

}
