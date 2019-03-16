package com.dereekb.gae.test.utility.web;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
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

	@Disabled
	@Test
	public void testAntMatcher() {
		String variable = "b";
		String pattern = "a/{" + variable + "}/c";
		String path = "a/" + variable + "/c";

		assertTrue(antMatcher.match(pattern, path));
		Map<String, String> variables = antMatcher.extractUriTemplateVariables(pattern, path);

		String variableValue = variables.get(variable);
		assertTrue(variableValue.equals(variable));
	}

	@Test
	public void testWildcardPrefixAntMatcher() {
		String pattern = "**/a/b/c/**";
		String directPath = "/a/b/c";
		String apiPath = "api/service/version" + directPath;

		assertTrue(antMatcher.match(pattern, apiPath), "Should match the entire path");
		assertFalse(antMatcher.match(pattern, directPath), "Will not match the direct path");
	}

	@Test
	public void testMultiTypeAntRequestMatcher() {
		String pattern = "/{type}/query";
		Set<String> types = new HashSet<String>();
		types.add("a");
		types.add("b");
		types.add("c");

		MultiTypeAntRequestMatcher matcher = new MultiTypeAntRequestMatcher(pattern, types);

		assertTrue(matcher.matchesPath("/a/query"));
		assertTrue(matcher.matchesPath("/b/query"));
		assertTrue(matcher.matchesPath("/c/query"));

		assertFalse(matcher.matchesPath("/d/query"));
		assertFalse(matcher.matchesPath("/x/y/z"));
		assertFalse(matcher.matchesPath("/dfgsdfgsfdg"));
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

		assertTrue(matcher.matchesPath("/b/y/foo"));
		assertTrue(matcher.matchesPath("/b/x/foo"));
		assertTrue(matcher.matchesPath("/a/z/foo"));
		assertTrue(matcher.matchesPath("/c/x/foo"));

		assertFalse(matcher.matchesPath("/d/query"));
		assertFalse(matcher.matchesPath("/x/y/z"));
		assertFalse(matcher.matchesPath("/dfgsdfgsfdg"));
	}

}
