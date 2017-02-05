package com.dereekb.gae.test.utility.web;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.AntPathMatcher;

import com.dereekb.gae.utilities.web.matcher.MultiTypeAntRequestMatcher;

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

		Assert.assertTrue(matcher.matches("/a/query"));
		Assert.assertTrue(matcher.matches("/b/query"));
		Assert.assertTrue(matcher.matches("/c/query"));

		Assert.assertFalse(matcher.matches("/d/query"));
		Assert.assertFalse(matcher.matches("/x/y/z"));
		Assert.assertFalse(matcher.matches("/dfgsdfgsfdg"));
	}

}
