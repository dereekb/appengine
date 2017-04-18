package com.dereekb.gae.utilities.misc.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dereekb.gae.utilities.misc.path.impl.SimplePathImpl;
import com.google.common.base.Joiner;

/**
 * Contains static functions for combining URIs.
 *
 * @author dereekb
 *
 */
public class PathUtility {

	public static List<String> getComponents(String path,
	                                         String separator) {
		List<String> components;

		if (path == null) {
			components = Collections.emptyList();
		} else {
			String[] split = path.split(separator);
			components = new ArrayList<String>();

			for (String component : split) {
				if (component.isEmpty() == false) {
					components.add(component);
				}
			}
		}

		return components;
	}

	@SafeVarargs
	public static List<String> mergeComponents(List<String>... components) {
		List<String> merged = new ArrayList<String>();

		for (List<String> component : components) {
			merged.addAll(component);
		}

		return merged;
	}

	public static List<String> mergePaths(SimplePath... paths) {
		List<String> merged = new ArrayList<String>();

		for (SimplePath path : paths) {
			merged.addAll(path.getPathComponents());
		}

		return merged;
	}

	public static String buildPath(List<String> components,
	                               String divider) {
		Joiner joiner = Joiner.on(divider).skipNulls();
		return joiner.join(components);
	}

	public static String buildPath(String basePath,
	                               String relativePath) {
		return buildPath(basePath, new SimplePathImpl(relativePath));
	}

	public static String buildPath(String basePath,
	                               SimplePath relativePath) {

		if (basePath.charAt(basePath.length() - 1) == '/') {
			basePath = basePath.substring(0, basePath.length() - 2);
		}

		return basePath + relativePath.getPath();
	}

}
