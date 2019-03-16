package com.dereekb.gae.test.utility.misc.path;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.utilities.misc.path.impl.SimplePathImpl;

/**
 * Tests for {@link SimplePathImpl}.
 *
 * @author dereekb
 *
 */
public class SimplePathImplTest {

	@Test
	public void testPath() {

		String a = "a";
		SimplePathImpl aPath = new SimplePathImpl(a);

		String path = aPath.getPath();
		assertTrue(path.equals(aPath.getDivider() + a));
	}

	@Test
	public void testPathWithNullString() {

		String a = null;
		SimplePathImpl aPath = new SimplePathImpl(a);

		String path = aPath.getPath();
		assertTrue(path.equals(aPath.getDivider()));
	}

	@Test
	public void testPathCombining() {

		String a = "a";
		SimplePathImpl aPath = new SimplePathImpl(a);

		String b = "b";
		SimplePathImpl bPath = new SimplePathImpl(b);

		SimplePathImpl cPath = aPath.append(bPath);

		String path = cPath.getPath();
		String expected = aPath.getDivider() + a + aPath.getDivider() + b;

		assertTrue(path.equals(expected));
	}

	@Test
	public void testMalformedPathCombining() {

		String a = "//////a////";
		SimplePathImpl aPath = new SimplePathImpl(a);

		String b = "/b//////";
		SimplePathImpl bPath = new SimplePathImpl(b);

		SimplePathImpl cPath = aPath.append(bPath);

		String path = cPath.getPath();
		String expected = aPath.getDivider() + 'a' + aPath.getDivider() + 'b';

		assertTrue(path.equals(expected));
	}

}
