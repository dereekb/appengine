package com.dereekb.gae.test.utility.data;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.dereekb.gae.utilities.data.StringUtility;

public class StringUtilityTest {

	@Test
	public void testNormalizeSpaceRemovesNewLines() throws IOException {
		String text = "A\nB\nC";
		String normalized = StringUtility.normalizeSpace(text);
		assertTrue(normalized.equals("A B C"));
	}

	@Test
	public void testNormalizeSpaceDoesNotLeaveExtraSpaces() throws IOException {
		String text = "A\n B \nC";
		String normalized = StringUtility.normalizeSpace(text);
		assertTrue(normalized.equals("A B C"));
	}

}
