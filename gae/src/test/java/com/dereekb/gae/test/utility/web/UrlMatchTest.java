package com.dereekb.gae.test.utility.web;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.AntPathMatcher;

import com.dereekb.gae.utilities.collections.map.HashMapWithSet;
import com.dereekb.gae.utilities.web.matcher.MultiTypeAntRequestMatcher;
import com.dereekb.gae.utilities.web.matcher.MultiTypeMapAntRequestMatcher;

public class UrlMatchTest {

	private static final AntPathMatcher antMatcher = createMatcher();

	private static AntPathMatcher createMatcher() {
		AntPathMatcher matcher = new AntPathMatcher();
		matcher.setTrimTokens(false);
		return matcher;
	}

	@Ignore
	@Test
	public void testAntMatcher() {
		String variable = "b";
		String pattern = "a/{" + variable + "}/c";
		String path = "a/" + variable + "/c";

		Assert.assertTrue(antMatcher.match(pattern, path));
		Map<String, String> variables = antMatcher.extractUriTemplateVariables(pattern, path);

		String variableValue = variables.get(variable);
		Assert.assertTrue(variableValue.equals(variable));
	}

	@Test
	public void testMultiTypeAntRequestMatcher() {
		String pattern = "/{type}/query";
		Set<String> types = new HashSet<String>();
		types.add("a");
		types.add("b");
		types.add("c");

		MultiTypeAntRequestMatcher matcher = new MultiTypeAntRequestMatcher(pattern, types);

		Assert.assertTrue(matcher.matchesPath("/a/query"));
		Assert.assertTrue(matcher.matchesPath("/b/query"));
		Assert.assertTrue(matcher.matchesPath("/c/query"));

		Assert.assertFalse(matcher.matchesPath("/d/query"));
		Assert.assertFalse(matcher.matchesPath("/x/y/z"));
		Assert.assertFalse(matcher.matchesPath("/dfgsdfgsfdg"));
	}

	@Test
	public void testMultiTypeMapAntRequestMatcher() {
		String pattern = "/{a}/{x}/foo";

		HashMapWithSet<String, String> map = new HashMapWithSet<String, String>();
		// Matches against {a}
		map.add("a", "a");
		map.add("a", "b");
		map.add("a", "c");

		// Matches against {x}
		map.add("x", "x");
		map.add("x", "y");
		map.add("x", "z");

		MultiTypeMapAntRequestMatcher matcher = new MultiTypeMapAntRequestMatcher(pattern, map);

		Assert.assertTrue(matcher.matchesPath("/b/y/foo"));
		Assert.assertTrue(matcher.matchesPath("/b/x/foo"));
		Assert.assertTrue(matcher.matchesPath("/a/z/foo"));
		Assert.assertTrue(matcher.matchesPath("/c/x/foo"));

		Assert.assertFalse(matcher.matchesPath("/d/query"));
		Assert.assertFalse(matcher.matchesPath("/x/y/z"));
		Assert.assertFalse(matcher.matchesPath("/dfgsdfgsfdg"));
	}

}
